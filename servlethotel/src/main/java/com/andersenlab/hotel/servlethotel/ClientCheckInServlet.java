package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

public class ClientCheckInServlet extends JsonServlet {
    private final CheckInClientUseCase checkInClientUseCase;

    ClientCheckInServlet(CheckInClientUseCase checkInClientUseCase) {
        this.checkInClientUseCase = checkInClientUseCase;
    }

    @Override
    Response put(String uri, Map<String, String> body) {
        UUID clientId = UUID.fromString(body.get("clientId"));
        UUID apartmentId = UUID.fromString(body.get("apartmentId"));

        checkInClientUseCase.checkIn(clientId,apartmentId);
        return new Response(HttpServletResponse.SC_OK);
    }
}
