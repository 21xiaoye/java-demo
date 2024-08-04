package com.example.datastructure.graph;

import java.util.*;

public class Graph{

    private static final int DEFAULT_MATRIX_LENGTH = 5;
    private static int size;
    private static int unsize;
    private static HashSet<Node> nodes; // 有向图节点集合
    private static HashSet<Node> undirectedNodes; // 无向图节点集合

    private static HashSet<Edge> directedEdges; // 有向边集合
    public HashSet<Edge> undirectedEdges; // 无向边集合

    private static MatrixNode[] directedMatrix; // 有向图邻接表
    public MatrixNode[] undirectedMatrix; // 无向图邻接表

    public Graph(){
        Graph.nodes = new HashSet<>();
        Graph.undirectedNodes = new HashSet<>();

        Graph.directedEdges = new HashSet<>();
        undirectedEdges = new HashSet<>();

        Graph.directedMatrix = new MatrixNode[DEFAULT_MATRIX_LENGTH];
        undirectedMatrix  = new MatrixNode[DEFAULT_MATRIX_LENGTH];
    }
    /**
     * @return int
     * */
    public int nodesize(){
        return Graph.nodes.size();
    }
    public int undirectedEdgeSize(){ return undirectedEdges.size(); }
    public int directedEdgesSize(){ return Graph.directedEdges.size(); }
    public int undirectedNodesSize(){ return Graph.undirectedNodes.size(); }

    /**
     * @param index 下标
     * @return MatrixNode
    * */
    public MatrixNode getMatrix(int index) {
        if(index > size-1 || index < 0) throw new IllegalArgumentException();
        return Graph.directedMatrix[index];
    }

    public MatrixNode getMatrix(int index, MatrixNode[] matrixList){
        if(index < 0) throw  new IllegalArgumentException();
        return matrixList[index];
    }
    /**
     * @param edge 有向边
     * */
    private void insertDirectedEdge(Edge edge){
        Graph.directedEdges.add(edge);

        if(size >= Graph.directedMatrix.length-1) resize();

        if(Graph.nodes.add(edge.from)) Graph.directedMatrix[size++] = new MatrixNode(edge.from, 0, null);

        if(Graph.nodes.add(edge.to)) Graph.directedMatrix[size++] = new MatrixNode(edge.to, 0 ,null);

        edge.to.in++;
        edge.from.out++;

        add(edge.from, edge.to, edge.weight);
    }

    /**
     * @param edge 无向边
     * */
    private void insertUndirectedEdge(Edge edge){

        if(unsize >= undirectedMatrix.length-1) undirectedResize();

        if(Graph.undirectedNodes.add(edge.from)) undirectedMatrix[unsize++] = new MatrixNode(edge.from, 0, null);

        if(Graph.undirectedNodes.add(edge.to)) undirectedMatrix[unsize++] = new MatrixNode(edge.to, 0 , null);

        if(undirectedAdd(edge.from, edge.to, edge.weight)){
            this.undirectedEdges.add(edge);
            edge.to.in++;
            edge.to.out++;
            edge.from.in++;
            edge.from.out++;
        }

    }
    /**
     * @param firstNode tail
     * @param nextNode head
     * @date 2023/6/3
     * @email zouye0113@gmail.com
     * */
    private void add(Node firstNode, Node nextNode, int weights){
        int index = searchMatrixNode(firstNode);

        if(index == -1){
            int i = size;
            Graph.directedMatrix[size++] = new MatrixNode(firstNode,0 ,null);
            MatrixNode matrix = Graph.directedMatrix[i];
            matrix.next = new MatrixNode(nextNode,weights,null);
        }else{
            MatrixNode matrix = Graph.directedMatrix[index];

            while (matrix.next != null) matrix = matrix.next;

            matrix.next = new MatrixNode(nextNode, weights, null);
        }
    }

    private boolean undirectedAdd(Node firstNode, Node nextNode, int weights){
        int firstIndex = searchMatrixNode(firstNode, undirectedMatrix);
        int nextIndex = searchMatrixNode(nextNode, undirectedMatrix);

        MatrixNode firstMatrix = undirectedMatrix[firstIndex];
        MatrixNode nextMatrix = undirectedMatrix[nextIndex];

        if(isUndirectNode(firstMatrix, nextNode) && isUndirectNode(nextMatrix, firstNode)){
            while ( firstMatrix.next!=null){ firstMatrix = firstMatrix.next;}
            while ( nextMatrix.next!=null){ nextMatrix = nextMatrix.next; }
            firstMatrix.next = new MatrixNode(nextNode, weights, null);
            nextMatrix.next = new MatrixNode(firstNode, weights, null);
            return true;
        }
        return false;
    }

