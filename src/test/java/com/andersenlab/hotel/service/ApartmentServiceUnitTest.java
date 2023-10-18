package com.andersenlab.hotel.service;


import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApartmentServiceUnitTest {
    ApartmentService target;
    InMemoryApartmentRepository repo;

    Apartment apartment;
    @BeforeEach
    void setUp() {
        repo = mock(InMemoryApartmentRepository.class);
        target = new ApartmentService(repo);
    }

    @Test
    void delete_NotExistingId_ShouldThrowsApartmentNotFoundException() {
        final UUID id = UUID.randomUUID();

        assertThrows(ApartmentNotfoundException.class, () -> target.delete(id));
    }

    @Test
    void delete_ExistingId_ShouldCallRepositoryMethod(){
        final UUID id = UUID.randomUUID();
        when(repo.has(any(UUID.class))).thenReturn(true);

        repo.delete(id);

        verify(repo).delete(any());
    }
}
