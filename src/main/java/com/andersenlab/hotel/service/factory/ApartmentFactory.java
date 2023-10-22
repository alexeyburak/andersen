package com.andersenlab.hotel.service.factory;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class ApartmentFactory {

    private static final int VALID_ARGUMENTS_SIZE = 5;

    private ApartmentFactory() {}

    public static Apartment createApartment(List<String> args) {
        validateArgs(args);

        return new Apartment(
                UUID.fromString(args.get(0)), BigDecimal.valueOf(NumberUtils.createDouble(args.get(1))),
                BigInteger.valueOf(NumberUtils.toLong(args.get(2))), BooleanUtils.toBoolean(args.get(3)),
                EnumUtils.getEnum(ApartmentStatus.class, args.get(4).toUpperCase())
        );
    }

    private static void validateArgs(List<String> args) {
        if (args.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }
        if (!NumberUtils.isParsable(args.get(1))) {
            throw new IllegalArgumentException("Illegal price");
        }
        if (!NumberUtils.isParsable(args.get(2))) {
            throw new IllegalArgumentException("Illegal capacity");
        }
        if ((!args.get(3).equals("true")) && (!args.get(3).equals("false"))) {
            throw new IllegalArgumentException("Illegal availability");
        }
        if (!EnumUtils.isValidEnum(ApartmentStatus.class, args.get(4).toUpperCase())) {
            throw new IllegalArgumentException("Illegal status");
        }
    }
}
