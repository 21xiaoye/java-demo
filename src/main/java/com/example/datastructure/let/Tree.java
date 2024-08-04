package com.example.datastructure.let;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
    /**
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[3],[9,20],[15,7]]
     *
     * 输入：root = [1]
     * 输出：[[1]]
     *
     * 输入：root = []
     * 输出：[]
     * */
    public static <T> List<List<Integer>> leverOrder(com.example.datastructure.Tree.Tree.Node<T> root) {
        ArrayList<List<Integer>> list = new ArrayList<List<Integer>>();
        if(root==null) return list;

        Queue<com.example.datastructure.Tree.Tree.Node<T>> queue =  new LinkedList<com.example.datastructure.Tree.Tree.Node<T>>();
        queue.offer(root);
        while(!queue.isEmpty()){
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            int count = queue.size();
            while(count > 0){
                com.example.datastructure.Tree.Tree.Node<T> node = queue.poll();
                assert node != null;
                list1.add((Integer) node.getValue());
                if(node.getLeft()!=null)
                    queue.offer(node.getLeft());

                if(node.getRight()!=null)
                    queue.offer(node.getRight());
                count--;
            }
            list.add(list1);
        }
        return list;
    }
    public static void main(String[] args) {
        Integer [] array={12,35,53,21,45};
        com.example.datastructure.Tree.Tree.Node<Integer> tNode = com.example.datastructure.Tree.Tree.initListBiTree(array, 0);
        List<List<Integer>> lists = leverOrder(tNode);
        System.out.println(lists.size());
    }
}
