//package com.andersenlab.hotel.usecase;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.List;
//import java.util.UUID;
//
//public interface ListApartmentsUseCase {
//
//    List<ApartmentView> list(Sort sort);
//
//    enum Sort {
//        ID, PRICE, CAPACITY, AVAILABILITY
//    }
//
//    enum ApartmentStatus {
//        AVAILABLE, CLOSED
//    }
//
//    record ApartmentView(UUID id, BigDecimal price, BigInteger capacity, boolean availability,
//                           ApartmentStatus status) {
//    }
//}
