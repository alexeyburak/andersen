package com.andersenlab.hotel.usecase.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ApartmentWithSameIdExists extends RuntimeException {
    public ApartmentWithSameIdExists(String message) {
        log.info(message);
    }
}
