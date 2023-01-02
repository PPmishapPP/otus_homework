package ru.otus.jdbc.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    WeakHashMap<K, V> weakHashMap = new WeakHashMap<>();
    List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        weakHashMap.put(key, value);
        notifyAll(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = weakHashMap.remove(key);
        notifyAll(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = weakHashMap.get(key);
        notifyAll(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyAll(K key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
