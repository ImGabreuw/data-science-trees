package com.github.imgabreuw.backend.database.structure;

import com.github.imgabreuw.backend.database.structure.node.Node;
import com.github.imgabreuw.backend.database.structure.node.AVLNode;

public final class AVL<T extends Comparable<T>> extends AbstractIBinarySearchTree<T> {

    @Override
    protected Node<T> doInsert(Node<T> node, T data) {
        // Inserção normal de BST
        if (node == null) {
            return new AVLNode<>(data);
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(doInsert(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(doInsert(node.getRight(), data));
        } else {
            return node;
        }

        // Atualizar altura
        ((AVLNode<T>) node).setHeight(1 + Math.max(
                height(node.getLeft()),
                height(node.getRight())
        ));

        int balance = getBalance((AVLNode<T>) node);

        // Casos de balanceamento
        // Caso Esquerda-Esquerda
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rightRotate((AVLNode<T>) node);
        }

        // Caso Direita-Direita
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return leftRotate((AVLNode<T>) node);
        }

        // Caso Esquerda-Direita
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(leftRotate((AVLNode<T>) node.getLeft()));
            return rightRotate((AVLNode<T>) node);
        }

        // Caso Direita-Esquerda
        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rightRotate((AVLNode<T>) node.getRight()));
            return leftRotate((AVLNode<T>) node);
        }

        return node;
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
            // Nó com um ou nenhum filho
            if (node.getLeft() == null || node.getRight() == null) {
                Node<T> temp = null;
                if (temp == node.getLeft()) {
                    temp = node.getRight();
                } else {
                    temp = node.getLeft();
                }

                // Caso sem filhos
                if (temp == null) {
                    temp = node;
                    node = null;
                } else { // Caso com um filho
                    node = temp;
                }
            } else {
                // Nó com dois filhos
                Node<T> successor = findMin(node.getRight());
                node.setData(successor.getData());
                node.setRight(doDelete(node.getRight(), successor.getData()));
            }
        }

        // Se a árvore tinha apenas um nó
        if (node == null) {
            return null;
        }

        // Atualizar altura
        ((AVLNode<T>) node).setHeight(1 + Math.max(
                height(node.getLeft()),
                height(node.getRight())
        ));

        // Calcular fator de balanceamento
        int balance = getBalance((AVLNode<T>) node);

        // Casos de balanceamento
        // Caso Esquerda-Esquerda
        if (balance > 1 && getBalance((AVLNode<T>) node.getLeft()) >= 0) {
            return rightRotate((AVLNode<T>) node);
        }

        // Caso Esquerda-Direita
        if (balance > 1 && getBalance((AVLNode<T>) node.getLeft()) < 0) {
            node.setLeft(leftRotate((AVLNode<T>) node.getLeft()));
            return rightRotate((AVLNode<T>) node);
        }

        // Caso Direita-Direita
        if (balance < -1 && getBalance((AVLNode<T>) node.getRight()) <= 0) {
            return leftRotate((AVLNode<T>) node);
        }

        // Caso Direita-Esquerda
        if (balance < -1 && getBalance((AVLNode<T>) node.getRight()) > 0) {
            node.setRight(rightRotate((AVLNode<T>) node.getRight()));
            return leftRotate((AVLNode<T>) node);
        }

        return node;
    }

    private void updateHeightAndBalanceFactor(AVLNode<T> node) {
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return ((AVLNode<T>) node).getHeight();
    }

    private int getBalance(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = (AVLNode<T>) y.getLeft();
        AVLNode<T> T2 = (AVLNode<T>) x.getRight();

        // Realizar rotação
        x.setRight(y);
        y.setLeft(T2);

        // Atualizar alturas
        y.setHeight(1 + Math.max(height(y.getLeft()), height(y.getRight())));
        x.setHeight(1 + Math.max(height(x.getLeft()), height(x.getRight())));

        return x;
    }

    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = (AVLNode<T>) x.getRight();
        AVLNode<T> T2 = (AVLNode<T>) y.getLeft();

        // Realizar rotação
        y.setLeft(x);
        x.setRight(T2);

        // Atualizar alturas
        x.setHeight(1 + Math.max(height(x.getLeft()), height(x.getRight())));
        y.setHeight(1 + Math.max(height(y.getLeft()), height(y.getRight())));

        return y;
    }

}

