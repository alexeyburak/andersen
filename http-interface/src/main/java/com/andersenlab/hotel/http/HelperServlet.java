package com.andersenlab.hotel.http;

import java.util.Arrays;
import java.util.Map;

public class HelperServlet extends JsonServlet {

    private static final String COMMANDS_FORMAT = "To action %s, go to: %s - %s";

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        return new Response(
                Arrays.stream(AvailableUri.values())
                        .map(value ->
                                String.format(COMMANDS_FORMAT, value.name().toLowerCase(), value.getUrl(), value.getMethod())
                        )
                        .toList());
    }
}
