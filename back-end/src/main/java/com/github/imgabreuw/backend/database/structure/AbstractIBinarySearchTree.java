package com.github.imgabreuw.backend.database.structure;

import com.github.imgabreuw.backend.database.structure.node.BSTNode;
import com.github.imgabreuw.backend.database.structure.node.Node;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public abstract sealed class AbstractIBinarySearchTree<T extends Comparable<T>> implements IBinarySearchTree<T> permits BST, AVL {

    protected Node<T> root;

    protected int size;

    @Override
    public final void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        try {
            root = doInsert(root, data);
            size++;
        } catch (IllegalArgumentException ignored) {
        }
    }

    protected abstract Node<T> doInsert(Node<T> node, T data);

    @Override
    public final void delete(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        root = doDelete(root, data);
        size--;
    }

    protected abstract Node<T> doDelete(Node<T> node, T data);

    @Override
    public final boolean search(T data) {
        return searchNode(root, data);
    }

    private boolean searchNode(Node<T> node, T data) {
        return switch (node) {
            case null -> false;
            case Node<T> n when data.compareTo(n.getData()) == 0 -> true;
            case Node<T> n when data.compareTo(n.getData()) < 0 -> searchNode(n.getLeft(), data);
            default -> searchNode(node.getRight(), data);
        };
    }

    @Override
    public final Optional<T> min() {
        return Optional.ofNullable(findMin(root)).map(Node::getData);
    }

    protected Node<T> findMin(Node<T> node) {
        if (node == null) return null;

        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    @Override
    public final Optional<T> max() {
        return Optional.ofNullable(findMax(root)).map(Node::getData);
    }

    protected Node<T> findMax(Node<T> node) {
        if (node == null) return null;

        while (node.getRight() != null) {
            node = node.getRight();
        }

        return node;
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Stream<T> inOrderTraversal() {
        Builder<T> builder = Stream.builder();
        inOrder(root, builder);
        return builder.build();
    }

    private void inOrder(Node<T> node, Builder<T> builder) {
        if (node != null) {
            inOrder(node.getLeft(), builder);
            builder.add(node.getData());
            inOrder(node.getRight(), builder);
        }
    }

    @Override
    public Stream<T> preOrderTraversal() {
        Builder<T> builder = Stream.builder();
        preOrder(root, builder);
        return builder.build();
    }

    private void preOrder(Node<T> node, Builder<T> builder) {
        if (node != null) {
            builder.add(node.getData());
            preOrder(node.getLeft(), builder);
            preOrder(node.getRight(), builder);
        }
    }

    @Override
    public Stream<T> postOrderTraversal() {
        Builder<T> builder = Stream.builder();
        postOrder(root, builder);
        return builder.build();
    }

    private void postOrder(Node<T> node, Builder<T> builder) {
        if (node != null) {
            postOrder(node.getLeft(), builder);
            postOrder(node.getRight(), builder);
            builder.add(node.getData());
        }
    }

    protected Node<T> createNode(T data) {
        return new BSTNode<>(data, null, null);
    }
}

