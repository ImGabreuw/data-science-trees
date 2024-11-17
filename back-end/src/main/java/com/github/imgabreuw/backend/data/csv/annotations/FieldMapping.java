package com.github.imgabreuw.backend.data.csv.annotations;

import java.lang.reflect.Field;

public record FieldMapping(Field field, String subFieldName) {

    public FieldMapping {
        if (field == null) {
            throw new IllegalArgumentException("field cannot be null");
        }

        subFieldName = subFieldName == null ? "" : subFieldName;
    }

}
