package com.github.imgabreuw.backend.database.structure.node;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class AVLNode<T extends Comparable<T>> implements Node<T> {

    private T data;

    private Node<T> left;
    private Node<T> right;

    private int height;
    private int balanceFactor;

    public AVLNode(T data) {
        this.data = data;
    }

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

