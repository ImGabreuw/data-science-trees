package com.github.imgabreuw.backend.data.csv.annotations;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CsvAnnotationProcessor {

    @Cacheable(value = "csvAnnotations", key = "#clazz.getName()")
    public Map<String, FieldMapping> processAnnotations(Class<?> clazz) {
        ConcurrentHashMap<String, FieldMapping> columnToFieldMap = new ConcurrentHashMap<>();

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> processField(field, columnToFieldMap));

        return columnToFieldMap;
    }

    private void processField(Field field, Map<String, FieldMapping> columnToFieldMap) {
        field.setAccessible(true);

        processCsvColumn(field, columnToFieldMap);
        processCsvColumnMapping(field, columnToFieldMap);
    }

    private void processCsvColumn(Field field, Map<String, FieldMapping> columnToFieldMap) {
        CsvColumn annotation = field.getAnnotation(CsvColumn.class);

        if (annotation == null) {
            return;
        }

        columnToFieldMap.put(
                annotation.value(),
                new FieldMapping(field, annotation.fieldName())
        );
    }

    private void processCsvColumnMapping(Field field, Map<String, FieldMapping> columnToFieldMap) {
        CsvColumnMapping mapping = field.getAnnotation(CsvColumnMapping.class);

        if (mapping == null) {
            return;
        }

        Arrays.stream(mapping.value())
                .forEach(annotation -> columnToFieldMap.put(
                        annotation.value(),
                        new FieldMapping(field, annotation.fieldName())
                ));
    }
}

