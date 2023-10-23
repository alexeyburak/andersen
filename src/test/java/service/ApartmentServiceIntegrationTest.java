package service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.repository.ApartmentSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ApartmentServiceIntegrationTest {

    private ApartmentService target;
    private SortableCrudRepository<Apartment, ApartmentSort> repo;
    private Apartment apartmentAvailable;
    private Apartment apartmentUnAvailable;


    @BeforeEach
    void setUp() {
        repo = new InMemoryApartmentRepository();
        target = new ApartmentService(repo);
        apartmentAvailable = new Apartment(UUID.randomUUID(), BigDecimal.ONE, BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        apartmentUnAvailable = new Apartment(UUID.randomUUID(), BigDecimal.TWO, BigInteger.TWO, false, ApartmentStatus.RESERVED);

    }

    @Test
    void deleteNoExistingId_ShouldThrowApartmentNotFoundException() {
        final UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> target.delete(id)).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void deleteExistingId_ShouldDelete() {
        target.save(apartmentAvailable);

        target.delete(apartmentAvailable.getId());

        assertThat(target.has(apartmentAvailable.getId())).isFalse();
    }

    @Test
    void hasNotExistingId_shouldReturnFalse() {
        assertThat(target.has(apartmentAvailable.getId())).isFalse();
    }

    @Test
    void hasExistingId_shouldReturnTrue() {
        target.save(apartmentAvailable);

        assertThat(target.has(apartmentAvailable.getId())).isTrue();
    }

    @Test
    void getApartmentById_returnApartmentEntity() {
        target.save(apartmentAvailable);

        assertThatCode(() -> target.getById(apartmentAvailable.getId())).doesNotThrowAnyException();
    }

    @Test
    void NotExistApartmentById_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() -> target.getById(apartmentUnAvailable.getId())).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void saveExistingApartment_shouldThrowApartmentWithSameIdExistException() {
        target.save(apartmentAvailable);

        assertThatThrownBy(() -> target.save(apartmentAvailable)).isInstanceOf(ApartmentWithSameIdExists.class);
    }

    @Test
    void saveApartment_shouldSaveApartment() {
        assertThatCode(() -> target.save(apartmentAvailable)).doesNotThrowAnyException();
    }

    @Test
    void updateExistingApartment_shouldNotThrowException() {
        target.save(apartmentAvailable);
        assertThatCode(() -> target.update(apartmentAvailable)).doesNotThrowAnyException();
    }

    @Test
    void updateNotExistingApartment_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() -> target.update(apartmentUnAvailable)).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void updateApartmentWithNullId_shouldThrowException() {
        apartmentAvailable.setId(null);
        assertThatThrownBy(() -> target.update(apartmentUnAvailable))
                .isInstanceOf(ApartmentNotfoundException.class);
    }


    @Test
    void mapToEntity_shouldNotThrowAnyException() {
        assertThatCode(() -> target.list(ApartmentSort.ID)).doesNotThrowAnyException();
    }

    @Test
    void testListApartment() {
        List<ApartmentEntity> list = target.list(ApartmentSort.ID);
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.isEmpty());
    }


    @Test
    void shouldAdjustApartmentPrice() {
        BigDecimal newPrice = new BigDecimal(234);
        target.save(apartmentAvailable);
        target.adjust(apartmentAvailable.getId(),newPrice);
        ApartmentEntity apartment = target.getById(apartmentAvailable.getId());
        assertThat(apartment.price()).isEqualTo(newPrice);
    }

    @Test
    void adjustPrice_shouldThrowApartmentNotFoundException() {
        assertThatThrownBy(() -> target.adjust(UUID.randomUUID(), new BigDecimal(23)))
                .isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void adjustPriceNotThrowAnyException() {
        target.save(apartmentAvailable);
        assertThatCode(() -> target.adjust(apartmentAvailable.getId(), new BigDecimal(23))).doesNotThrowAnyException();
    }


    @Test
    void notAdjustApartmentPrice_ifApartmentNotAvailable() {
        BigDecimal newPrice = new BigDecimal(44);
        target.save(apartmentUnAvailable);
        assertThatThrownBy(() -> target.adjust(apartmentUnAvailable.getId(), newPrice)).isInstanceOf(ApartmentNotfoundException.class);
        ApartmentEntity apartment = target.getById(apartmentUnAvailable.getId());
        assertThat(apartment.price()).isNotEqualByComparingTo(newPrice);

    }
}