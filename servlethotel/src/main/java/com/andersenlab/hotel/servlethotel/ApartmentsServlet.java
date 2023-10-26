package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.service.CrudService;
import java.util.Map;
import java.util.UUID;

public class ApartmentsServlet extends JsonServlet {
    private CrudService<Apartment, ApartmentEntity> apartmentService;

    public ApartmentsServlet(CrudService<Apartment, ApartmentEntity> apartmentService) {
        this.apartmentService = apartmentService;
    }
    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        int lastSlash = uri.lastIndexOf("/");
        String id = uri.substring(lastSlash + 1);
        System.out.println(id);
        ApartmentEntity entity = apartmentService.getById(UUID.fromString(id));
        System.out.println(entity);
        return new Response(entity);
    }
}
