package com.example.datastructure.Tree;


import java.util.*;

public class Tree<T> {
    //传入数组创建一个二叉树
    public static <T> Node<T> initListBiTree(T[] array, int num){
        //去除数组最后无意义的null元素
        while(array[array.length-1]==null)
            array = removeListLastElement(array);

        Node<T> integerNode = new Node<>(array[num]);
        if(num*2+1<array.length){
            integerNode.leftNode=initListBiTree(array,num*2+1);
        }
        if(num *2+2<array.length){
            integerNode.rightNode=initListBiTree(array,num*2+2);
        }
        return (Node<T>) integerNode;
    }
    // 删除数组最后一个元素
    public static <T> T[] removeListLastElement(T[] array){
        return Arrays.copyOf(array,array.length-1);
    }
    //递归先序遍历二叉树
    public static <T> void rPreOrder(Node<T> node){
        if(node==null) return;
        System.out.print(node.value+" ");
        rPreOrder(node.leftNode);
        rPreOrder(node.rightNode);
    }
    //递归中序遍历二叉树
    public static <T> void rInOrder(Node<T> node){
        if(node==null) return;
        rInOrder(node.leftNode);
        System.out.print(node.value+" ");
        rInOrder(node.rightNode);
    }
    //递归后序遍历二叉树
    public static <T>  void rSubOrder(Node<T> node){
        if(node==null) return;
        rSubOrder(node.leftNode);
        rSubOrder(node.rightNode);
        System.out.print(node.value+" ");
    }
    /**
     * 使用栈对树进行先中后序遍历
     * */
    public static <T> void stackPreOrder(Node<T> node){
        Stack<Node<T>> nodes = new Stack<>();
        if(node==null) return;
        while (!nodes.isEmpty() || node!=null){
            while(node!=null){
                System.out.print(node.value+" ");
                nodes.push(node);
                node = node.leftNode;
            }
            node = nodes.pop();
            node=node.rightNode;
        }
    }
    public static <T> void stackInOrder(Node<T> node){
        Stack<Node<T>> nodes = new Stack<>();
        if(node==null) return;

        while(!nodes.isEmpty() || node!=null){
            while(node!=null){
                nodes.push(node);
                node=node.leftNode;
            }
            node = nodes.pop();
            System.out.print(node.value+" ");
            node = node.rightNode;
        }
    }

    public static <T> void stackSubOrder(Node<T> node) {
        Stack<Node<T>> stack1 = new Stack<Node<T>>();
        Stack<Node<T>> stack2 = new Stack<Node<T>>();
        stack1.push(node);
        while(!stack1.isEmpty() && node!=null){
            node = stack1.pop();
            stack2.push(node);
            if(node.leftNode!=null){
                stack1.push(node.leftNode);
            }
            if(node.rightNode!=null){
                stack1.push(node.rightNode);
            }
        }
        while(!stack2.isEmpty()){
            node = stack2.pop();
            System.out.print(node.value+" ");
        }
    }

