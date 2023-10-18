package com.andersenlab.hotel.service.factory;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ApartmentFactory {

    private static final int VALID_ARGUMENTS_SIZE = 5;
    private static final Random rand = new Random();

    private ApartmentFactory() {}

    public static Apartment createApartment(List<String> args) {
        return args.isEmpty() ? createRandomApartment() : createApartmentFromArgs(args);
    }

    public static Apartment createApartmentFromArgs(List<String> args) {
        validateArgs(args);

        return new Apartment(
                UUID.fromString(args.get(0)), BigDecimal.valueOf(NumberUtils.createDouble(args.get(1))),
                BigInteger.valueOf(NumberUtils.toLong(args.get(2))), BooleanUtils.toBoolean(args.get(3)),
                EnumUtils.getEnum(ApartmentStatus.class, args.get(4).toUpperCase())
        );
    }

    private static void validateArgs(List<String> args) {
        if (args.size() != VALID_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_NUMBER_OF_ARGUMENTS.getMessage());
        }
        if (!NumberUtils.isParsable(args.get(1))) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_PRICE.getMessage());
        }
        if (!NumberUtils.isParsable(args.get(2))) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_CAPACITY.getMessage());
        }
        if ((!args.get(3).equals("true")) && (!args.get(3).equals("false"))) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_AVAILABILITY.getMessage());
        }
        if (!EnumUtils.isValidEnum(ApartmentStatus.class, args.get(4).toUpperCase())) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_STATUS.getMessage());
        }
    }

    public static Apartment createRandomApartment() {
        ApartmentStatus[] statuses = ApartmentStatus.values();
        ApartmentStatus status = statuses[rand.nextInt(statuses.length)];
        final double decimalPlace = 10;
        return new Apartment(
                UUID.randomUUID(),
                BigDecimal.valueOf(Math.round(rand.nextFloat() * 100 * decimalPlace)/decimalPlace),
                BigInteger.valueOf(rand.nextInt(4)),
                status.equals(ApartmentStatus.AVAILABLE), status
        );
    }
}
