package com.github.imgabreuw.backend.data.csv;

import com.github.imgabreuw.backend.data.csv.config.CsvProperties;
import com.github.imgabreuw.backend.data.csv.annotations.CsvAnnotationProcessor;
import com.github.imgabreuw.backend.data.csv.annotations.FieldMapping;
import com.github.imgabreuw.backend.data.converter.ValueConverter;
import com.github.imgabreuw.backend.data.csv.exception.CsvProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CsvReader {

    private final CsvAnnotationProcessor annotationProcessor;
    private final ValueConverter valueConverter;
    private final CsvProperties properties;

    @Cacheable(value = "csvRecords", key = "#filePath.toString() + #clazz.getName()")
    public <T> List<T> readCsv(Path filePath, Class<T> clazz) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            Map<String, FieldMapping> columnToFieldMap = annotationProcessor.processAnnotations(clazz);
            Map<Integer, String> headers = parseHeaders(reader.readLine());

            return reader.lines()
                    .parallel()
                    .map(line -> parseLine(line, headers, clazz, columnToFieldMap))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CsvProcessingException("Failed to process CSV file", e);
        }
    }

    private Map<Integer, String> parseHeaders(String headerLine) {
        if (headerLine == null) {
            throw new CsvProcessingException("CSV file is empty");
        }

        ConcurrentHashMap<Integer, String> headers = new ConcurrentHashMap<>();
        String[] headerArray = headerLine.split(properties.getDelimiter());

        for (int i = 0; i < headerArray.length; i++) {
            headers.put(i, headerArray[i].trim());
        }

        return headers;
    }

    private <T> T parseLine(
            String line,
            Map<Integer, String> headers,
            Class<T> clazz,
            Map<String, FieldMapping> columnToFieldMap
    ) {
        try {
            String[] values = line.split(properties.getDelimiter());
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (int i = 0; i < values.length; i++) {
                String columnName = headers.get(i);
                FieldMapping fieldMapping = columnToFieldMap.get(columnName);

                if (fieldMapping != null) {
                    setFieldValue(instance, fieldMapping, values[i].trim());
                }
            }

            return instance;
        } catch (Exception e) {
            throw new CsvProcessingException("Failed to parse CSV line: " + line, e);
        }
    }

    private void setFieldValue(Object instance, FieldMapping fieldMapping, String value) {
        try {
            Field field = fieldMapping.field();
            field.setAccessible(true);

            if (StringUtils.hasText(fieldMapping.subFieldName())) {
                setCompositeFieldValue(instance, fieldMapping, value);
                return;
            }

            // Set simple field value
            Object convertedValue = valueConverter.convert(value, field.getType());
            field.set(instance, convertedValue);
        } catch (Exception e) {
            throw new CsvProcessingException("Failed to set field value", e);
        }
    }

    private void setCompositeFieldValue(Object instance, FieldMapping fieldMapping, String value) throws ReflectiveOperationException {
        Field field = fieldMapping.field();
        Object compositeInstance = field.get(instance);

        if (compositeInstance == null) {
            compositeInstance = field.getType().getDeclaredConstructor().newInstance();
            field.set(instance, compositeInstance);
        }

        Field subField = compositeInstance.getClass().getDeclaredField(fieldMapping.subFieldName());
        subField.setAccessible(true);

        Object convertedValue = valueConverter.convert(value, subField.getType());
        subField.set(compositeInstance, convertedValue);
    }

}
