package com.example.datastructure.Tree;

import java.util.*;

public class HuffumanTree {
    protected  HuffumanNode mRoot;
    /**
     * create HuffmanTree
     * */
    public HuffumanTree(int[] array){
        MinHeap minHeap;
        HuffumanNode parent = null;
        minHeap = new MinHeap(array);
        for (int i=0; i<array.length-1; i++){
            HuffumanNode leftNode = minHeap.getMinHepMinElementAndRemove();
            HuffumanNode rightNode = minHeap.getMinHepMinElementAndRemove();
            parent = new HuffumanNode(leftNode.getKey() + rightNode.getKey(),leftNode,rightNode,null);
            leftNode.setParent(parent);
            rightNode.setParent(parent);

            minHeap.insert(parent);
        }
        mRoot = parent;
    }

    public  HuffumanTree(char[] array){
        MinHeap minHeap;
        HuffumanNode parent = null;
        minHeap = new MinHeap(array);
        for (int i=0; i<array.length-1; i++){
            HuffumanNode leftNode = minHeap.getMinHepMinElementAndRemove();
            HuffumanNode rightNode = minHeap.getMinHepMinElementAndRemove();
            parent = new HuffumanNode(leftNode.getKey() + rightNode.getKey(),' ',leftNode,rightNode,null);
            leftNode.setParent(parent);
            rightNode.setParent(parent);

            minHeap.insert(parent);
        }
        mRoot = parent;
    }
    public void preOrder(HuffumanNode node){
        if(node!=null){
            System.out.print(node.getKey()+" ");
            preOrder(node.getLeftNode());
            preOrder(node.getRightNode());
        }
    }
    //层序遍历返回一个数组
    public List<Integer> levelOrder(HuffumanNode node){
        Queue<HuffumanNode> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            node = queue.poll();
            list.add(node.getKey());
            if(node.getLeftNode()!=null)  queue.offer(node.getLeftNode());
            if(node.getRightNode()!=null) queue.offer(node.getRightNode());
        }
        return list;
    }

    public List<HuffumanNode> leverOrder(HuffumanNode node){
        Queue<HuffumanNode> queue = new LinkedList<>();
        List<HuffumanNode> list = new ArrayList<>();
        if(node == null) return list;
        queue.offer(node);

        while(!queue.isEmpty()){
            node = queue.poll();
            list.add(node);
            if(node.getLeftNode() != null) queue.offer(node.getLeftNode());
            if(node.getRightNode() != null) queue.offer(node.getRightNode());
        }
        return list;
    }

    //层序遍历返回数组，每层的元素用一个List数组进行存储
    public List<List<Integer>> levelOrderList(HuffumanNode node){
        ArrayList<List<Integer>> lists = new ArrayList<>();
        Queue<HuffumanNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            int count = queue.size();
            List<Integer> list = new ArrayList<>();
            while (count != 0){
                node = queue.poll();
                assert node!=null;
                list.add(node.getKey());
                if(node.getLeftNode() != null) queue.offer(node.getLeftNode());
                if(node.getRightNode() != null) queue.offer(node.getRightNode());
                count--;
            }
            lists.add(list);
        }
        return lists;
    }
    /**
     * @param node huffumanTree of root
     * @return huffumanCoding HashMap
     * */
    public static HashMap<Character,List<Character>> huffumanCoding(HuffumanNode node){
        HashMap<Character, List<Character>> characterListHashMap = new HashMap<>();
        Queue<HuffumanNode> queue = new LinkedList<>();
        if(node == null) return characterListHashMap;
        queue.offer(node);
        while (!queue.isEmpty()){
            List<Character> characterList;
            node = queue.poll();
            if(node.getValue() != ' ') {
                characterList = charCoding(node);
                characterListHashMap.put(node.getValue(),characterList);
            }
            if(node.getLeftNode()!=null) queue.offer(node.getLeftNode());
            if(node.getRightNode()!=null) queue.offer(node.getRightNode());
        }
        return characterListHashMap;
    }
    private static List<Character> charCoding(HuffumanNode node){
        List<Character> list = new ArrayList<>();
        if(node == null) return list;
        HuffumanNode parent;
        while (node.getParent()!=null){
            parent = node.getParent();
            if(parent.getLeftNode()==node) list.add('0');
            if(parent.getRightNode()==node) list.add('1');
            node = parent;
        }
        //因为是从下往上，所以得到的编码是倒序的，这里进行逆序
        Collections.reverse(list);
        return list;
    }
    /**
     * @param listHashMap HuffumanCoding HashMap
     * @param huffumanString Huffman codes that need to be decoded
     * @return String
     * */
    public static String decoding(String huffumanString, HashMap<Character,List<Character>> listHashMap){
        if(huffumanString.length() == 0) return " ";
        StringBuilder stringBuilder = new StringBuilder();
        List<Character> list= new ArrayList<>();
        for (int i=0,len = huffumanString.length(); i < len; i++) {
            list.add(huffumanString.charAt(i));
            Character key = getKey(listHashMap, list);
            if(key!=null){
                stringBuilder.append(key);
                list.clear();
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 合并两个数组
     * */
    @SafeVarargs
    private static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
    /**
     * 获取字符串的huffumanCoding
     * @param str 需要转码的字符串;
     * @param listHashMap huffumanCoding HashMap
     * @return List<Character>;
     * */
    public static String stringHuffumanCoding(String str,HashMap<Character,List<Character>> listHashMap){
        ArrayList<Character> characters = new ArrayList<>();
        if(str.length() == 0) return "";
        for (int i=0, len=str.length(); i<len; i++){
            List<Character> characterList = listHashMap.get(str.charAt(i));
            characters.addAll(characterList);
        }
        return valueOf(characters);
    }

    private static <T,V> T getKey(Map<T,V> map, Object value){
        T t= null;
        for(T key: map.keySet()){
            if(map.get(key).equals(value)){
                t = key;
            }
        }
        return t;
    }
    private static <T> String valueOf(List<T> arr){
        StringBuilder stringBuilder = new StringBuilder();
        for (T num : arr)
            stringBuilder.append(num);
        return stringBuilder.toString();
    }
    public static void printHuffmanTree(List<List<Integer>> list){
        for (List<Integer> integers : list) {
            System.out.println(integers.toString());
        }
    }
    public static  void main(String[] args) {
        long l = System.currentTimeMillis();
        char[] array = new char[58];
        int index =0;
        for (char i='A'; i <='z'; i++)
            array[index++]= i;
        HuffumanTree huffumanTree = new HuffumanTree(array);
        HashMap<Character, List<Character>> characterListHashMap = huffumanCoding(huffumanTree.mRoot);
        for (Map.Entry<Character, List<Character>> listEntry : characterListHashMap.entrySet()) {
            System.out.print("字符   " + listEntry.getKey() + "   的huffumanCoding为：" + listEntry.getValue());
            System.out.println();
        }
        String str = "abc[g]re[]grgbvSFMM";
        String characterList = stringHuffumanCoding(str, characterListHashMap);
        System.out.println(characterList);
        String decoding = decoding(characterList, characterListHashMap);
        System.out.println(decoding);
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);
    }
}