    private static boolean isUndirectNode(MatrixNode matrixNode, Node node){
        if(matrixNode.node == node) return false;
        while (matrixNode!=null){
            if(matrixNode.node == node) return false;
            matrixNode = matrixNode.next;
        }
        return true;
    }

    private void resize(){
        int oldLength = Graph.directedMatrix.length;
        Graph.directedMatrix = Arrays.copyOf(Graph.directedMatrix, oldLength << 1);
    }

    private void undirectedResize(){
        int oldLength = undirectedMatrix.length;
        undirectedMatrix = Arrays.copyOf(undirectedMatrix, oldLength << 1);
    }

    public int searchMatrixNode(Node node, MatrixNode[] matrixList){
        for (int i= 0; i< matrixList.length; i++) {
            MatrixNode matrixNode = matrixList[i];
            if (matrixNode.node == node) return i;
        }
        return -1;
    }

    /**
     * @param node 图节点
     * @return int
     * */
    private  int searchMatrixNode(Node node) {
        for (int i = 0; i < Graph.directedMatrix.length; i++) {
            MatrixNode matrix = Graph.directedMatrix[i];
            if (matrix.node == node) return i;
        }
        return -1;
    }

    /**
     * 生成一个随机节点数组
     * */
    private static List<Node> randomNodeList(int binCount){
        ArrayList<Node> list = new ArrayList<>();
        Random random = new Random();
        HashSet<Character> integers = new HashSet<>();
        int size = 0;
        while (size < binCount){

            // 生成'A' 到 'Z'
            int num = random.nextInt(26) + 65;
            if(integers.add((char)num)){
                Node node = new Node((char) num);
                list.add(node);
                ++size;
            }
        }
        return list;
    }
    /**
     * 生成随机有向边集合
     * */
    private static HashSet<Edge> randomEdge(){
        Random random = new Random();
        // 生成多少个随机节点
        int count = random.nextInt(26) + 1;

        List<Node> nodeList = randomNodeList(count);
        HashSet<Edge> edges = new HashSet<>();

        int len = nodeList.size();
        int index = 0;
        while (index < len){
            Node node = nodeList.get(index); // tail
            int anCount = random.nextInt(len);
            while (anCount!=0){
                int nextInt = random.nextInt(len); // 生成head，建立边
                Node current = nodeList.get(nextInt);
                if (current != node){ // head和tail相同，不建立边
                    int weights = random.nextInt(100)+1; // 生成随机权重
                    Edge edge = new Edge(weights, node, current);
                    // 避免建立相同的边
                    if(edges.add(edge)) anCount--;
                }
            }
            index++;
        }
        return edges;
    }

    /**
     * 生成随机无向图
     * */
    public static Graph randomUndirectedGraph(){
        HashSet<Edge> edges = randomEdge();
        Graph graph = new Graph();
        for (Edge edge : edges)
            graph.insertUndirectedEdge(edge);
        return graph;
    }

    /**
     * 生成随机有向图
     * */
    public static Graph randomDirectedGraph(){
        HashSet<Edge> edges = randomEdge();
        Graph graph = new Graph();
        for (Edge edge : edges)
            graph.insertDirectedEdge(edge);
        return graph;
    }


    // 对图中每一个节点进行DFS
    private void printDFS(){
        for (Node node : Graph.nodes){
            List<Character> dfs = DFS(node);
            System.out.println("从"+ node.value + "进行DFS" + dfs);
        }
    }

    /**
     * 打印有向图
     * */
    private void printDirectedGraph(){
        for (Edge edge : directedEdges)
            System.out.println(edge.from.value + "---->" + edge.to.value + "权重" + edge.weight);
    }

