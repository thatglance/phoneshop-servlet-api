package com.es.phoneshop.model.entity;

public interface AbstractDao<T extends Entity> {
    T getEntity(Long id);
    void save(T entity);
    void delete(Long id);
}
