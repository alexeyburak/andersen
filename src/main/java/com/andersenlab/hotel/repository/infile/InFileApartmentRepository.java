package com.andersenlab.hotel.repository.infile;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.Database;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InFileApartmentRepository implements SortableCrudRepository<Apartment, ApartmentSort> {
    private static final Logger LOG = LoggerFactory.getLogger(InFileApartmentRepository.class);
    private final File database;
    private final ObjectMapper jsonMapper;

    public InFileApartmentRepository(File database) {
        this.database = database;
        jsonMapper = new ObjectMapper();
    }

    @Override
    public void save(Apartment entity) {
        final Map<UUID, Apartment> apartmentMap = getAll();
        apartmentMap.put(entity.getId(), entity);
        saveAll(apartmentMap);
    }

    @Override
    public Collection<Apartment> findAllSorted(ApartmentSort sort) {
        return getAll().values()
                .stream()
                .sorted(sort.getComparator())
                .toList();
    }

    @Override
    public void delete(UUID id) {
        final Map<UUID, Apartment> apartmentMap = getAll();
        apartmentMap.remove(id);
        saveAll(apartmentMap);
    }

    @Override
    public boolean has(UUID id) {
        final Map<UUID, Apartment> apartmentMap = getAll();
        return apartmentMap.containsKey(id);
    }

    @Override
    public Optional<Apartment> getById(UUID id) {
        final Map<UUID, Apartment> apartmentMap = getAll();
        return Optional.ofNullable(apartmentMap.get(id));
    }

    @Override
    public void update(Apartment entity) {
        save(entity);
    }

    @SneakyThrows
    private Map<UUID, Apartment> getAll() {
        return jsonMapper.readValue(database, Database.class).apartments();
    }

    @SneakyThrows
    private void saveAll(Map<UUID, Apartment> apartmentMap) {
        Database db = jsonMapper.readValue(database, Database.class);
        db.apartments().clear();
        db.apartments().putAll(apartmentMap);
        jsonMapper.writeValue(database, db);
        LOG.debug("File was successfully saved");
    }
}
