package com.andersenlab.hotel.repository.jpa;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.ApartmentStatus;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaApartmentRepositoryTest {
    static JpaApartmentRepository repository;
    Apartment apartmentWithoutId;

    @BeforeAll
    @SneakyThrows
    static void beforeAll(){

    }

    @BeforeEach
    @SneakyThrows
    void setUp() {
        repository = new JpaApartmentRepository(JpaUnitNameFromPersistentXml.nameOfJPAUnitFromPersistenceFile());
        apartmentWithoutId = new Apartment(BigDecimal.valueOf(1.0), BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
    }

    @AfterEach
    void tearDown() {
        repository.truncateTable();
    }

    @Test
    void save_SingleEntity_ShouldSaveEntityInDBTable() {
        System.out.println("HELLO");
        repository.save(apartmentWithoutId);
        Optional<Apartment> apartment = repository.getById(apartmentWithoutId.getId());

        assertThat(apartment.get().getId().equals("")).isFalse();
    }

    @Test
    void findAllSorted_EntityId_ShouldSortEntitiesFromDBTableById() {
        ApartmentSort sort = ApartmentSort.ID;
        Apartment apartment1 = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                new BigDecimal(1),BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        Apartment apartment2 = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                new BigDecimal(2),BigInteger.TWO, true, ApartmentStatus.AVAILABLE);
        Apartment apartment3 = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000003"),
                new BigDecimal(2),BigInteger.valueOf(3L), true, ApartmentStatus.AVAILABLE);

        repository.save(apartment3);
        repository.save(apartment1);

        List<Apartment> actual = (List<Apartment>) repository.findAllSorted(sort);
        List<Apartment> expected = Stream.of(apartment1, apartment2)
                .sorted(sort.getComparator()).toList();
        assertThat(actual).isEqualTo(expected);
    }

}
