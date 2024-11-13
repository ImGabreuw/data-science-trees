package com.github.imgabreuw.backend.tree;

import com.github.imgabreuw.backend.tree.node.AVLNode;
import com.github.imgabreuw.backend.tree.node.Node;

public final class AVL<T extends Comparable<T>> extends AbstractIBinarySearchTree<T> {

    @Override
    protected Node<T> doInsert(Node<T> node, T data) {
        if (node == null) {
            return new AVLNode<>(data);
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(doInsert(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(doInsert(node.getRight(), data));
        } else {
            return node; // Elemento duplicado não é inserido
        }

        // Atualizar altura e balanceamento do nó
        updateHeightAndBalanceFactor((AVLNode<T>) node);

        // Balancear o nó
        return balance((AVLNode<T>) node);
    }

    @Override
    protected Node<T> doDelete(Node<T> node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(doDelete(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(doDelete(node.getRight(), data));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            } else {
                Node<T> successor = findMin(node.getRight());
                ((AVLNode<T>) node).setData(successor.getData());
                node.setRight(doDelete(node.getRight(), successor.getData()));
            }
        }

        if (node == null) {
            return null;
        }

        updateHeightAndBalanceFactor((AVLNode<T>) node);

        return balance((AVLNode<T>) node);
    }

    private void updateHeightAndBalanceFactor(AVLNode<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    private int height(Node<T> node) {
        return (node == null) ? 0 : ((AVLNode<T>) node).getHeight();
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) { // Subárvore esquerda é mais alta
            if (((AVLNode<T>) node.getLeft()).getBalanceFactor() < 0) {
                node.setLeft(leftRotate((AVLNode<T>) node.getLeft())); // Rotação esquerda-direita
            }

            return rightRotate(node);
        }

        if (node.getBalanceFactor() < -1) { // Subárvore direita é mais alta
            if (((AVLNode<T>) node.getRight()).getBalanceFactor() > 0) {
                node.setRight(rightRotate((AVLNode<T>) node.getRight())); // Rotação direita-esquerda
            }
            return leftRotate(node);
        }

        return node;
    }

    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = (AVLNode<T>) y.getLeft();
        AVLNode<T> T2 = (AVLNode<T>) x.getRight();

        // Rotação
        x.setRight(y);
        y.setLeft(T2);

        // Atualizar alturas e fatores de balanceamento
        updateHeightAndBalanceFactor(y);
        updateHeightAndBalanceFactor(x);

        return x;
    }

    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = (AVLNode<T>) x.getRight();
        AVLNode<T> T2 = (AVLNode<T>) y.getLeft();

        // Rotação
        y.setLeft(x);
        x.setRight(T2);

        // Atualizar alturas e fatores de balanceamento
        updateHeightAndBalanceFactor(x);
        updateHeightAndBalanceFactor(y);

        return y;
    }
}

