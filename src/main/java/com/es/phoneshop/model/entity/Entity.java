package com.es.phoneshop.model.entity;

public class Entity {
    private Long id;

    public Entity() {
    }

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Entity entity = (Entity) obj;
        return id.equals(entity.id);
    }

    @Override
    public String toString() {
        return "Entity { id = " + id + " }";
    }
}
