package com.andersenlab.hotel.application.command;

import java.util.List;

@FunctionalInterface
public interface ArgumentsValidator<T> {
    void validateArguments(List<T> arguments) throws IllegalArgumentException;
}
