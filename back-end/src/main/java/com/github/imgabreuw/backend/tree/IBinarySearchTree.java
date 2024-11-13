package com.github.imgabreuw.backend.tree;

import java.util.Optional;
import java.util.stream.Stream;

public sealed interface IBinarySearchTree<T extends Comparable<T>> permits AbstractIBinarySearchTree {

    void insert(T data);

    boolean search(T data);

    void delete(T data);

    Optional<T> min();

    Optional<T> max();

    int size();

    boolean isEmpty();

    Stream<T> inOrderTraversal();

    Stream<T> preOrderTraversal();

    Stream<T> postOrderTraversal();

}

