package com.github.imgabreuw.backend.database.structure;

import com.github.imgabreuw.backend.database.structure.node.BSTNode;
import com.github.imgabreuw.backend.database.structure.node.Node;

public final class BST<T extends Comparable<T>> extends AbstractIBinarySearchTree<T> {

    @Override
    protected Node<T> doInsert(Node<T> node, T data) {
        if (node == null) {
            return createNode(data);
        }

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            return new BSTNode<>(node.getData(), doInsert(node.getLeft(), data), node.getRight());
        }

        if (cmp > 0) {
            return new BSTNode<>(node.getData(), node.getLeft(), doInsert(node.getRight(), data));
        }

        return node;
    }

    @Override
    protected Node<T> doDelete(Node<T> node, T data) {
        if (node == null) return null;

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            return new BSTNode<>(node.getData(), doDelete(node.getLeft(), data), node.getRight());
        }

        if (cmp > 0) {
            return new BSTNode<>(node.getData(), node.getLeft(), doDelete(node.getRight(), data));
        }

        if (node.getLeft() == null) {
            return node.getRight();
        }

        if (node.getRight() == null) {
            return node.getLeft();
        }

        Node<T> successor = findMin(node.getRight());

        return new BSTNode<>(successor.getData(), node.getLeft(), doDelete(node.getRight(), successor.getData()));
    }

}

