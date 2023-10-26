package com.andersenlab.hotel.repository.infile;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.Database;
import com.andersenlab.hotel.model.ClientSort;
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

public class InFileClientRepository implements SortableCrudRepository<Client, ClientSort> {

    private static final Logger LOG = LoggerFactory.getLogger(InFileClientRepository.class);
    private final File database;
    private final ObjectMapper jsonMapper;

    @SneakyThrows
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

    @SneakyThrows
    private Map<UUID, Client> getAll() {
        return jsonMapper.readValue(database, Database.class).clients();
    }

    @SneakyThrows
    private void saveAll(Map<UUID, Client> clientMap) {
        Database db = jsonMapper.readValue(database, Database.class);
        db.clients().clear();
        db.clients().putAll(clientMap);
        jsonMapper.writeValue(database, db);
        LOG.debug("File was successfully saved");
    }
}
