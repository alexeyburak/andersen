package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.usecase.CheckInClientUseCase;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

public class ClientCheckInServlet extends JsonServlet{
    private final CheckInClientUseCase checkInClientUseCase;

    ClientCheckInServlet(CheckInClientUseCase checkInClientUseCase) {
        this.checkInClientUseCase = checkInClientUseCase;
    }

    @Override
    Response put(String uri, Map<String, String> body) {
        UUID clientID = UUID.fromString(body.get("clientID"));
        UUID apartmentID = UUID.fromString(body.get("apartmentID"));

        checkInClientUseCase.checkIn(clientID,apartmentID);
        return new Response(HttpServletResponse.SC_OK);
    }
}
