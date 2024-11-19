package com.github.imgabreuw.backend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamHelper {

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
