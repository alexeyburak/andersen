package com.andersenlab.hotel.repository.infile;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Entity;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private Map<UUID, Apartment> getAll() {
        final Map<String, LinkedHashMap<String, Object>> map =
                (Map<String, LinkedHashMap<String, Object>>) getFullMap().get(Entity.APARTMENT.toString());

        return map.values()
                .stream()
                .collect(Collectors.toMap(
                        fieldMap -> UUID.fromString(fieldMap.get("id").toString()),
                        fieldMap -> new Apartment(
                                UUID.fromString(String.valueOf(fieldMap.get("id"))),
                                new BigDecimal(String.valueOf(fieldMap.get("price"))),
                                new BigInteger(String.valueOf(fieldMap.get("capacity"))),
                                Boolean.parseBoolean(String.valueOf(fieldMap.get("availability"))),
                                EnumUtils.getEnum(ApartmentStatus.class, String.valueOf(fieldMap.get("status")))
                        )));

    }

    private Map<String, Object> getFullMap() {
        Map<String, Object> fullMap = new HashMap<>();
        try {
            fullMap = jsonMapper.readValue(database, HashMap.class);
        } catch (IOException e) {
            LOG.warn("Failed to load resources");
        }
        if(!fullMap.containsKey(Entity.APARTMENT.toString())) {
            fullMap.put(Entity.APARTMENT.toString(), new LinkedHashMap<String, Object>());
        }
        return fullMap;
    }

    @SneakyThrows
    private void saveAll(Map<UUID, Apartment> apartmentMap) {
        Map<String, Object> fullMap = getFullMap();
        fullMap.put(Entity.APARTMENT.toString(), apartmentMap);
        jsonMapper.writeValue(database, fullMap);
    }
}
