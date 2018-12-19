package com.es.phoneshop.model.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaoImpl<T extends Entity> implements AbstractDao<T> {

    protected final List<T> entities = new ArrayList<>();

    @Override
    public synchronized T getEntity(Long id) {
        return entities.stream().filter(entity -> entity.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public synchronized void save(T entity) {
        if (getEntity(entity.getId()) == null) {
            entities.add(entity);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        entities.removeIf(entity -> entity.getId().equals(id));
    }
}
