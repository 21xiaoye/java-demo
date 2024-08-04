package com.example.datastructure.AC;

import java.util.*;

/**
 * @Author yogurtzzz
 * @Date 2022/4/26 10:30
 *
 * AC 自动机
 **/
public class AcMachine {

    private AcNode root;

    /**
     * 用一个敏感词集合构建一个 AC 自动机
     * **/
    public AcMachine(Set<String> words) {
        root = new AcNode('0');
        buildTrieTree(words); // 先把 Trie 树建起来
        fillFailPointer(); // 再填充每个节点的 fail 指针
    }

    /**
     * 进行敏感词过滤
     * @param s 原字符串
     * @return 脱敏后的字符串
     * **/
    public String filterWithSensitiveWords(String s) {
        char[] cs = s.toCharArray();
        List<Integer> begins = new ArrayList<>();
        List<Integer> lens = new ArrayList<>();

        // 开始查找
        AcNode cur = root;
        for (int j = 0; j < cs.length; j++) {
            char c = cs[j];
            if (cur.hasChild(c)) {
                cur = cur.getOrCreateChild(c);
                if (cur.len != -1) {
                    // 该节点为结束节点
                    lens.add(cur.len);
                    begins.add(j - cur.len + 1);
                }
            } else {
                while (cur.fail != null) {
                    cur = cur.fail;
                    if (cur.hasChild(c)) {
                        cur = cur.getOrCreateChild(c);
                        if (cur.len != -1) {
                            lens.add(cur.len);
                            begins.add(j - cur.len + 1);
                        }
                        break;
                    }
                }
            }
        }

        // 查找出所有敏感词出现的起始位置和长度后, 对原字符串进行敏感词屏蔽
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int j = 0; j < begins.size(); j++) {
            int begin = begins.get(j);
            int len = lens.get(j);
            for (; i < begin; i++) sb.append(cs[i]);
            for (; i < begin + len; i++) sb.append('*');
        }
        while (i < cs.length) {
            sb.append(cs[i]);
            i++;
        }
        return sb.toString();
    }

    private void buildTrieTree(Set<String> words) {
        words.forEach(this::addWord);
    }

    private void addWord(String word) {
        AcNode p = root;
        char[] cs = word.toCharArray();
         for (char c : cs) {
            p = p.getOrCreateChild(c);
        }
        p.len = cs.length;
    }

    private void fillFailPointer() {
        // BFS 层序遍历填充 fail 指针
        Queue<AcNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            AcNode x = q.poll();
            if (x.children == null) continue;
            // 处理当前节点的子节点的fail指针
            x.children.values().forEach(node -> {
                q.offer(node); // 加入队列
                if (x == root) node.fail = root;
                else {
                    AcNode last = x.fail;
                    while (last != null) {
                        if (last.hasChild(node.c)) {
                            node.fail = last.children.get(node.c);
                            break;
                        } else {
                            last = last.fail;
                        }
                    }
                    if (last == null) node.fail = root;
                }
            });
        }
    }

    private static class AcNode {

        private char c;

        private int len = -1; // 如果是结尾节点, 记录长度

        private Map<Character, AcNode> children;

        private AcNode fail;

        AcNode(char c) {
            this.c = c;
        }

        AcNode getOrCreateChild(char c) {
            if (children == null) children = new HashMap<>();
            if (children.containsKey(c)) return children.get(c);
            AcNode newNode = new AcNode(c);
            children.put(c, newNode);
            return newNode;
        }

        boolean hasChild(char c) {
            return children != null && children.containsKey(c);
        }
    }

    public static void main(String[] args) {
        testEn();
        System.out.println();
        testZh();
    }

    private static void testEn() {
        Set<String> wordSet = new HashSet<>(Arrays.asList("she", "bleed", "dog", "doggy", "hurt"));
        AcMachine acMachine = new AcMachine(wordSet);
        String s = "A girl is bitten by a doggy, and she is badly hurt, bleeding along the road";
        System.out.printf("sensitive words : ");
        for (String x : wordSet) System.out.printf("%s ", x);
        System.out.println();
        System.out.println(s);
        String filteredString = acMachine.filterWithSensitiveWords(s);
        System.out.println(filteredString);
    }

    private static void testZh() {
        Set<String> wordSet = new HashSet<>(Arrays.asList("血", "政府", "暴力"));
        AcMachine acMachine = new AcMachine(wordSet);
        System.out.printf("sensitive words : ");
        for (String x : wordSet) System.out.printf("%s ", x);
        System.out.println();
        String s = "美国政府呼吁民众不要暴力, 因为暴力会造成流血";
        System.out.println(s);
        System.out.println(acMachine.filterWithSensitiveWords(s));

    }

}

