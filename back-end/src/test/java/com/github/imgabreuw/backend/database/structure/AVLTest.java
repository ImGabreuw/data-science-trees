package com.github.imgabreuw.backend.database.structure;

import com.github.imgabreuw.backend.database.structure.node.AVLNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {

    private AVL<Integer> underTest;

    @BeforeEach
    void setUp() {
        underTest = new AVL<>();
    }

    @Test
    @DisplayName("Teste de inserção simples")
    void testSimpleInsertion() {
        underTest.insert(10);
        assertTrue(underTest.search(10));
        assertEquals(1, underTest.size());
    }

    @Test
    @DisplayName("Teste de inserção com rotação simples à direita")
    void testRightRotation() {
        underTest.insert(30);
        underTest.insert(20);
        underTest.insert(10);

        // Verificar se a árvore está balanceada
        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(20, root.getData());
        assertEquals(10, root.getLeft().getData());
        assertEquals(30, root.getRight().getData());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test
    @DisplayName("Teste de inserção com rotação simples à esquerda")
    void testLeftRotation() {
        underTest.insert(10);
        underTest.insert(20);
        underTest.insert(30);

        // Verificar se a árvore está balanceada
        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(20, root.getData());
        assertEquals(10, root.getLeft().getData());
        assertEquals(30, root.getRight().getData());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test
    @DisplayName("Teste de inserção com rotação dupla esquerda-direita")
    void testLeftRightRotation() {
        underTest.insert(30);
        underTest.insert(10);
        underTest.insert(20);

        // Verificar se a árvore está balanceada
        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(20, root.getData());
        assertEquals(10, root.getLeft().getData());
        assertEquals(30, root.getRight().getData());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test
    @DisplayName("Teste de inserção com rotação dupla direita-esquerda")
    void testRightLeftRotation() {
        underTest.insert(10);
        underTest.insert(30);
        underTest.insert(20);

        // Verificar se a árvore está balanceada
        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(20, root.getData());
        assertEquals(10, root.getLeft().getData());
        assertEquals(30, root.getRight().getData());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test
    @DisplayName("Teste de remoção simples")
    void testSimpleDeletion() {
        underTest.insert(10);
        underTest.insert(20);
        underTest.delete(10);

        assertFalse(underTest.search(10));
        assertTrue(underTest.search(20));
        assertEquals(1, underTest.size());
    }

    @Test
    @DisplayName("Teste de remoção com rebalanceamento")
    void testDeletionWithRebalancing() {
        underTest.insert(20);
        underTest.insert(10);
        underTest.insert(30);
        underTest.insert(40);
        underTest.delete(10);

        // Verificar se a árvore está balanceada após a remoção
        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(30, root.getData());
        assertEquals(20, root.getLeft().getData());
        assertEquals(40, root.getRight().getData());
        assertEquals(0, root.getBalanceFactor());
    }

    @Test
    @DisplayName("Teste de busca de mínimo e máximo")
    void testMinMax() {
        underTest.insert(20);
        underTest.insert(10);
        underTest.insert(30);
        underTest.insert(5);
        underTest.insert(35);

        Optional<Integer> min = underTest.min();
        Optional<Integer> max = underTest.max();

        assertTrue(min.isPresent());
        assertTrue(max.isPresent());
        assertEquals(5, min.get());
        assertEquals(35, max.get());
    }

    @Test
    @DisplayName("Teste de percurso em ordem")
    void testInOrderTraversal() {
        underTest.insert(20);
        underTest.insert(10);
        underTest.insert(30);
        underTest.insert(5);
        underTest.insert(35);

        List<Integer> inOrderList = underTest.inOrderTraversal()
                .collect(Collectors.toList());

        assertEquals(List.of(5, 10, 20, 30, 35), inOrderList);
    }

    @Test
    @DisplayName("Teste de inserção de valor nulo")
    void testNullInsertion() {
        assertThrows(IllegalArgumentException.class, () -> underTest.insert(null));
    }

    @Test
    @DisplayName("Teste de remoção de valor nulo")
    void testNullDeletion() {
        assertThrows(IllegalArgumentException.class, () -> underTest.delete(null));
    }

    @Test
    @DisplayName("Teste de árvore vazia")
    void testEmptyTree() {
        assertTrue(underTest.isEmpty());
        assertEquals(0, underTest.size());
        assertTrue(underTest.min().isEmpty());
        assertTrue(underTest.max().isEmpty());
    }

    @Test
    @DisplayName("Teste de altura da árvore")
    void testTreeHeight() {
        // Inserir valores que devem resultar em uma árvore balanceada
        underTest.insert(20);
        underTest.insert(10);
        underTest.insert(30);
        underTest.insert(5);
        underTest.insert(35);

        AVLNode<Integer> root = (AVLNode<Integer>) underTest.root;
        assertEquals(2, root.getHeight());
        assertTrue(Math.abs(root.getBalanceFactor()) <= 1);
    }
}