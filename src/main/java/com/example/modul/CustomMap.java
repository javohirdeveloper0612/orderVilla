package com.example.modul;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

public class CustomMap<K, V> implements Cloneable,Serializable, RandomAccess {

    private Map<K, List<V>> map = new HashMap<>();

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<V> values = new LinkedList<>();
            values.add(value);
            map.put(key, values);
        }
    }

    public List<V> get(K key) {
        return map.get(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public void remove(K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).remove(value);
            if (map.get(key).isEmpty()) {
                map.remove(key);
            }
        }
    }

    public boolean removeKey(K key) {
        if (map.containsKey(key)){
            map.remove(key);
            return true;
        }
        return false;
    }

    public Map<K, List<V>> getMap() {
        return map;
    }

    public void forEach(BiConsumer<? super K, ? super List<V>> action) {
        map.forEach(action);
    }


    @Override
    public CustomMap<K, V> clone() {
        try {
            CustomMap clone = (CustomMap) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
