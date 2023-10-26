package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentSort;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.service.CrudService;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.EnumUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

public class ApartmentsServlet extends JsonServlet {

    private final CrudService<Apartment, ApartmentEntity> apartmentService;
    private final ListApartmentsUseCase useCase;

    public ApartmentsServlet(CrudService<Apartment, ApartmentEntity> apartmentService, ListApartmentsUseCase useCase) {
        this.apartmentService = apartmentService;
        this.useCase = useCase;
    }

    @Override
    Response post(String uri, Map<String, String> body) {
        Apartment apartment = new Apartment(
                UUID.fromString(body.get("id")), new BigDecimal(body.get("price")),
                new BigInteger(body.get("capacity")), Boolean.parseBoolean(body.get("availability")),
                EnumUtils.getEnum(ApartmentStatus.class, body.get("status"))
        );
        apartmentService.save(apartment);
        return new Response(apartment);
    }

    @Override
    Response delete(String uri, Map<String, String> body) {
        apartmentService.delete(UUID.fromString(body.get("id")));
        return new Response(HttpServletResponse.SC_OK);
    }

    @Override
    Response get(String uri, Map<String, String[]> parameters) {
        ApartmentSort sort = EnumUtils.getEnum(ApartmentSort.class, parameters.get("sort")[0]);
        return new Response(useCase.list(sort));
    }
}
