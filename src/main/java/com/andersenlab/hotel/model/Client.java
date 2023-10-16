package com.andersenlab.hotel.model;

import com.andersenlab.hotel.usecase.ListClientsUseCase;
import lombok.Data;

import java.util.UUID;
@Data
public class Client {
    private UUID id;
    private String name;
    private ListClientsUseCase.ClientStatus status;
}
