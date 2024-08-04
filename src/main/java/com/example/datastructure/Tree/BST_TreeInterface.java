package com.example.datastructure.Tree;

import java.util.List;

public interface BST_TreeInterface<T extends Comparable<T>> {
    BST_Tree.BST_Node<T> search(BST_Tree.BST_Node<T> root, T item);
    List<List<T>> leverOrder(BST_Tree.BST_Node<T> root);

    BST_Tree.BST_Node<T> successor(BST_Tree.BST_Node<T> root);
    BST_Tree.BST_Node<T> predecessor(BST_Tree.BST_Node<T> root);

    BST_Tree.BST_Node<T> minBstNode(BST_Tree.BST_Node<T> root);
    BST_Tree.BST_Node<T> maxBstNode(BST_Tree.BST_Node<T> root);

    void insertion(T key);
    void remove(T key);
}
