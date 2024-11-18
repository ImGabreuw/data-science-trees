package com.github.imgabreuw.backend.database.structure.factory;

import com.github.imgabreuw.backend.database.structure.AbstractIBinarySearchTree;

public interface TreeStrategy {

    <T extends Comparable<T>> AbstractIBinarySearchTree<T> createTree();

}
