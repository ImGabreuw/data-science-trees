package com.github.imgabreuw.backend.data.csv;

import com.github.imgabreuw.backend.data.csv.annotations.CsvColumn;
import com.github.imgabreuw.backend.data.csv.annotations.CsvColumnMapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Processador da anotação CsvColumn e CsvColumnMapping.
 * <br>
 * Responsável por mapear os campos da classe ao cabeçalho do arquivo CSV.
 */
public class CsvAnnotationProcessor {

    /**
     * Processa as anotações de uma classe e retorna um mapa associando nomes de colunas a campos.
     *
     * @param clazz a classe para ser processada.
     * @return um mapa onde a chave é o nome da coluna do CSV e o valor é o campo correspondente.
     */
    public static Map<String, FieldWrapper> processAnnotations(Class<?> clazz) {
        Map<String, FieldWrapper> columnToFieldMap = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(CsvColumn.class)) {
                CsvColumn annotation = field.getAnnotation(CsvColumn.class);
                columnToFieldMap.put(annotation.value(), new FieldWrapper(field, annotation.fieldName()));
                continue;
            }

            if (field.isAnnotationPresent(CsvColumnMapping.class)) {
                CsvColumnMapping mapping = field.getAnnotation(CsvColumnMapping.class);

                for (CsvColumn annotation : mapping.value()) {
                    String columnName = annotation.value();
                    columnToFieldMap.put(columnName, new FieldWrapper(field, annotation.fieldName()));
                }
            }
        }

        return columnToFieldMap;
    }

    /**
     * Representa um campo mapeado, incluindo seu subcampo (se aplicável).
     */
    public record FieldWrapper(Field field, String subFieldName) {
    }
}


