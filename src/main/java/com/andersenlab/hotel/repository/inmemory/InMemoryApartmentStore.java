package com.andersenlab.hotel.repository.inmemory;

import com.andersenlab.hotel.repository.CrudRepository;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Slf4j
public class InMemoryApartmentStore implements CrudRepository<Apartment> {
    private static final Logger log = LoggerFactory.getLogger(InMemoryApartmentStore.class);

    private static InMemoryApartmentStore instance;
    private final Map<UUID, Apartment> apartments;

    protected InMemoryApartmentStore() {
        apartments = new HashMap<>();
    } //TODO ask about this when code-review

    public static InMemoryApartmentStore getInstance() {
        if(instance == null){
            instance = new InMemoryApartmentStore();
        }
        return instance;
    }


    @Override
    public void save(Apartment apartment) {
        if (apartments.containsKey(apartment.getId())){
            throw new ApartmentWithSameIdExists("Apartment with id " + apartment.getId() + " already exist");
        }
        else {
            apartments.put(apartment.getId(), apartment);
        }

    }

    @Override
    public Collection<Apartment> findAll() {
        return apartments.values().stream().toList();
    }

    @Override
    public void delete(Apartment apartment) {
        if (apartments.containsKey(apartment.getId())){
            apartments.remove(apartment.getId());
        }
        else {
            log.info("Object wasn't removed because of absence in data base");
        }
    }

    @Override
    public boolean hasIn(Apartment apartment) {
        return apartments.containsKey(apartment.getId());
    }

    @Override
    public Optional<Apartment> getById(UUID id) {
        if (!apartments.containsKey(id)) {
            log.info("No apartment with id " + id);
        }
        return Optional.ofNullable(apartments.get(id));
    }

}
