package com.andersenlab.hotel.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CrudRepository<T>{

    void save(T t);

    Collection<T> findAll();

    void delete(UUID id);

    boolean hasIn(UUID t);


    Optional<T> getById(UUID t);


}
