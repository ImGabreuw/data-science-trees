package com.github.imgabreuw.backend.database.structure.factory;

import com.github.imgabreuw.backend.database.structure.AVL;
import com.github.imgabreuw.backend.database.structure.AbstractIBinarySearchTree;

public class AVLStrategy implements TreeStrategy {

    @Override
    public <T extends Comparable<T>> AbstractIBinarySearchTree<T> createTree() {
        return new AVL<>();
    }

}
