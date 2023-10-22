package com.andersenlab.hotel.service;

import com.andersenlab.hotel.model.*;
import com.andersenlab.hotel.repository.ClientSort;
import com.andersenlab.hotel.repository.SortableCrudRepository;
import com.andersenlab.hotel.service.impl.*;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private SortableCrudRepository<Client, ClientSort> clientRepository;

    @Mock
    private ApartmentService apartmentService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCheckOut() {
        // Create test data
        UUID clientId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();

        Client client = new Client(clientId, "John Doe", ClientStatus.NEW, new HashSet<>());
        ApartmentEntity apartment = new ApartmentEntity(apartmentId, BigDecimal.valueOf(100.0), new BigInteger("2"), true, ApartmentStatus.AVAILABLE);

        // Mock the behavior of dependencies
        when(clientRepository.getById(clientId)).thenReturn(Optional.of(client));
        when(apartmentService.getById(apartmentId)).thenReturn(apartment);

        // Perform the checkOut operation
        clientService.checkOut(clientId, apartmentId);

        // Assert that the client's apartment list is empty
        assertEquals(0, client.getApartments().size());
    }

    @Test
    void testCheckOutApartmentNotAvailable() {
        UUID clientId = UUID.randomUUID();
        UUID apartmentId = UUID.randomUUID();

        Client client = new Client(clientId, "John Doe", ClientStatus.NEW, new HashSet<>());
        ApartmentEntity apartment = new ApartmentEntity(apartmentId, BigDecimal.valueOf(100.0), new BigInteger("2"), false, ApartmentStatus.RESERVED);

        when(clientRepository.getById(clientId)).thenReturn(Optional.of(client));
        when(apartmentService.getById(apartmentId)).thenReturn(apartment);

        // Attempt to check out when the apartment is not available, and it should throw an exception
        assertThrows(ClientNotfoundException.class, () -> clientService.checkOut(clientId, apartmentId));
    }
}
