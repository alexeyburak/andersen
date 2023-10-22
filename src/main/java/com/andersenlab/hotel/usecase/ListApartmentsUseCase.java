package com.andersenlab.hotel.usecase;

import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.repository.ApartmentSort;

import java.util.List;

public interface ListApartmentsUseCase {

    List<ApartmentEntity> list(ApartmentSort sort);
}
