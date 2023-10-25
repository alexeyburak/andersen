package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.service.CrudService;

import java.util.Map;
import java.util.UUID;

public class ClientsByIdServlet extends JsonServlet {
    private final CrudService<Client, ClientEntity> crudService;

    public ClientsByIdServlet(CrudService<Client, ClientEntity> crudService) {
        this.crudService = crudService;
    }

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        String id = uri.substring(uri.lastIndexOf("/") + 1);
        return new Response(crudService.getById(UUID.fromString(id)));
    }
}
