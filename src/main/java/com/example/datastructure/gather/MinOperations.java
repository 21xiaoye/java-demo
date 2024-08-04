package com.example.datastructure.gather;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MinOperations {
    public static int minOperations(int num1, int num2) {
        Set<Integer> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        visited.add(num1);
        queue.add(new Node(num1, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.value == 0) {
                return current.steps;
            }

            for (int i = 0; i <= 60; i++) {
                int nextValue = current.value + (1 - 2 * (i % 2)) * (1 << (i / 2)) * num2;
                if (!visited.contains(nextValue)) {
                    visited.add(nextValue);
                    queue.add(new Node(nextValue, current.steps + 1));
                }
            }
        }

        return -1;
    }

    static class Node {
        int value;
        int steps;

        public Node(int value, int steps) {
            this.value = value;
            this.steps = steps;
        }
    }

    public static void main(String[] args) {
        System.out.println(minOperations(3, -2)); // 输出：3
        System.out.println(minOperations(5, 7)); // 输出：-1
    }
}



