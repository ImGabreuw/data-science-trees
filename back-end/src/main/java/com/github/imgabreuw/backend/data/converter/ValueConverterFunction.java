package com.github.imgabreuw.backend.data.converter;

@FunctionalInterface
public interface ValueConverterFunction<T> {

    T convert(String value);

}