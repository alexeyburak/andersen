package com.andersenlab.hotel.repository.jdbc;

public class CouldNotExecuteSql extends RuntimeException {
    public CouldNotExecuteSql(String message) {
        super(message);
    }

}
