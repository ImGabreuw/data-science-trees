package com.github.imgabreuw.backend.database.structure.node;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class BSTNode<T extends Comparable<T>> implements Node<T> {

    private final T data;

    private Node<T> left;
    private Node<T> right;

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Node<T> getLeft() {
        return left;
    }

    @Override
    public Node<T> getRight() {
        return right;
    }

    @Override
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    @Override
    public void setRight(Node<T> right) {
        this.right = right;
    }

}
