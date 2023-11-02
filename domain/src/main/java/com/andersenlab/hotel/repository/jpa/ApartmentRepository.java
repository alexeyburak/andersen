package com.andersenlab.hotel.repository.jpa;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class ApartmentRepository implements SortableCrudRepository<Apartment, ApartmentSort> {//
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    ApartmentRepository(String JPAUnitName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(JPAUnitName);
        entityManager = entityManagerFactory.createEntityManager();

    }

    @Override
    public void save(Apartment entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Collection<Apartment> findAllSorted(ApartmentSort sort) {
        String sortName = sort.name();
        return entityManager.createQuery("SELECT a FROM ApartmentEntityJPA a ORDER BY " + sortName.toLowerCase()).getResultList();
    }

    @Override
    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        Apartment apartment = entityManager.getReference(Apartment.class, id);
        entityManager.remove(apartment);
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean has(UUID id) {
        entityManager.getTransaction().begin();
        Apartment apartment = entityManager.getReference(Apartment.class, id);
        entityManager.getTransaction().commit();
        return ! (apartment.getId().toString().equals(""));
    }

    @Override
    public Optional<Apartment> getById(UUID id) {
        entityManager.getTransaction().begin();
        Apartment apartment = entityManager.find(Apartment.class, id);
        entityManager.getTransaction().commit();
        return Optional.ofNullable(apartment);
    }

    @Override
    public void update(Apartment entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }
}
