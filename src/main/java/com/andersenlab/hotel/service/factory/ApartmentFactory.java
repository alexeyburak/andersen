package com.andersenlab.hotel.service.factory;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.repository.ApartmentStore;
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

    public static ApartmentStore.ApartmentEntity createApartment(List<String> args) {
        return args.isEmpty() ? createRandomApartment() : createApartmentFromArgs(args);
    }

    public static ApartmentStore.ApartmentEntity createApartmentFromArgs(List<String> args) {
        validateArgs(args);

        return new ApartmentStore.ApartmentEntity(
                UUID.fromString(args.get(0)), BigDecimal.valueOf(NumberUtils.createDouble(args.get(1))),
                BigInteger.valueOf(NumberUtils.toLong(args.get(2))), BooleanUtils.toBoolean(args.get(3)),
                EnumUtils.getEnum(ApartmentStore.ApartmentStatus.class, args.get(4).toUpperCase())
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
        if (!EnumUtils.isValidEnum(ApartmentStore.ApartmentStatus.class, args.get(4).toUpperCase())) {
            throw new IllegalArgumentException(CustomErrorMessage.ILLEGAL_STATUS.getMessage());
        }
    }

    public static ApartmentStore.ApartmentEntity createRandomApartment() {
        ApartmentStore.ApartmentStatus[] statuses = ApartmentStore.ApartmentStatus.values();

        return new ApartmentStore.ApartmentEntity(
                UUID.randomUUID(),
                BigDecimal.valueOf(rand.nextDouble()),
                BigInteger.valueOf(rand.nextInt(4)),
                rand.nextBoolean(), statuses[rand.nextInt(statuses.length)]
        );
    }
}
