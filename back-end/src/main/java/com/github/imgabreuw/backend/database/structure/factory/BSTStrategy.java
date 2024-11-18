package com.github.imgabreuw.backend.database.structure.factory;

import com.github.imgabreuw.backend.database.structure.AbstractIBinarySearchTree;
import com.github.imgabreuw.backend.database.structure.BST;

public class BSTStrategy implements TreeStrategy {

    @Override
    public <T extends Comparable<T>> AbstractIBinarySearchTree<T> createTree() {
        return new BST<>();
    }

}
