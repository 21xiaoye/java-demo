package com.example.datastructure.map;

public interface Map<K,V> {
    V put(K k, V v);

    V get(K k);

    int size();

    boolean isEmpty();

    interface Entry<K, V>{
        K getKey();
        V getValue();
    }
}
