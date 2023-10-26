package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientEntity;
import com.andersenlab.hotel.model.ClientSort;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.service.CrudService;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.EnumUtils;

import java.util.Map;
import java.util.UUID;

public class ClientsServlet extends JsonServlet {
    private final CrudService<Client, ClientEntity> clientService;
    private final ListClientsUseCase useCase;

    public ClientsServlet(CrudService<Client, ClientEntity> clientService, ListClientsUseCase useCase) {
        this.clientService = clientService;
        this.useCase = useCase;
    }

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        ClientSort sort = EnumUtils.getEnum(ClientSort.class ,parameters.get("sort")[0]);
        return new Response(useCase.list(sort));
    }

    @Override
    Response post(String uri, Map<String, String> body) {
        Client client = new Client(
                UUID.fromString(body.get("id")),
                body.get("name"),
                ClientStatus.NEW
        );
        clientService.save(client);
        return new Response(client);
    }

    @Override
    Response delete(String uri, Map<String, String> body) {
        UUID id = UUID.fromString(body.get("id"));
        clientService.delete(id);
        return new Response(HttpServletResponse.SC_NO_CONTENT);
    }
}
