package com.github.peiatgithub.java.utils.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <pre>
 * Conveniently build an instance of HashMap or LinkedHashMap.
 * This class is different from the Java 9 Map.of(...) and Guava ImmutableMap.of(...) in:
 * 1. Created Map is explicitly type of HashMap or LinkedHashMap.
 * 2. This Builder can create LinkedHashMap for keeping entry insertion order. 
 * 3. Created Map is mutable
 * 4. Do not have the limit on the number of entries like Java 9 Map.of(...)(Max 10) 
 *    and Guava ImmutableMap.of(...)(Max 5)
 * 5. Need to call method build() at the end to get the created Map instance.
 *</pre>
 * @author pei
 */
public class MapBuilder{

    public static <K, V> HashMapBuilder<K, V> hashMap(K key, V value) {
        return new MapBuilder().getHashMapBuilder(key, value);
    }
    public static <K, V> LinkedHashMapBuilder<K, V> linkedHashMap(K key, V value) {
        return new MapBuilder().getLinkedHashMapBuilder(key, value);
    }

    //
    private <K, V> HashMapBuilder<K, V> getHashMapBuilder(K key, V value) {
        return new HashMapBuilder<K, V>(key, value);
    }
    private <K, V> LinkedHashMapBuilder<K, V> getLinkedHashMapBuilder(K key, V value) {
        return new LinkedHashMapBuilder<K, V>(key, value);
    }

    /**
     * @author pei
     */
    public class HashMapBuilder<K, V>{
        private HashMap<K, V> theHashMap;
        
        private HashMapBuilder(K key, V value) {
            this.theHashMap = new HashMap<>();
            this.theHashMap.put(key, value);
        }
        
        public HashMapBuilder<K, V> put(K key, V value) {
            this.theHashMap.put(key, value);
            return this;
        }
        
        public HashMap<K, V> build() {
            return this.theHashMap;
        }
    }
    
    /**
     * @author pei
     */
    public class LinkedHashMapBuilder<K, V>{
        private LinkedHashMap<K, V> theLinkedHashMap;
        
        private LinkedHashMapBuilder(K key, V value) {
            this.theLinkedHashMap = new LinkedHashMap<>();
            this.theLinkedHashMap.put(key, value);
        }
        
        public LinkedHashMapBuilder<K, V> put(K key, V value) {
            this.theLinkedHashMap.put(key, value);
            return this;
        }
        
        public LinkedHashMap<K, V> build() {
            return this.theLinkedHashMap;
        }
    }
}
