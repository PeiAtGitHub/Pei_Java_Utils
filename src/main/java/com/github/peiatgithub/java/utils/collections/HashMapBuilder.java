package com.github.peiatgithub.java.utils.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;

import lombok.Getter;

/**
 * 
 * Conveniently build an instance of a HashMap
 * 
 * @author pei
 *
 */
public class HashMapBuilder<K, V> {

    @Getter
    private HashMap<K, V> theHashMap;

    private boolean linked = false;

    public HashMapBuilder() {
        theHashMap = new HashMap<>();
    }

    public HashMapBuilder(boolean linked) {
        if (linked) {
            theHashMap = new LinkedHashMap<>();
        } else {
            theHashMap = new HashMap<>();
        }
    }

    public HashMapBuilder(K key, V value) {
        theHashMap = new HashMap<>();
        theHashMap.put(key, value);
    }

    public HashMapBuilder(boolean linked, K key, V value) {
        if (linked) {
            theHashMap = new LinkedHashMap<>();
        } else {
            theHashMap = new HashMap<>();
        }
        theHashMap.put(key, value);
    }

    public HashMapBuilder<K, V> put(K key, V value) {
        this.theHashMap.put(key, value);
        return this;
    }

    public HashMap<K, V> build() {
        return getTheHashMap();
    }

}
