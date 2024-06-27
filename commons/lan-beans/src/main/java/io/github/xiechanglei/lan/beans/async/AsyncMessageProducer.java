package io.github.xiechanglei.lan.beans.async;

public interface AsyncMessageProducer<K, T> {

    T await(K key, long timeout) throws Exception;

    void put(K key, T response);
}
