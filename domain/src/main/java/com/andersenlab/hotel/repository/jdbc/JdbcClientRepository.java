package com.andersenlab.hotel.repository.jdbc;

import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import lombok.SneakyThrows;
import org.h2.api.H2Type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class JdbcClientRepository implements SortableCrudRepository<Client, ClientSort> {

    private final JdbcConnector config;

    public JdbcClientRepository(JdbcConnector config) {
        this.config = config;
    }

    @Override
    public void save(Client entity) {
        final String query = """
                INSERT INTO client(id, name, status)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, entity.getId(), H2Type.UUID);
            statement.setObject(2, entity.getName(), H2Type.VARCHAR);
            statement.setObject(3, entity.getStatus().name(), H2Type.VARCHAR);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to save client: " + e.getMessage());
        }
    }

    @Override
    public Collection<Client> findAllSorted(ClientSort sort) {
        final String query = String.format("""
                        SELECT c.id AS client_id, c.name AS client_name, c.status AS client_status,
                        a.id AS apartment_id, a.price, a.capacity, a.availability, a.status AS apartment_status
                        FROM client c
                        LEFT JOIN client_apartment ca ON c.id = ca.client_id
                        LEFT JOIN apartment a ON ca.apartment_id = a.id
                        ORDER BY c.%s
                        """,
                sort.name().toLowerCase(Locale.ROOT)
        );
        Map<UUID, Client> clientMap = new HashMap<>();

        try (Connection connection = config.getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                Client dbClient = get(resultSet);
                final UUID id = dbClient.getId();

                Client client = clientMap.getOrDefault(id, dbClient);

                addApartmentCollection(resultSet, client);

                clientMap.put(id, client);
            }
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to retrieve clients: " + e.getMessage());
        }

        return clientMap.values();
    }

    @Override
    public void delete(UUID id) {
        final String deleteQuery = """
                DELETE FROM client
                WHERE id = ?
                """;
        final String deleteApartmentQuery = """
                DELETE FROM client_apartment
                WHERE client_id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
             PreparedStatement deleteApartmentStatement = connection.prepareStatement(deleteApartmentQuery)) {
            connection.setAutoCommit(false);

            deleteApartmentStatement.setObject(1, id, H2Type.UUID);
            deleteApartmentStatement.executeUpdate();

            deleteStatement.setObject(1, id, H2Type.UUID);
            deleteStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to delete client and their apartments: " + e.getMessage());
        }
    }

    @Override
    public boolean has(UUID id) {
        final String query = """
                SELECT COUNT(*)
                FROM client
                WHERE id = ?
                """;
        boolean exists = false;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, H2Type.UUID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to check client existence: " + e.getMessage());
        }

        return exists;
    }

    @Override
    public Optional<Client> getById(UUID id) {
        final String query = """
                SELECT c.name AS client_name, c.status AS client_status,
                a.id AS apartment_id, a.price, a.capacity, a.availability, a.status AS apartment_status
                FROM client c
                LEFT JOIN client_apartment ca
                ON c.id = ca.client_id
                LEFT JOIN apartment a
                ON ca.apartment_id = a.id
                WHERE c.id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, H2Type.UUID);
            ResultSet resultSet = statement.executeQuery();
            Client client = null;
            while (resultSet.next()) {
                if (client == null) {
                    String name = resultSet.getString("client_name");
                    ClientStatus status = ClientStatus.valueOf(resultSet.getString("client_status"));
                    client = new Client(id, name, status);
                }

                addApartmentCollection(resultSet, client);
            }
            return Optional.ofNullable(client);
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to retrieve client: " + e.getMessage());
        }
    }

    @Override
    public void update(Client entity) {
        final String updateQuery = """
                UPDATE client
                SET name = ?, status = ?
                WHERE id = ?
                """;
        final String insertApartmentQuery = """
                INSERT INTO client_apartment (client_id, apartment_id)
                VALUES (?, ?)
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement insertApartmentStatement = connection.prepareStatement(insertApartmentQuery)) {
            connection.setAutoCommit(false);

            final UUID entityId = entity.getId();
            updateStatement.setObject(1, entity.getName(), H2Type.VARCHAR);
            updateStatement.setObject(2, entity.getStatus().name(), H2Type.VARCHAR);
            updateStatement.setObject(3, entityId, H2Type.UUID);
            updateStatement.executeUpdate();

            deleteClientApartments(entityId);

            for (ApartmentEntity apartment : entity.getApartments()) {
                insertApartmentStatement.setObject(1, entityId, H2Type.UUID);
                insertApartmentStatement.setObject(2, apartment.id(), H2Type.UUID);
                insertApartmentStatement.addBatch();
            }

            insertApartmentStatement.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to update client and their apartments: " + e.getMessage());
        }
    }

    private void deleteClientApartments(UUID id) {
        final String deleteClientApartmentsQuery = """
                DELETE FROM client_apartment
                WHERE client_id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteClientApartmentsQuery)) {
            statement.setObject(1, id, H2Type.UUID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to delete client and their apartments: " + e.getMessage());
        }
    }

    @SneakyThrows
    private Client get(ResultSet resultSet) {
        return new Client(
                resultSet.getObject("client_id", UUID.class),
                resultSet.getString("client_name"),
                ClientStatus.valueOf(resultSet.getString("client_status"))
        );
    }

    private void addApartmentCollection(ResultSet resultSet, Client client) throws SQLException {
        UUID apartmentId = resultSet.getObject("apartment_id", UUID.class);
        if (apartmentId != null) {
            BigDecimal price = resultSet.getBigDecimal("price");
            long capacity = resultSet.getLong("capacity");
            boolean availability = resultSet.getBoolean("availability");
            ApartmentStatus apartmentStatus = ApartmentStatus.valueOf(resultSet.getString("apartment_status"));

            client.getApartments()
                    .add(new ApartmentEntity(apartmentId, price, BigInteger.valueOf(capacity), availability, apartmentStatus));
        }
    }

}
