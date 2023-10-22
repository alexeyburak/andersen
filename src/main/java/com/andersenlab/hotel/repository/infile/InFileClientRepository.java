package com.andersenlab.hotel.repository.infile;

import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.model.Entity;
import com.andersenlab.hotel.repository.ClientSort;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class InFileClientRepository implements SortableCrudRepository<Client, ClientSort> {

    private static final Logger LOG = LoggerFactory.getLogger(InFileClientRepository.class);
    private final File database;
    private final ObjectMapper jsonMapper;

    public InFileClientRepository(File database) {
        this.database = database;
        jsonMapper = new ObjectMapper();
    }

    @Override
    public void save(Client entity) {
        final Map<UUID, Client> clientMap = getAll();
        clientMap.put(entity.getId(), entity);
        saveAll(clientMap);
    }

    @Override
    public Collection<Client> findAllSorted(ClientSort sort) {
        return getAll().values()
                .stream()
                .sorted(sort.getComparator())
                .toList();
    }

    @Override
    public void delete(UUID id) {
        final Map<UUID, Client> clientMap = getAll();
        clientMap.remove(id);
        saveAll(clientMap);
    }

    @Override
    public boolean has(UUID id) {
        final Map<UUID, Client> clientMap = getAll();
        return clientMap.containsKey(id);
    }

    @Override
    public Optional<Client> getById(UUID id) {
        final Map<UUID, Client> clientMap = getAll();
        return Optional.ofNullable(clientMap.get(id));
    }

    @Override
    public void update(Client entity) {
        save(entity);
    }

    private Map<UUID, Client> getAll() {
        final Map<String, LinkedHashMap<String, Object>> map =
                (Map<String, LinkedHashMap<String, Object>>) getFullMap().get(Entity.CLIENT.toString());
        return map.values()
                .stream()
                .collect(Collectors.toMap(
                        fieldMap -> UUID.fromString(fieldMap.get("id").toString()),
                        fieldMap -> new Client(
                                UUID.fromString(String.valueOf(fieldMap.get("id"))),
                                String.valueOf(fieldMap.get("name")),
                                EnumUtils.getEnum(ClientStatus.class, String.valueOf(fieldMap.get("status"))),
                                toApartments((ArrayList<LinkedHashMap<String, Object>>) fieldMap.get("apartments"))
                        )));
    }

    private Set<ApartmentEntity> toApartments(final ArrayList<LinkedHashMap<String, Object>> apartmentSet) {
        return apartmentSet.stream().map(fieldMap -> new ApartmentEntity(
                UUID.fromString(String.valueOf(fieldMap.get("id"))),
                new BigDecimal(String.valueOf(fieldMap.get("price"))),
                new BigInteger(String.valueOf(fieldMap.get("capacity"))),
                Boolean.parseBoolean(String.valueOf(fieldMap.get("availability"))),
                EnumUtils.getEnum(ApartmentStatus.class, String.valueOf(fieldMap.get("status")))
        )).collect(Collectors.toSet());
    }

    private Map<String, Object> getFullMap() {
        Map<String, Object> fullMap = new HashMap<>();
        try {
            fullMap = jsonMapper.readValue(database, HashMap.class);
        } catch (IOException e) {
            LOG.warn("Failed to load resources");
        }
        if(!fullMap.containsKey(Entity.CLIENT.toString())) {
            fullMap.put(Entity.CLIENT.toString(), new LinkedHashMap<String, Object>());
        }
        return fullMap;
    }

    @SneakyThrows
    private void saveAll(Map<UUID, Client> clientMap) {
        Map<String, Object> fullMap = getFullMap();
        fullMap.put(Entity.CLIENT.toString(), clientMap);
        jsonMapper.writeValue(database, fullMap);
    }
}
