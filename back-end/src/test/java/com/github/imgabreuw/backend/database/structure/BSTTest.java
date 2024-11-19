package com.github.imgabreuw.backend.database.structure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BSTTest {

    private BST<Integer> underTest;

    @BeforeEach
    void setUp() {
        underTest = new BST<>();
    }

    @Test
    void testInsertAndSearch() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);

        assertThat(underTest.search(10)).isTrue();
        assertThat(underTest.search(5)).isTrue();
        assertThat(underTest.search(15)).isTrue();
        assertThat(underTest.search(20)).isFalse();
    }

    @Test
    void testDeleteLeafNode() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);

        underTest.delete(5);

        assertThat(underTest.search(5)).isFalse();
        assertThat(underTest.size()).isEqualTo(2);
    }

    @Test
    void testDeleteNodeWithOneChild() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(3);

        underTest.delete(5);
        assertThat(underTest.search(5)).isFalse();
        assertThat(underTest.search(3)).isTrue();
        assertThat(underTest.size()).isEqualTo(2);
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);
        underTest.insert(12);
        underTest.insert(18);

        underTest.delete(15);
        assertThat(underTest.search(15)).isFalse();
        assertThat(underTest.search(12)).isTrue();
        assertThat(underTest.search(18)).isTrue();
        assertThat(underTest.size()).isEqualTo(4);
    }

    @Test
    void testMinAndMax() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);
        underTest.insert(1);
        underTest.insert(20);

        assertThat(underTest.min()).isEqualTo(Optional.of(1));
        assertThat(underTest.max()).isEqualTo(Optional.of(20));
    }

    @Test
    void testSize() {
        assertThat(underTest.size()).isEqualTo(0);

        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);

        assertThat(underTest.size()).isEqualTo(3);
    }

    @Test
    void testIsEmpty() {
        assertThat(underTest.isEmpty()).isTrue();

        underTest.insert(10);

        assertThat(underTest.isEmpty()).isFalse();
    }

    @Test
    void testInOrderTraversal() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);
        underTest.insert(3);
        underTest.insert(7);

        List<Integer> inOrder = underTest.inOrderTraversal().toList();
        assertThat(inOrder).containsExactly(3, 5, 7, 10, 15);
    }

    @Test
    void testPreOrderTraversal() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);
        underTest.insert(3);
        underTest.insert(7);

        List<Integer> preOrder = underTest.preOrderTraversal().toList();
        assertThat(preOrder).containsExactly(10, 5, 3, 7, 15);
    }

    @Test
    void testPostOrderTraversal() {
        underTest.insert(10);
        underTest.insert(5);
        underTest.insert(15);
        underTest.insert(3);
        underTest.insert(7);

        List<Integer> postOrder = underTest.postOrderTraversal().toList();
        assertThat(postOrder).containsExactly(3, 7, 5, 15, 10);
    }

    @Test
    void testInsertNullThrowsException() {
        assertThatThrownBy(() -> underTest.insert(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("data cannot be null");
    }

    @Test
    void testDeleteNullThrowsException() {
        assertThatThrownBy(() -> underTest.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("data cannot be null");
    }

}
