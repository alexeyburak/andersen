package com.andersenlab.hotel.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface SortableCrudRepository<E, S> {
    void save(E entity);

    Collection<E> findAllSorted(S sort);

    void delete(UUID id);

    boolean has(UUID id);

    Optional<E> getById(UUID id);

    void update(E entity);
}
