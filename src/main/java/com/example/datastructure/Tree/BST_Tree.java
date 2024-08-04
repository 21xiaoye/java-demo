package com.example.datastructure.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BST_Tree<T extends Comparable<T>> implements BST_TreeInterface<T>{

    private BST_Node<T> mRoot;

    public BST_Tree() {
        this.mRoot = null;
    }
    //找结点(x)的后继结点。即，查找"二叉树中数据值大于该结点"的"最小结点"
    @Override
    public BST_Node<T> successor(BST_Node<T> root){
        if(root.right !=null){
            return minBstNode(root.right);
        }

        BST_Node<T> cur = root.parent;

        while ((cur!=null) && (root == cur.right)){
            root = cur;
            cur = cur.parent;
        }
        return cur;
    }
    // 找结点(x)的前驱结点。即，查找"二叉树中数据值小于该结点"的"最大结点"。
    @Override
    public BST_Node<T> predecessor(BST_Node<T> root){
        if(root.left!=null) return maxBstNode(root.left);

        BST_Node<T> cur = root.parent;

        while ((cur!=null) && (root == cur.left)){
            root = cur;
            cur = cur.parent;
        }
        return cur;

    }
    @Override
    public BST_Node<T> search(BST_Node<T> root, T item) {
        while (root!=null){
            int cmp = item.compareTo(root.item);
            if(cmp < 0) root = root.left;
            else if(cmp > 0) root = root.right;
            else return root;
        }
        return null;
    }
    @Override
    public BST_Node<T> minBstNode(BST_Node<T> root){
        if(root == null) return null;
        while (root.left!=null) root = root.left;
        return root;
    }

    @Override
    public BST_Node<T> maxBstNode(BST_Node<T> root) {
        if(root == null) return null;
        while (root.right!=null) root = root.right;
        return root;
    }

    @Override
    public List<List<T>> leverOrder(BST_Node<T> root) {
        ArrayList<List<T>> lists = new ArrayList<>();
        Queue<BST_Node<T>> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()){
            List<T> list = new ArrayList<>();
            int count = queue.size();

            while (count!=0) {
                count--;
                root = queue.poll();
                if(root == null){
                    list.add(null);
                    continue;
                }
                list.add(root.item);
                if(root.left!=null) queue.offer(root.left);
                else queue.offer(null);
                if(root.right!=null) queue.offer(root.right);
                else queue.offer(null);
            }
            lists.add(list);
        }
        return lists;
    }

    private static <T extends Comparable<T>> void insert(BST_Tree<T> bst, BST_Node<T> node){
        int cmp;

        BST_Node<T> cur = null;
        BST_Node<T> root = bst.mRoot;

        while (root!=null){
            cur = root;
            cmp = node.item.compareTo(root.item);

            if(cmp < 0) root = root.left;
            else root = root.right;
        }

        node.parent = cur;

        if(cur == null) bst.mRoot = node;
        else{
            cmp = node.item.compareTo(cur.item);

            if(cmp < 0) cur.left = node;
            else  cur.right = node;
        }
    }

    @Override
    public void insertion(T key) {
        BST_Node<T> tbst_node = new BST_Node<>(key, null, null, null);

        insert(this,tbst_node);
    }

    private static <T extends Comparable<T>> BST_Node<T> removeNode(BST_Tree<T> bst, BST_Node<T> root){
        BST_Node<T> cur, pre;

        // 只存在左右节点
        if(root.left == null || root.right == null) cur = root;

        // 存在左右结点，找到结点的前驱结点
        else cur = bst.predecessor(root);

        if(cur.left!=null){
            pre = cur.left;
        }else{
            pre = cur.right;
        }
        //要删除的结点只存在左结点或右节点，将左结点或右节点的父节点和要删除结点的父节点相连
        if(pre != null){
            pre.parent = cur.parent;
        }

        // 要删除的结点为根节点
        if (cur.parent == null)
            bst.mRoot = pre;

        // 前驱结点拥有左右结点，没有左右结点就指向空
        else if (cur == cur.parent.left)
            cur.parent.left = pre;
        else
            cur.parent.right = pre;

        // 改变要删除元素的数据
        if (cur != root)
            root.item = cur.item;
        return cur;
    }


    @Override
    public void remove(T key){
        BST_Node<T> cur, pre;

        if((pre = search(this.mRoot, key)) != null){
            cur = removeNode(this, pre);
            cur = null;
        }
    }

    public static <T extends Comparable<T>> void inOrder(BST_Node<T> node){
        if(node == null) return;
        inOrder(node.left);
        System.out.print(node.item+" ");
        inOrder(node.right);
    }

    public static <T extends Comparable<T>> void suOrder(BST_Node<T> node){
        if(node == null) return;
        System.out.print(node.item+" ");
        suOrder(node.left);
        suOrder(node.right);
    }

    static class BST_Node<T extends Comparable<T>>{
        private T item;
        private BST_Node<T> left;
        private BST_Node<T> right;
        private BST_Node<T> parent;

        public BST_Node(){}

        public BST_Node(T item, BST_Node<T> left, BST_Node<T> right, BST_Node<T> parent) {
            this.item = item;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getItem() {
            return item;
        }
        public void setItem(T item) {
            this.item = item;
        }
        public BST_Node<T> getLeft() {
            return left;
        }
        public void setLeft(BST_Node<T> left) {
            this.left = left;
        }
        public BST_Node<T> getRight() {
            return right;
        }
        public void setRight(BST_Node<T> right) {
            this.right = right;
        }
        public BST_Node<T> getParent() {
            return parent;
        }
        public void setParent(BST_Node<T> parent) {
            this.parent = parent;
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,5,4,3,2,6};
        BST_Tree<Integer> integerBST_tree = new BST_Tree<>();

        for (int j : arr) integerBST_tree.insertion(j);

        inOrder(integerBST_tree.mRoot);
        System.out.println();
        suOrder(integerBST_tree.mRoot);
        System.out.println();
        List<List<Integer>> lists = integerBST_tree.leverOrder(integerBST_tree.mRoot);
        System.out.println(lists);

        BST_Node<Integer> search = integerBST_tree.search(integerBST_tree.mRoot, 6);
        System.out.println("找到："+search.item);
        BST_Node<Integer> integerBST_node1 = integerBST_tree.minBstNode(integerBST_tree.mRoot);
        System.out.println("最小值为："+integerBST_node1.item);
        BST_Node<Integer> integerBST_node = integerBST_tree.maxBstNode(integerBST_tree.mRoot);
        System.out.println("最大值为:"+integerBST_node.item);
        integerBST_tree.remove(5);
        List<List<Integer>> lists1 = integerBST_tree.leverOrder(integerBST_tree.mRoot);
        System.out.println(lists1);
    }
}
