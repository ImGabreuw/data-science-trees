package com.github.imgabreuw.backend.data.converter;

import com.github.imgabreuw.backend.data.csv.exception.CsvProcessingException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ValueConverter {

    private final Map<Class<?>, ValueConverterFunction<?>> converters = new ConcurrentHashMap<>();

    public ValueConverter() {
        registerDefaultConverters();
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(String value, Class<T> targetType) {
        ValueConverterFunction<T> converter = (ValueConverterFunction<T>) converters.get(targetType);

        if (converter == null) {
            throw new CsvProcessingException("No converter found for type: " + targetType);
        }

        return converter.convert(value);
    }

    private void registerDefaultConverters() {
        converters.put(String.class, value -> value);
        converters.put(Integer.class, Integer::valueOf);
        converters.put(int.class, Integer::parseInt);
        converters.put(Double.class, value -> Double.valueOf(value.replace(",", ".")));
        converters.put(double.class, value -> Double.parseDouble(value.replace(",", ".")));
    }

}
