package com.andersenlab.hotel.http;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.service.CrudService;
import org.apache.commons.lang3.EnumUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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