package com.andersenlab.hotel.http;

import com.andersenlab.hotel.usecase.CheckOutClientUseCase;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.UUID;

public class CheckOutClientServlet extends JsonServlet {

    private final CheckOutClientUseCase useCase;

    public CheckOutClientServlet(CheckOutClientUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    Response put(String uri, Map<String, String> body) {
        UUID clientId = UUID.fromString(body.get("clientId"));
        UUID apartmentId = UUID.fromString(body.get("apartmentId"));
        useCase.checkOut(clientId, apartmentId);
        return new Response(HttpServletResponse.SC_OK);
    }
}
