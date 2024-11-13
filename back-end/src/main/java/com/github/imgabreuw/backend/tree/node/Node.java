package com.github.imgabreuw.backend.tree.node;

public sealed interface Node<T extends Comparable<T>> permits BSTNode, AVLNode {

    T getData();

    Node<T> getLeft();

    Node<T> getRight();

    void setLeft(Node<T> left);

    void setRight(Node<T> right);

}
