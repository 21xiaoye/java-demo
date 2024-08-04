package com.example.datastructure.graph;

import java.util.*;

public class Kruskal {
    public <K> int hash(K k){
        int index;
        return k == null ? 0 : (index = k.hashCode()) ^ (index >>> 16);
    }
    public List<Graph.Edge> kruskal(Graph graph){
        ArrayList<Graph.Edge> list = new ArrayList<>();
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(26);
        TreeSet<Graph.Edge> edgeTreeSet = new TreeSet<>(new Comparator<Graph.Edge>() {
            @Override
            public int compare(Graph.Edge o1, Graph.Edge o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        edgeTreeSet.addAll(graph.undirectedEdges);
        int sum = 0;
        int count =1;
        for (Graph.Edge edge : edgeTreeSet) {
            int x = (int)edge.getFrom().getValue()-'A';
            int y = (int)edge.getTo().getValue()-'A';
            if (disjointSetUnion.unionSet(x, y)) {
                sum += edge.getWeight();
                count++;
                list.add(edge);
                if (count == graph.undirectedNodesSize()) {
                    break;
                }
            }
        }
        return list;
    }
    public static void main(String[] args) {
        Graph graph = Graph.randomUndirectedGraph();
        graph.printUndirectedGraph();
        List<Character> characterList = graph.BFSGraph(graph.undirectedMatrix[0].getNode(), graph.undirectedMatrix);
        System.out.println("深度优先遍历"+characterList);
        Kruskal kruskal = new Kruskal();
        System.out.println("最小生成树：");
        List<Graph.Edge> kruskal1 = kruskal.kruskal(graph);
        for (Graph.Edge edge : kruskal1) {
            System.out.println(edge.getFrom().getValue() +" ---------- "+ edge.getTo().getValue() +"权重" + edge.getWeight());
        }
    }
    static class DisjointSetUnion {
        int[] f;
        int[] rank;
        int n;

        public DisjointSetUnion(int n) {
            this.n = n;
            this.rank = new int[n];
            Arrays.fill(this.rank, 1);
            this.f = new int[n];
            for (int i = 0; i < n; i++) {
                this.f[i] = i;
            }
        }
        // 进行路径压缩
        public int find(int x) {
            return f[x] == x ? x : (f[x] = find(f[x]));
        }

        // 未进行路径压缩
        public int finds(int x){
            if(f[x] == x){
                return x;
            }else{
                return find(f[x]);
            }
        }

        public boolean unionSet(int x, int y) {
            int fx = find(x), fy = find(y);
            if (fx == fy) {
                return false;
            }
            if (rank[fx] < rank[fy]) {
                int temp = fx;
                fx = fy;
                fy = temp;
            }
            rank[fx] += rank[fy];
            f[fy] = fx;
            return true;
        }
    }
}

