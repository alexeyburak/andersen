package com.andersenlab.hotel.application.servlet;

import java.util.Map;

public class HelperServlet extends JsonServlet {
    @Override
    Response get(String uri, Map<String, String[]> parameters) {

        return new Response(
                AvailableUri.values()
        );
    }
}
