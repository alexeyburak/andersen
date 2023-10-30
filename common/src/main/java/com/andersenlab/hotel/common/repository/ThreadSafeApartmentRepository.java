package com.andersenlab.hotel.common.repository;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class ThreadSafeApartmentRepository implements SortableCrudRepository<Apartment, ApartmentSort> {

    private final SortableCrudRepository<Apartment, ApartmentSort> original;
    private final ReadWriteLockWrapper lock = new ReadWriteLockWrapper();

    public ThreadSafeApartmentRepository(SortableCrudRepository<Apartment, ApartmentSort> original) {
        this.original = original;
    }

    @Override
    public void save(Apartment entity) {
        lock.withWriteLock(() -> original.save(entity));
    }

    @Override
    public Collection<Apartment> findAllSorted(ApartmentSort sort) {
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
    public Optional<Apartment> getById(UUID id) {
        return lock.withReadLock(() -> original.getById(id));
    }

    @Override
    public void update(Apartment entity) {
        lock.withWriteLock(() -> original.update(entity));
    }
}
