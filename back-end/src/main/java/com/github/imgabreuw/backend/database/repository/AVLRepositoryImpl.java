package com.github.imgabreuw.backend.database.repository;

import com.github.imgabreuw.backend.database.exception.DatabaseException;
import com.github.imgabreuw.backend.database.structure.AVL;
import com.github.imgabreuw.backend.database.structure.AbstractIBinarySearchTree;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Log4j2
@Repository
public class AVLRepositoryImpl<T extends Comparable<T>> implements TreeRepository<T> {

    private final AbstractIBinarySearchTree<T> tree = new AVL<>();

    @Override
    public void save(T entity) {
        try {
            tree.insert(entity);
            log.debug("AVL saved entity: {}", entity);
        } catch (Exception e) {
            log.error("AVL save error", e);
            throw new DatabaseException("Failed to save entity", e);
        }
    }

    @Override
    public void saveAll(Collection<T> entities) {
        entities.forEach(this::save);
    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        Optional<T> node = tree.inOrderTraversal()
                .filter(predicate)
                .findFirst();

        if (node.isPresent()) {
            log.debug("AVL found entity: {}", node.get());
        } else {
            log.debug("AVL not found entity");
        }

        return node;
    }

    @Override
    public Stream<T> findAll() {
        return tree.inOrderTraversal();
    }

    @Override
    public void delete(T entity) {
        try {
            tree.delete(entity);
            log.debug("AVL deleted entity: {}", entity);
        } catch (Exception e) {
            log.error("AVL delete error", e);
            throw new DatabaseException("Failed to delete entity", e);
        }
    }

    @Override
    public void deleteAll() {
        while (tree.min().isPresent()) {
            tree.delete(tree.min().get());
        }
    }

    @Override
    public boolean exists(T entity) {
        return tree.search(entity);
    }

    @Override
    public long count() {
        return tree.size();
    }

    @Override
    public Optional<T> findMin() {
        return tree.min();
    }

    @Override
    public Optional<T> findMax() {
        return tree.max();
    }

    @Override
    public Stream<T> findInRange(T start, T end) {
        return tree.inOrderTraversal()
                .filter(item -> item.compareTo(start) >= 0 && item.compareTo(end) <= 0);
    }
}
