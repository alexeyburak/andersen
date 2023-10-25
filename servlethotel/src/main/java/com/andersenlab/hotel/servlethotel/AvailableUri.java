package com.andersenlab.hotel.servlethotel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AvailableUri {
    GET_ALL("/entities?sort={sort}", "GET"),
    CREATE("/entities", "POST"),
    GET("/entities/{id}", "GET"),
    DELETE("/entities/{id}", "DELETE"),
    ADJUST_APARTMENT_PRICE("/apartments/adjust", "PUT"),
    CHECK_IN("/clients/check-in", "PUT"),
    CHECK_OUT("/clients/check-out", "PUT"),
    CLIENT_PRICE_PER_STAY("/clients/stay?clientId={id}", "GET");

    private final String url;
    private final String method;
}