    public static <T> void stackSubOrder2(Node<T> node){
        Stack<Node<T>> nodes = new Stack<>();
        Node<T> tNode = null;

        while(node!=null || !nodes.isEmpty()){
            //将左结点压栈
            while(node!=null){
                nodes.push(node);
                node=node.leftNode;
            }
            node=nodes.pop();
            //没有右节点就弹出栈，否则将右结点压栈
            if(node.rightNode==null || node.rightNode==tNode){
                System.out.print(node.value+" ");
                tNode=node;
                node=null;
            }else{
                nodes.push(node);
                node=node.rightNode;
            }
        }
    }
    /**
     * 层次遍历二叉树
     * */
    public static <T> void levelOrder(Node<T> node){
        Queue<Node<T>> nodes = new LinkedList<>();
        nodes.offer(node);
        while (!nodes.isEmpty()){
            Node<T> poll = nodes.poll();
            System.out.print(poll.value+" ");
            if(poll.leftNode!=null)
                nodes.offer(poll.leftNode);
            if(poll.rightNode!=null)
                nodes.offer(poll.rightNode);
        }
    }
    /**
     * 层次遍历返回一个数组
     * */
    public static <T> List<T> leverOrderList(Node<T> node){
        ArrayList<T> list = new ArrayList<>();
        if(node == null) return list;
        Queue<Node<T>> nodes = new LinkedList<>();
        nodes.offer(node);
        while(!nodes.isEmpty()){
            node = nodes.poll();
            assert node!=null;
            list.add(node.value);
            if(node.leftNode!=null) nodes.offer(node.leftNode);
            if(node.rightNode!=null) nodes.offer(node.rightNode);
        }
        return list;
    }
    /**
     * 递归二叉树最大深度
     * */
    public static <T> int rMaxDepth(Node<T> node){
        if(node == null) return 0;
        int leftMaxDepth = rMaxDepth(node.leftNode);
        int rightMaxDepth = rMaxDepth(node.rightNode);
        return Math.max(leftMaxDepth,rightMaxDepth) + 1;
    }
    /**
     * 使用层次遍历求二叉树最大深度
    * */
    public static <T> int maxDepth(Node<T> node){
        if(node == null) return 0;
        LinkedList<Node<T>> nodeQueue = new LinkedList<Node<T>>();
        nodeQueue.offer(node);
        int ans = 0;
        while(!nodeQueue.isEmpty()){
            int count = nodeQueue.size();
            while(count != 0){
                node = nodeQueue.poll();
                assert node != null;
                if(node.leftNode != null) nodeQueue.offer(node.leftNode);
                if(node.rightNode != null) nodeQueue.offer(node.rightNode);
                count--;
            }
            ans++;
        }
        return ans;
    }
    /**
     * 判断两颗树是否相同
     * */
    public static <T> boolean rIsSameTree(Node<T> node, Node<T> root){
        if(node == null && root == null) return true;
        if(node==null || root==null) return false;

        if(node.value == root.value){
            return rIsSameTree(node.leftNode,root.leftNode) && rIsSameTree(node.rightNode,root.rightNode);
        }else
            return false;
    }
    /**
     * 先序遍历判断两颗二叉树是否相等
    * */
    public static <T> boolean isSameTreePreOrder(Node<T> node, Node<T> root){
        if(node == null && root == null) return true;
        if(node == null || root == null) return false;
        Stack<Node<T>> stackNode = new Stack<>();
        Stack<Node<T>> stackRoot = new Stack<>();
        while(node!=null || root!=null || !stackNode.isEmpty() || !stackRoot.isEmpty()){
            while(node !=null && root!=null){
                stackNode.push(node);
                stackRoot.push(root);
                if(node.value != root.value) return false;
                node = node.leftNode;
                root = root.leftNode;
            }
            if(node == null && root == null){
                node = stackNode.pop();
                root = stackRoot.pop();
                node = node.rightNode;
                root = root.rightNode;
            }else return false;
        }
        return true;
    }
    /**
     * 中序遍历判断两颗二叉树是否相等
     * */
    public static <T> boolean isSameTreeInOrder(Node<T> node, Node<T> root){
        if(node == null && root == null) return true;
        if(node == null || root == null) return false;
        Stack<Node<T>> stackNode = new Stack<>();
        Stack<Node<T>> stackRoot = new Stack<>();
        while(node!=null || root!=null || !stackNode.isEmpty() || !stackRoot.isEmpty()){
            while(node !=null && root!=null){
                stackNode.push(node);
                stackRoot.push(root);
                node = node.leftNode;
                root = root.leftNode;
            }
            if(node == null && root == null){
                node = stackNode.pop();
                root = stackRoot.pop();
                if(node.value != root.value) return false;
                node = node.rightNode;
                root = root.rightNode;
            }else return false;
        }
        return true;
    }
    /**
     * 后序遍历判断两棵二叉树是否相等
     * */
    public static <T> boolean isSametreeSubOrder(Node<T> p, Node<T> q){
        if (p == null && q == null) return true;
        if (p == null || q==null) return false;
        Stack<Node<T>> stackP = new Stack<>();
        stackP.push(p);
        Node<T> pPre = null;
        Stack<Node<T>> stackQ = new Stack<>();
        stackQ.push(q);
        Node<T> qPre = null;
        while (!stackP.isEmpty() || !stackQ.isEmpty()) {
            p = stackP.peek();
            q = stackQ.peek();
            if (((pPre != null && (p.leftNode == pPre || p.rightNode == pPre)) || (p.leftNode == null && p.rightNode == null)) &&
                    ((qPre != null && (q.leftNode == qPre || q.rightNode == qPre)) || (q.leftNode == null && q.rightNode == null))) {
                if (p.value != q.value) {
                    return false;
                }
                pPre = p;
                qPre = q;
                stackP.pop();
                stackQ.pop();
            } else {
                if (p.rightNode != null && q.rightNode != null) {
                    stackP.push(p.rightNode);
                    stackQ.push(q.rightNode);
                } else if (p.rightNode != null || q.rightNode != null) {
                    return false;
                }

                if (p.leftNode != null && q.leftNode != null) {
                    stackP.push(p.leftNode);
                    stackQ.push(q.leftNode);
                } else if (p.leftNode != null || q.leftNode != null) {
                    return false;
                }
            }
        }
        return true;
    }
    //树结点
    public static class Node<T> {
        private T value;//结点存储的数据
        private Node<T> leftNode;//左结点
        private Node<T> rightNode;//右节点
        private static int size;

        public T getValue() {
            return value;
        }
        public void setValue(T value) {
            this.value = value;
        }
        public Node<T> getLeft() {
            return leftNode;
        }
        public void setLeft(Node<T> leftNode) {
            this.leftNode = leftNode;
        }
        public Node<T> getRight() {
            return rightNode;
        }
        public void setRight(Node<T> rightNode) {
            this.rightNode = rightNode;
        }
        public  Node(){}
        public Node(T t){
            this.value=t;
            if(this.value!=null) size++;
            this.leftNode=null;
            this.rightNode=null;
        }
        public boolean isEmpty(Node<T> node){
            return node == null;
        }
        public int size(){
            return size;
        }
    }
}