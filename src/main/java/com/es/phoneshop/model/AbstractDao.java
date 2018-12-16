package com.es.phoneshop.model;

public interface AbstractDao<T> {
    T get(Long id);
    void save(T object);
}
