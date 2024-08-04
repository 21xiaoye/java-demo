package com.example.datastructure.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap<K,V> implements Map<K, V>, Serializable {

    //默认数组的大小，初始值为16
    static final int DEFAULT_LENGTH = 1 << 4;
    //默认的负载因子
    static final float DEFAULT_LOADER = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int TREEIFY_THRESHOLD = 8;
    //Entry数组
    @SuppressWarnings("unchecked")
    private  Node<K,V>[] table =(Node<K, V>[]) new Node[DEFAULT_LENGTH];
    //HashMap大小
    private int size;

    int threshold;

    final float loadFactor;

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    /**
     * 自定义哈希函数
     * 根据key的哈希值的低16位和高16位异或得出数组的下标
     * @param k k
     * @return int
     * */
     final int hash(K k){
        int index;
        return (k == null) ? 0 : (index = k.hashCode()) ^ (index >>> 16);
    }
    public HashMap(int threshold, float loadFactor){
        if(threshold<0) throw new IllegalArgumentException();

        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(threshold);
    }

    public HashMap(int threshold){
        this(threshold,DEFAULT_LOADER);
    }
    public HashMap(){
        this.loadFactor = DEFAULT_LOADER;
        this.threshold = DEFAULT_LENGTH;
    }
    @Override
    public V put(K k, V v) {
        if (size >= loadFactor * threshold) resize();

        int hash = hash(k);
        int i;
        Node<K, V> kvEntry;
        //没有元素存在
        if((kvEntry = table[i=(table.length - 1) & hash]) == null){
            table[i] = new Node<>(hash, k, v, null);
            ++size;
        }else{
            table[i] = new Node<>(hash, k, v, kvEntry);
        }
        return table[i].value;
    }

    @Override
    public V get(K k) {
        Node<K,V> kvNode;
        return (kvNode = getNode(hash(k),k)) ==null ? null : kvNode.value;
    }
    private Node<K, V> getNode(int hash, K k){
        Node<K, V> element, e;
        K key;
        if((element = table[(table.length - 1) & hash]) != null){
            if(element.hash == hash &&
                    ((key = element.key) == k || (k!=null && k.equals(key)))){
                return element;
            }
             // 找到真正需要的Node
            if((e = element.next) != null){
                do {
                    if(e.hash == hash &&
                            ((key = e.key) == k || (k!=null && k.equals(key))))
                        return e;
                }while ((e = e.next)!=null);
            }
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    private void resize(){
        Node<K,V>[] newTable =(Node<K, V>[])new Node[threshold << 1];
        rehash(newTable);
    }

    private void rehash(Node<K,V>[] newTable) {
        ArrayList<Node<K, V>> list = new ArrayList<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) continue;
            size=0;
            findEntryByNext(kvNode, list);
        }
        if(!list.isEmpty()){
            table = newTable;
            for (Node<K, V> node : list){
                if(node.next != null) node.next = null;
                put(node.getKey(), node.getValue());
            }
        }
    }

    private void findEntryByNext(Node<K,V> node, ArrayList<Node<K,V>> list) {
        do {
            list.add(node);
        }while ((node = node.next) != null);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public Node<K,V> getNode(K k) {
        Node<K,V> element;
        if((element=table[(table.length-1) & hash(k)])==null)
            return null;
        else return element;
    }

    public List<V> getNodeValueList(K k){
        Node<K,V> element;

        ArrayList<V> vsList = new ArrayList<>();

        if((element = table[(table.length-1) & hash(k)]) == null) return vsList;
        else{
            do {
                vsList.add(element.value);
            }while ((element = element.next) != null);
        }
        return vsList;
    }
    static class Node<K, V> implements Entry<K, V>{
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next =  next;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key + "=" + value ;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
        }
    }
}
