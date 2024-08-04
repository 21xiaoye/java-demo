package com.example.datastructure.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class prim {

    public void prims(Graph graph){
        int[][] list = new int[26][3];
        for (int[] item : list) Arrays.fill(item,Integer.MAX_VALUE);

        HashSet<Graph.Node> set = new HashSet<Graph.Node>();
        Stack<Graph.Node> stack = new Stack<>();
        Graph.MatrixNode undirectedMatrix = graph.undirectedMatrix[0];
        Graph.Node node = undirectedMatrix.getNode();
        stack.push(node);
        list[node.getValue()-'A'][0]= node.getValue()-'A';
        list[node.getValue()-'A'][1]= Integer.MIN_VALUE;
        list[node.getValue()-'A'][2]= 0;
        while (!stack.isEmpty()){
            node = stack.pop();
            int i = graph.searchMatrixNode(node, graph.undirectedMatrix);
            Graph.MatrixNode matrix = graph.getMatrix(i, graph.undirectedMatrix);
            while ((matrix = matrix.getNext())!=null){
//                System.out.print(matrix.getNode().getValue()+" ");
                if(!set.contains(matrix.getNode())){
                    if(matrix.getWeights() < list[(int)(matrix.getNode().getValue()-'A')][1]){
                        list[(int)(matrix.getNode().getValue()-'A')][0] = node.getValue()-'A';
                        list[(int)(matrix.getNode().getValue()-'A')][1] = matrix.getWeights();
                    }
                }
            }
            set.add(node);
            list[node.getValue()-'A'][2]=1;
            int minindex = Integer.MAX_VALUE;

            int index=-1;
            for (int j = 0; j < list.length; j++){
                if(list[j][1] < minindex && list[j][2] != 1){
                    minindex  = list[j][1];
                    index = j;
                }
            }
            for (int k=0; k< graph.undirectedMatrix.length; k++){
                if(index == -1) break;
                if(graph.undirectedMatrix[k].getNode().getValue() == (char)index+'A'){
                    stack.push(graph.undirectedMatrix[k].getNode());
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = Graph.randomUndirectedGraph();
        List<Character> characterList = graph.BFSGraph(graph.undirectedMatrix[0].getNode(), graph.undirectedMatrix);
        System.out.println("深度遍历"+characterList);
        prim prim = new prim();
        prim.prims(graph);
    }
}
