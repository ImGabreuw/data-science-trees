package com.github.imgabreuw.backend.database.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface TreeRepository<T extends Comparable<T>> {

    void save(T entity);

    void saveAll(Collection<T> entities);

    Optional<T> find(Predicate<T> predicate);

    Stream<T> findAll();

    void delete(T entity);

    void deleteAll();

    boolean exists(T entity);

    long count();

    Optional<T> findMin();

    Optional<T> findMax();

    Stream<T> findInRange(T start, T end);
}
