package com.andersenlab.hotel.common.repository;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class ThreadSafeClientRepository implements SortableCrudRepository<Client, ClientSort> {

    private final SortableCrudRepository<Client, ClientSort> original;
    private final ReadWriteLockWrapper lock = new ReadWriteLockWrapper();

    public ThreadSafeClientRepository(SortableCrudRepository<Client, ClientSort> original) {
        this.original = original;
    }

    @Override
    public void save(Client entity) {
        lock.withWriteLock(() -> original.save(entity));
    }

    @Override
    public Collection<Client> findAllSorted(ClientSort sort) {
        return lock.withReadLock(() -> original.findAllSorted(sort));
    }

    @Override
    public void delete(UUID id) {
        lock.withWriteLock(() -> original.delete(id));
    }

    @Override
    public boolean has(UUID id) {
        return lock.withReadLock(() -> original.has(id));
    }

    @Override
    public Optional<Client> getById(UUID id) {
        return lock.withReadLock(() -> original.getById(id));
    }

    @Override
    public void update(Client entity) {
        lock.withWriteLock(() -> original.update(entity));
    }
}
