package com.andersenlab.hotel.application;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.service.CrudService;
import com.andersenlab.hotel.usecase.*;

public record HotelModule(CrudService<Client, ClientEntity> clientService,
                          CrudService<Apartment, ApartmentEntity> apartmentService,
                          AdjustApartmentPriceUseCase adjustApartmentPriceUseCase,
                          CalculateClientStayCurrentPriceUseCase calculateClientStayCurrentPriceUseCase,
                          CheckOutClientUseCase checkOutClientUseCase, CheckInClientUseCase checkInClientUseCase,
                          ListApartmentsUseCase listApartmentsUseCase, ListClientsUseCase listClientsUseCase) {
}
