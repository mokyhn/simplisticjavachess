package com.simplisticjavachess.engine;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.HashMap;
import java.util.Map;

public class Cache<K,V> {
    CircularFifoQueue<K> circularFifoQueue;
    Map<K, V> cache;

    public Cache(int cacheSize) {
        circularFifoQueue = new CircularFifoQueue(cacheSize);
        cache = new HashMap<>();
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            return;
        }

        // Remove if needed
        if (circularFifoQueue.isAtFullCapacity()) {
            K elementToGo = circularFifoQueue.remove();
            cache.remove(elementToGo);
        }

        // Add
        circularFifoQueue.add(key);
        cache.put(key, value);

    }

    public V get(K key) {
        return cache.get(key);
    }
}
