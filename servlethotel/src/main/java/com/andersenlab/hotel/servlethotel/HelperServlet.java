package com.andersenlab.hotel.servlethotel;

import java.util.Arrays;
import java.util.Map;

public class HelperServlet extends JsonServlet {
    @Override
    Response get(String uri, Map<String, String[]> parameters) {

        return new Response(
                Arrays.stream(AvailableUri.values()).map(value -> "To action " + value.name().toLowerCase() +
                        ", go to: " + value.getUrl() + " - " + value.getMethod()).toList());
    }
}
