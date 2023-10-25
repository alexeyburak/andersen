package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentReservedException;
import com.andersenlab.hotel.usecase.exception.ClientBannedException;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ClientServiceIntegrationTest {

    private ClientService target;
    private InMemoryClientRepository inMemoryClientRepository;
    private InMemoryApartmentRepository inMemoryApartmentRepository;
    private ApartmentService apartmentService;
    private Client client;
    private ApartmentEntity apartmentEntity;
    private Apartment apartment;

    @BeforeEach
    void setUp() {
        inMemoryClientRepository = new InMemoryClientRepository();
        inMemoryApartmentRepository = new InMemoryApartmentRepository();
        apartmentService = new ApartmentService(inMemoryApartmentRepository);
        target = new ClientService(inMemoryClientRepository, apartmentService);

        apartmentEntity = new ApartmentEntity(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                BigDecimal.ONE, BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        Set<ApartmentEntity> apartments = new HashSet<>(
                Set.of(
                        apartmentEntity,
                        new ApartmentEntity(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                                BigDecimal.ONE, BigInteger.ONE, true, ApartmentStatus.AVAILABLE),
                        new ApartmentEntity(UUID.fromString("00000000-0000-0000-0000-000000000003"),
                                BigDecimal.TWO, BigInteger.ONE, true, ApartmentStatus.AVAILABLE)
                )
        );
        client = new Client(UUID.randomUUID(), "name", ClientStatus.NEW, apartments);
        apartment = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                BigDecimal.ONE, BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
    }

    @Test
    void calculatePrice_ExistingEntity_ShouldReturnDoubleValueOfSumPrice() {
        final UUID id = client.getId();
        final BigDecimal expectedPrice = BigDecimal.valueOf(4);
        inMemoryClientRepository.save(client);

        BigDecimal actual = target.calculatePrice(id);

        assertThat(actual).isEqualTo(expectedPrice);
    }

    @Test
    void calculatePrice_NonExistingEntity_ShouldThrowClientNotFoundException() {
        final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000010");

        assertThatThrownBy(() ->
                target.calculatePrice(id)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void checkIn_NotExistingClient_ShouldThrowClientNotfoundException() {
        final UUID clientId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        final UUID apartmentId = apartment.getId();
        inMemoryApartmentRepository.save(apartment);

        assertThatThrownBy(() ->
                target.checkIn(clientId, apartmentId)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void checkIn_BannedClient_ShouldThrowClientBannedException() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        client.setStatus(ClientStatus.BANNED);
        inMemoryClientRepository.save(client);
        inMemoryApartmentRepository.save(apartment);

        assertThatThrownBy(() ->
                target.checkIn(clientId, apartmentId)
        ).isInstanceOf(ClientBannedException.class);
    }

    @Test
    void checkIn_ReservedApartment_ShouldThrowApartmentReservedException() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        apartment.setStatus(ApartmentStatus.RESERVED);
        inMemoryClientRepository.save(client);
        inMemoryApartmentRepository.save(apartment);

        assertThatThrownBy(() ->
                target.checkIn(clientId, apartmentId)
        ).isInstanceOf(ApartmentReservedException.class);
    }

    @Test
    void checkIn_NotExistingApartment_ShouldThrowApartmentNotfoundException() {
        final UUID clientId = client.getId();
        final UUID apartmentId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        inMemoryClientRepository.save(client);

        assertThatThrownBy(() ->
                target.checkIn(clientId, apartmentId)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void checkIn_ValidEntities_ShouldUpdateUserApartmentSet() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        inMemoryClientRepository.save(client);
        inMemoryApartmentRepository.save(apartment);

        target.checkIn(clientId, apartmentId);

        assertThat(client.getApartments()).hasSize(4);
    }

    @Test
    void checkOut_NotExistingClient_ShouldThrowClientNotfoundException() {
        final UUID clientId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        final UUID apartmentId = apartment.getId();
        inMemoryApartmentRepository.save(apartment);

        assertThatThrownBy(() ->
                target.checkOut(clientId, apartmentId)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void checkOut_NotExistingApartment_ShouldThrowApartmentNotfoundException() {
        final UUID clientId = client.getId();
        final UUID apartmentId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        inMemoryClientRepository.save(client);

        assertThatThrownBy(() ->
                target.checkOut(clientId, apartmentId)
        ).isInstanceOf(ApartmentNotfoundException.class);
    }

    @Test
    void checkOut_ValidEntities_ShouldRemoveApartmentFromClientSetAndUpdateApartmentStatus() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        inMemoryClientRepository.save(client);
        inMemoryApartmentRepository.save(apartment);

        target.checkOut(clientId, apartmentId);

        assertThat(apartment.getStatus()).isEqualTo(ApartmentStatus.AVAILABLE);
        assertThat(client.getApartments()).hasSize(2);
    }

    @Test
    void checkOut_ApartmentDoesNotExistInClientSet_ShouldNotCallApartmentServiceSaveMethod() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        client.getApartments().remove(apartmentEntity);
        inMemoryClientRepository.save(client);
        inMemoryApartmentRepository.save(apartment);
        apartmentService = mock(ApartmentService.class);

        target.checkOut(clientId, apartmentId);

        verify(apartmentService, never()).update(any(Apartment.class));
    }

    @Test
    void getById_ValidClient_ShouldReturnClientFromRepository() {
        final UUID id = client.getId();
        inMemoryClientRepository.save(client);

        ClientEntity actual = target.getById(id);

        assertThat(actual).isNotNull();
    }

    @Test
    void getById_NotExistingClient_ShouldThrowClientNotfoundException() {
        final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000010");

        assertThatThrownBy(() ->
                target.getById(id)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void has_NotExistingClient_ShouldReturnFalse() {
        boolean actual = target.has(UUID.fromString("00000000-0000-0000-0000-000000000010"));

        assertThat(actual).isFalse();
    }

    @Test
    void has_ExistingClient_ShouldReturnTrue() {
        inMemoryClientRepository.save(client);

        boolean actual = target.has(client.getId());

        assertThat(actual).isTrue();
    }

    @Test
    void save_ExistingClient_ShouldThrowClientIsAlreadyExistsException() {
        inMemoryClientRepository.save(client);

        assertThatThrownBy(() ->
                target.save(client)
        ).isInstanceOf(ClientIsAlreadyExistsException.class);
    }

    @Test
    void save_NotExistingClient_ShouldCallRepositorySaveMethod() {
        target.save(client);

        assertThat(inMemoryClientRepository.has(client.getId())).isTrue();
    }

    @Test
    void delete_NotExistingClient_ShouldThrowClientNotfoundException() {
        final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000010");

        assertThatThrownBy(() ->
                target.delete(id)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void delete_ExistingClient_ShouldDeleteClientFromRepository() {
        inMemoryClientRepository.save(client);

        target.delete(client.getId());

        assertThat(inMemoryClientRepository.has(client.getId())).isFalse();
    }

    @Test
    void update_NotExistingClient_ShouldThrowClientNotfoundException() {
        inMemoryClientRepository.delete(client.getId());

        assertThatThrownBy(() ->
                target.update(client)
        ).isInstanceOf(ClientNotfoundException.class);
    }

    @Test
    void update_ExistingClient_ShouldUpdateClientInRepository() {
        inMemoryClientRepository.save(client);
        final Client expected =
                new Client(client.getId(), "name", ClientStatus.NEW, new HashSet<>());

        target.update(expected);

        assertThat(inMemoryClientRepository.has(expected.getId())).isTrue();
    }
}
