package com.andersenlab.hotel.http;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.service.CrudService;

import java.util.Map;
import java.util.UUID;

public class ApartmentServlet extends JsonServlet {

    private CrudService<Apartment, ApartmentEntity> apartmentService;

    public ApartmentServlet(CrudService<Apartment, ApartmentEntity> apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        int lastSlash = uri.lastIndexOf("/");
        String id = uri.substring(lastSlash + 1);
        ApartmentEntity entity = apartmentService.getById(UUID.fromString(id));
        return new Response(entity);
    }
}
