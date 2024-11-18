package com.github.imgabreuw.backend.structure;

import com.github.imgabreuw.backend.database.structure.BST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BSTTest {

    private BST<Integer> bst;

    @BeforeEach
    void setUp() {
        bst = new BST<>();
    }

    @Test
    void testInsertAndSearch() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        assertThat(bst.search(10)).isTrue();
        assertThat(bst.search(5)).isTrue();
        assertThat(bst.search(15)).isTrue();
        assertThat(bst.search(20)).isFalse();
    }

    @Test
    void testDeleteLeafNode() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        bst.delete(5);

        assertThat(bst.search(5)).isFalse();
        assertThat(bst.size()).isEqualTo(2);
    }

    @Test
    void testDeleteNodeWithOneChild() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(3);

        bst.delete(5);
        assertThat(bst.search(5)).isFalse();
        assertThat(bst.search(3)).isTrue();
        assertThat(bst.size()).isEqualTo(2);
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(12);
        bst.insert(18);

        bst.delete(15);
        assertThat(bst.search(15)).isFalse();
        assertThat(bst.search(12)).isTrue();
        assertThat(bst.search(18)).isTrue();
        assertThat(bst.size()).isEqualTo(4);
    }

    @Test
    void testMinAndMax() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(1);
        bst.insert(20);

        assertThat(bst.min()).isEqualTo(Optional.of(1));
        assertThat(bst.max()).isEqualTo(Optional.of(20));
    }

    @Test
    void testSize() {
        assertThat(bst.size()).isEqualTo(0);

        bst.insert(10);
        bst.insert(5);
        bst.insert(15);

        assertThat(bst.size()).isEqualTo(3);
    }

    @Test
    void testIsEmpty() {
        assertThat(bst.isEmpty()).isTrue();

        bst.insert(10);

        assertThat(bst.isEmpty()).isFalse();
    }

    @Test
    void testInOrderTraversal() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);

        List<Integer> inOrder = bst.inOrderTraversal().toList();
        assertThat(inOrder).containsExactly(3, 5, 7, 10, 15);
    }

    @Test
    void testPreOrderTraversal() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);

        List<Integer> preOrder = bst.preOrderTraversal().toList();
        assertThat(preOrder).containsExactly(10, 5, 3, 7, 15);
    }

    @Test
    void testPostOrderTraversal() {
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);

        List<Integer> postOrder = bst.postOrderTraversal().toList();
        assertThat(postOrder).containsExactly(3, 7, 5, 15, 10);
    }

    @Test
    void testInsertNullThrowsException() {
        assertThatThrownBy(() -> bst.insert(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("data cannot be null");
    }

    @Test
    void testDeleteNullThrowsException() {
        assertThatThrownBy(() -> bst.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("data cannot be null");
    }

}
