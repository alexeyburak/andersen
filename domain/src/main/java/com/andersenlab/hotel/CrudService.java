package com.andersenlab.hotel;

import java.util.UUID;

public interface CrudService<T, TEntity> {
    void save(T entity);
    void delete(UUID id);
    TEntity getById(UUID id);
    boolean has(UUID id);
    void update(T entity);
}
