package com.github.imgabreuw.backend.database.structure;

import com.github.imgabreuw.backend.database.structure.node.Node;
import com.github.imgabreuw.backend.database.structure.node.AVLNode;

public final class AVL<T extends Comparable<T>> extends AbstractIBinarySearchTree<T> {

    @Override
    protected Node<T> doInsert(Node<T> node, T data) {
        if (node == null) {
            return new AVLNode<>(data); // Caso base: criar novo nó
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(doInsert(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(doInsert(node.getRight(), data));
        } else {
            return node; // Elemento já existe
        }

        updateHeightAndBalanceFactor((AVLNode<T>) node);

        return balance((AVLNode<T>) node);
    }

    @Override
    protected Node<T> doDelete(Node<T> node, T data) {
        if (node == null) {
            return null; // Caso base: nó não encontrado
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(doDelete(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(doDelete(node.getRight(), data));
        } else {
            node = handleNodeDeletionCases(node);
        }

        if (node == null) {
            return null; // Árvore ficou vazia
        }

        updateHeightAndBalanceFactor((AVLNode<T>) node);

        return balance((AVLNode<T>) node);
    }

    private Node<T> handleNodeDeletionCases(Node<T> node) {
        // Caso com 0 ou 1 filho
        if (node.getLeft() == null || node.getRight() == null) {
            return (node.getLeft() != null) ? node.getLeft() : node.getRight();
        }

        // Caso com 2 filhos: substitui pelo sucessor
        Node<T> successor = findMin(node.getRight());
        node.setData(successor.getData());
        node.setRight(doDelete(node.getRight(), successor.getData()));

        return node;
    }

    private void updateHeightAndBalanceFactor(AVLNode<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    private int height(Node<T> node) {
        return (node == null) ? -1 : ((AVLNode<T>) node).getHeight();
    }

    private int getBalance(AVLNode<T> node) {
        return (node == null) ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance((AVLNode<T>) node.getLeft()) < 0) {
                node.setLeft(leftRotate((AVLNode<T>) node.getLeft())); // Rotação Esquerda-Direita
            }
            return rightRotate(node); // Rotação Direita
        }

        if (balance < -1) {
            if (getBalance((AVLNode<T>) node.getRight()) > 0) {
                node.setRight(rightRotate((AVLNode<T>) node.getRight())); // Rotação Direita-Esquerda
            }
            return leftRotate(node); // Rotação Esquerda
        }

        return node;
    }

    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = (AVLNode<T>) y.getLeft();
        AVLNode<T> T2 = (AVLNode<T>) x.getRight();

        // Realizar rotação
        x.setRight(y);
        y.setLeft(T2);

        updateHeightAndBalanceFactor(y);
        updateHeightAndBalanceFactor(x);

        return x;
    }

    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = (AVLNode<T>) x.getRight();
        AVLNode<T> T2 = (AVLNode<T>) y.getLeft();

        // Realizar rotação
        y.setLeft(x);
        x.setRight(T2);

        updateHeightAndBalanceFactor(x);
        updateHeightAndBalanceFactor(y);

        return y;
    }


}

