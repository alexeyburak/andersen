package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.usecase.CalculateClientStayCurrentPriceUseCase;

import java.util.Map;
import java.util.UUID;

public class ClientStayPriceServlet extends JsonServlet {

    private final CalculateClientStayCurrentPriceUseCase useCase;

    public ClientStayPriceServlet(CalculateClientStayCurrentPriceUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        String id = uri.substring(uri.lastIndexOf("?clientId=") + 1);
        return new Response(useCase.calculatePrice(UUID.fromString(id)));
    }

}
