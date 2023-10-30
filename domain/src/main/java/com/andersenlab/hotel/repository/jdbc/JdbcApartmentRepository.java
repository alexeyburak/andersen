package com.andersenlab.hotel.repository.jdbc;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.h2.api.H2Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcApartmentRepository implements SortableCrudRepository<Apartment, ApartmentSort> {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcApartmentRepository.class);
    private final JdbcConnector config;

    public JdbcApartmentRepository(JdbcConnector config) {
        this.config = config;
    }

    @Override
    public void save(Apartment entity) {
        final String query = """
                INSERT INTO apartment(
                price, capacity, availability, status, id)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            put(statement, entity);
            statement.execute();
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Save statement was not executed" + e.getMessage());
        }
    }

    @Override
    public Collection<Apartment> findAllSorted(ApartmentSort sort) {
        final List<Apartment> apartments = new ArrayList<>();
        final String query = String.format("""
                SELECT id, price, capacity, availability, status
                FROM apartment
                ORDER BY %s;
                """, sort.name());

        try (Connection connection = config.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                apartments.add(get(set));
            }
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Find all statement was not executed " + e.getMessage());
        }
        return apartments;
    }

    @Override
    public void delete(UUID id) {
        final String query = """
                DELETE FROM apartment
                WHERE id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, H2Type.UUID);
            statement.executeUpdate();
            LOG.info("Query executed. Apartment deleted: {}", id);
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Delete statement was not executed " + e.getMessage());
        }
    }

    @Override
    public boolean has(UUID id) {
        final String query = """
                SELECT COUNT(*) as quantity
                FROM APARTMENT
                WHERE id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, H2Type.UUID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("quantity") > 0;
            }
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Failed to check client existence: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Apartment> getById(UUID id) {
        final String query = """
                SELECT id, price, capacity, availability, status
                FROM apartment
                WHERE id = ?
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, H2Type.UUID);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return Optional.of(get(set));
            }
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Find all statement was not executed " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Apartment entity) {
        final String query = """
                UPDATE apartment
                SET price=?, capacity=?, availability=?, status=?
                WHERE id=?;
                """;

        try (Connection connection = config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            put(statement, entity);
            statement.execute();
        } catch (SQLException e) {
            throw new CouldNotExecuteSql("Update statement was not executed {}" + e.getMessage());
        }
    }

    @SneakyThrows
    private void put(PreparedStatement statement, Apartment apartment) {
        statement.setBigDecimal(1, apartment.getPrice());
        statement.setInt(2, apartment.getCapacity().intValue());
        statement.setBoolean(3, apartment.isAvailability());
        statement.setString(4, apartment.getStatus().toString());
        statement.setObject(5, apartment.getId(), H2Type.UUID);
    }

    @SneakyThrows
    private Apartment get(ResultSet resultSet) {
        return new Apartment(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getBigDecimal("price"),
                BigInteger.valueOf(resultSet.getInt("capacity")),
                resultSet.getBoolean("availability"),
                EnumUtils.getEnum(ApartmentStatus.class, resultSet.getString("status"))
        );
    }
}
