package com.andersenlab.hotel.application.command;

import com.andersenlab.hotel.repository.ApartmentStore;
import com.andersenlab.hotel.repository.ClientStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryApartmentStore;
import com.andersenlab.hotel.repository.inmemory.InMemoryClientStore;
import com.andersenlab.hotel.service.ApartmentService;
import com.andersenlab.hotel.service.ClientService;
import com.andersenlab.hotel.usecase.ListApartmentsUseCase;
import com.andersenlab.hotel.usecase.ListClientsUseCase;
import org.apache.commons.lang3.EnumUtils;

import java.io.PrintStream;
import java.util.List;

public class GetAll implements Command {
    @Override
    public void execute(PrintStream output, List<String> arguments) {
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("Invalid arguments quantity");
        }
        switch (arguments.get(1)) {
            case "apartment" -> {
                ListApartmentsUseCase.Sort sort = EnumUtils.getEnum(ListApartmentsUseCase.Sort.class, arguments.get(2));
                ApartmentService.getInstance().list(sort).forEach(System.out::println);
            }
            case "client" -> {
                ListClientsUseCase.Sort sort = EnumUtils.getEnum(ListClientsUseCase.Sort.class, arguments.get(2));
                ClientService.getInstance().list(sort).forEach(System.out::println);
            }
            default -> throw new IllegalArgumentException("Unknown argument");
        }
    }
}