    /**
     * 打印无向图
     * */
    public void printUndirectedGraph(){
        TreeSet<Edge> edgeTreeSet = new TreeSet<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });
        edgeTreeSet.addAll(this.undirectedEdges);
        for (Edge edge : edgeTreeSet)
            System.out.println(edge.from.value + "----" + edge.to.value + "权重" + edge.weight);
    }

    // 邻接表广度优先遍历
    private List<List<Character>> BFS(Node node){
        ArrayList<List<Character>> list = new ArrayList<>(); // 保存遍历路径
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>(); // 保存已经全部遍历的节点
        if(node == null) return list;
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            if(!set.contains(node)){
                ArrayList<Character> arrayList = new ArrayList<>();
                int i = searchMatrixNode(node);
                MatrixNode matrix = Graph.directedMatrix[i];
                arrayList.add(matrix.node.value);
                while ((matrix = matrix.next)!= null){
                    queue.offer(matrix.node);
                    arrayList.add(matrix.node.value);
                }
                set.add(node);
                list.add(arrayList);
            }
        }
        return list;
    }

    // 深度优先遍历
    private List<Character> DFS(Node node){
        Stack<Node> stack = new Stack<>();
        ArrayList<Character> list = new ArrayList<>();
        HashSet<Node> set = new HashSet<>();
        if(node == null) return list;

        set.add(node);
        list.add(node.value);
        stack.push(node);
        while (!stack.isEmpty()){
            Node pop = stack.pop();
            int i = searchMatrixNode(pop);
            MatrixNode matrix = Graph.directedMatrix[i];
            while ((matrix = matrix.next)!=null){
                if(!set.contains(matrix.node)){
                    stack.push(pop);
                    stack.push(matrix.node);
                    set.add(matrix.node);
                    list.add(matrix.node.value);
                    break;
                }
            }
        }
        return list;
    }

    public List<Character> BFSGraph(Node node, MatrixNode[] matrixList){
        Queue<Node> queue = new LinkedList<>();
        ArrayList<Character> arrayList = new ArrayList<>();
        HashSet<Node> set = new HashSet<>();

        if(node == null) return arrayList;

        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            if(!set.contains(node)){
                int i = searchMatrixNode(node, matrixList);
                MatrixNode matrixNode = matrixList[i];
                arrayList.add(matrixNode.node.value);
                while ((matrixNode = matrixNode.next)!=null) {
                    queue.offer(matrixNode.node);
                }
                set.add(node);
            }
        }
        return arrayList;
    }
    /**
     * 普里姆算法prim，在加权连通图中寻找最小生成树
    */
    private void prim(Node node){
        int [][] list = new int[26][2];
        // 对数组进行初始化
        for (int[] ints : list)
            Arrays.fill(ints, Integer.MAX_VALUE);

        HashSet<MatrixNode> set = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        PriorityQueue<MatrixNode> nodePriorityQueue = new PriorityQueue<>(new Comparator<MatrixNode>() {
            @Override
            public int compare(MatrixNode o1, MatrixNode o2) {
                return o1.weights - o2.weights;
            }
        });

        for(MatrixNode matrixNode : undirectedMatrix){
            if(Objects.isNull(matrixNode)){
                break;
            }
            Node pre = matrixNode.node;
            if(!set.contains(matrixNode)){
                set.add(matrixNode);

                while ((matrixNode = matrixNode.next)!=null){
                    nodePriorityQueue.add(matrixNode);
                }
                while (!nodePriorityQueue.isEmpty()){
                    MatrixNode poll = nodePriorityQueue.poll();
                    if(!set.contains(poll)){
                        set.add(poll);
                        System.out.println(pre.value  +" ------ " + poll.node.value +"权重" + poll.weights);
                        MatrixNode undirectedMatrix = this.undirectedMatrix[searchMatrixNode(poll.node, this.undirectedMatrix)];
                        while ((undirectedMatrix = undirectedMatrix.next) !=null){
                            nodePriorityQueue.add(undirectedMatrix);
                        }
                    }
                }
            }
        }

//
//        nodePriorityQueue.add(Graph.undirectedMatrix[searchMatrixNode(node,undirectedMatrix)]);
//        if(!set.contains(node)){
//
//        }
//        while (!nodePriorityQueue.isEmpty()){
//            MatrixNode poll = nodePriorityQueue.poll();
//            while ((poll = poll.next)!=null){
//                nodePriorityQueue.add(poll);
//            }
//        }



//        stack.push(node);
//        list[ node.value-'A'][0] = node.value-'A';
//        list[ node.value-'A'][1]=Integer.MAX_VALUE;
//        while (!stack.isEmpty()){
//            node = stack.pop();
//            // 该节点已全部遍历，进行标记
//            set.add(node);
//            int i = searchMatrixNode(node, undirectedMatrix);
//            MatrixNode undirectedMatrix = Graph.undirectedMatrix[i];
//
//            while ((undirectedMatrix = undirectedMatrix.next) !=null){
//                if(!set.contains(undirectedMatrix.node) && undirectedMatrix.weights < list[(int)node.value-'A'][1]){
//                    list[(int)undirectedMatrix.node.value-'A'][0]= node.value-'A';
//                    list[(int)undirectedMatrix.node.value-'A'][1]= undirectedMatrix.weights;
//                    nodePriorityQueue.add(undirectedMatrix);
//                }
//            }
//            MatrixNode poll = nodePriorityQueue.poll();
//
//            assert poll != null;
//            stack.push(poll.node);
//            nodePriorityQueue.clear();
//
//        }
        System.out.println("最小生成树为：");
        int sum = 0;
        for (int i=0; i<list.length; i++){
            if(list[i][1]!=Integer.MAX_VALUE){
                sum += list[i][1];
                System.out.println("路径为"+(char)(i+'A')+"-----"+(char)(list[i][0]+'A')+"权重为"+list[i][1]);
            }
        }
        System.out.println("最小权重为"+sum);
    }

    private List<Character> DFSGraph(Node node, MatrixNode[] matrixList){
        Stack<Node> stack = new Stack<>();
        ArrayList<Character> list = new ArrayList<>();
        HashSet<Node> set = new HashSet<>();
        if(node == null) return list;

        stack.push(node);
        list.add(node.value);

        while (!stack.isEmpty()){
            node = stack.pop();
            int i = searchMatrixNode(node, matrixList);
            MatrixNode matrixNode = matrixList[i];
            while ((matrixNode = matrixNode.next)!=null){
                if(!set.contains(matrixNode.node)){
                    stack.push(node);
                    stack.push(matrixNode.node);
                    list.add(matrixNode.node.value);
                    break;
                }
            }
            set.add(node);
        }
        return list;
    }

    static class MatrixNode{
        private Node node;
        private int weights;
        private MatrixNode next;

        public MatrixNode(){}

        public MatrixNode(Node node, int weights, MatrixNode next) {
            this.node = node;
            this.weights = weights;
            this.next = next;
        }

        public Node getNode() {
            return node;
        }

        public void setNode(Node node) {
            this.node = node;
        }

        public int getWeights() {
            return weights;
        }

        public void setWeights(int weights) {
            this.weights = weights;
        }

        public MatrixNode getNext() {
            return next;
        }

        public void setNext(MatrixNode next) {
            this.next = next;
        }
    }

    static class Node{
        private char value;
        private int in; // 入度
        private int out;// 出度

        public Node(char value){
            this.value = value;
            in = 0;
            out = 0;
        }

        public Character getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public int getIn() {
            return in;
        }

        public int getOut() {
            return out;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value && in == node.in && out == node.out;
        }

        @Override
        public int hashCode() {
            int index;
            return (index = Objects.hash(value)) ^ (index >>>16);
        }
    }

    static class Edge{
        private int weight; // 权重
        private Node from; // 出发
        private Node to; // 终点

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Node getFrom() {
            return from;
        }

        public void setFrom(Node from) {
            this.from = from;
        }

        public Node getTo() {
            return to;
        }

        public void setTo(Node to) {
            this.to = to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

    }


    public static void main(String[] args) {

        Graph graph = randomUndirectedGraph();
        graph.printUndirectedGraph();
        MatrixNode matrix = graph.getMatrix(0, graph.undirectedMatrix);

        List<Character> characterList1 = graph.BFSGraph(matrix.node, graph.undirectedMatrix);
        System.out.println("广度优先遍历"+characterList1);

        List<Character> characterList = graph.DFSGraph(matrix.node, graph.undirectedMatrix);
        System.out.println("深度优先遍历"+characterList);
        graph.prim(matrix.node);
//        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new Comparator<Edge>() {
//            @Override
//            public int compare(Edge o1, Edge o2) {
//                return o1.weight - o2.weight;
//            }
//        });
//        Node node = new Node('A');
//        Node node1 = new Node('B');
//        Node node2 = new Node('C');
//        Node node3 = new Node('D');
//        Edge edge = new Edge(34, node, node1);
//        Edge edge1 = new Edge(45, node2, node3);
//
//        priorityQueue.add(edge);
//        priorityQueue.add(edge1);
//        Edge poll = priorityQueue.poll();
//        System.out.println(poll.from.value);

    }
}
