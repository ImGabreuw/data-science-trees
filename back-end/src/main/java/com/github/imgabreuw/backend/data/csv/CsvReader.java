package com.github.imgabreuw.backend.data.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public static <T> List<T> readCsv(Path filePath, Class<T> clazz) throws IOException, ReflectiveOperationException {
        Map<String, CsvAnnotationProcessor.FieldWrapper> columnToFieldMap = CsvAnnotationProcessor.processAnnotations(clazz);

        List<T> records = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String[] headers = reader.readLine().split(";");
            Map<Integer, String> indexToColumnMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                indexToColumnMap.put(i, headers[i]);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                Map<String, String> columnValues = new HashMap<>();
                for (int i = 0; i < values.length; i++) {
                    columnValues.put(indexToColumnMap.get(i), values[i]);
                }

                T instance = createInstance(clazz, columnValues, columnToFieldMap);
                records.add(instance);
            }
        }

        return records;
    }

    private static Map<Integer, CsvAnnotationProcessor.FieldWrapper> mapHeadersToFields(String[] headers, Map<String, CsvAnnotationProcessor.FieldWrapper> columnToFieldMap) {
        Map<Integer, CsvAnnotationProcessor.FieldWrapper> indexToFieldMap = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            CsvAnnotationProcessor.FieldWrapper wrapper = columnToFieldMap.get(headers[i]);

            if (wrapper != null) {
                indexToFieldMap.put(i, wrapper);
            }
        }

        return indexToFieldMap;
    }

    private static Object convertValue(String value, Class<?> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (type == String.class) {
            return value;
        }

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == double.class || type == Double.class) {
            if (value.contains(",")) {
                value = value.replace(",", ".");
            }

            return Double.parseDouble(value);
        }

        // Para classes compostas, inicializa uma nova instância.
        return type.getDeclaredConstructor().newInstance();
    }

    private static <T> T createInstance(Class<T> clazz, Map<String, String> values, Map<String, CsvAnnotationProcessor.FieldWrapper> columnToFieldMap) throws ReflectiveOperationException {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String columnName = entry.getKey();
            String columnValue = entry.getValue();
            CsvAnnotationProcessor.FieldWrapper wrapper = columnToFieldMap.get(columnName);

            if (wrapper != null) {
                Field field = wrapper.field();
                field.setAccessible(true);

                if (wrapper.subFieldName().isEmpty()) {
                    // Campo simples
                    Object value = convertValue(columnValue, field.getType());
                    field.set(instance, value);
                } else {
                    // Campo composto
                    Object compositeInstance = field.get(instance);
                    if (compositeInstance == null) {
                        compositeInstance = field.getType().getDeclaredConstructor().newInstance();
                        field.set(instance, compositeInstance);
                    }

                    Field subField = compositeInstance.getClass().getDeclaredField(wrapper.subFieldName());
                    subField.setAccessible(true);
                    Object value = convertValue(columnValue, subField.getType());
                    subField.set(compositeInstance, value);
                }
            }
        }

        return instance;
    }

}
