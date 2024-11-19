package com.github.imgabreuw.backend.database.structure.node;

public sealed interface Node<T extends Comparable<T>> permits BSTNode, AVLNode {

    void setData(T data);

    T getData();

    Node<T> getLeft();

    Node<T> getRight();

    void setLeft(Node<T> left);

    void setRight(Node<T> right);

}
