package com.andersenlab;

import com.andersenlab.hotel.HotelModule;
import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.model.Client;
import com.andersenlab.hotel.model.ClientStatus;
import com.andersenlab.hotel.service.ContextBuilder;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.andersenlab.hotel.service.impl.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;

class CheckOutServletIntegrationTest {
    private ClientService clientService;
    private ApartmentService apartmentService;

    private Client client;
    private ApartmentEntity apartmentEntity;
    private Apartment apartment;

    private URI uri;
    private ObjectMapper objectMapper;

    ServletStarter starter;

    AtomicInteger integer = new AtomicInteger(0);

    String path;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        path = String.format("test-cio%d.json", integer.incrementAndGet());

        HotelModule context = new ContextBuilder().initFile(path)
                .initServices()
                .initCheckInCheckOut(true)
                .build();
        starter = ServletStarter.forModule(context);
        starter.run();

        apartmentService = (ApartmentService) context.apartmentService();
        clientService = (ClientService) context.clientService();

        apartmentEntity = new ApartmentEntity(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                new BigDecimal(1), BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        apartment = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                new BigDecimal(1), BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
        client = new Client(UUID.fromString("00000000-0000-0000-0000-000000000001"), "John", ClientStatus.NEW, new HashSet<>(Set.of(apartmentEntity)));

        apartmentService.save(apartment);
        clientService.save(client);

        uri = new URI("http://localhost:8080/clients/check-out");
        objectMapper = new ObjectMapper();
//        databaseFile = new File("db.json");
//
//        apartmentService = new ApartmentService(new InFileApartmentRepository(databaseFile));
//        clientService = new ClientService(new InFileClientRepository(databaseFile), apartmentService);
//
//        apartmentEntity = new ApartmentEntity(UUID.fromString("00000000-0000-0000-0000-000000000001"),
//                new BigDecimal(1), BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
//        apartment = new Apartment(UUID.fromString("00000000-0000-0000-0000-000000000001"),
//                new BigDecimal(1), BigInteger.ONE, true, ApartmentStatus.AVAILABLE);
//        client = new Client(UUID.fromString("00000000-0000-0000-0000-000000000001"), "John", ClientStatus.NEW, new HashSet<>(Set.of(apartmentEntity)));
//
//        uri = new URI("http://localhost:8080/clients/check-out");
//        objectMapper = new ObjectMapper();
//
//        path = String.format("test-cio%d.json", integer.incrementAndGet());
//
//        var context = new ContextBuilder().initFile(path)
//                .initServices()
//                .initCheckInCheckOut(true)
//                .build();
//        starter = ServletStarter.forModule(context);
//        starter.run();
    }

    @AfterEach
    void tearDown() {
        apartmentService.delete(apartment.getId());
        clientService.delete(client.getId());

        new File(path).delete();
        starter.stop();
    }

    @SneakyThrows
    @Test
    void checkOut_NotExistingClient_ShouldReturnSC_BAD_REQUEST() {
        final UUID newClientId = new Client(UUID.randomUUID(), "John", ClientStatus.NEW, new HashSet<>(Set.of(apartmentEntity))).getId();
        final UUID apartmentId = apartment.getId();
        String jsonBody = objectMapper.writeValueAsString(new CheckOutServletIntegrationTest.ClientAppartmentIds(newClientId, apartmentId));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }
    @SneakyThrows
    @Test
    void checkOut_NotExistingApartments_ShouldReturnSC_BAD_REQUEST() {
        final UUID clientId = client.getId();
        final UUID newApartmentId = new Apartment(UUID.randomUUID(), new BigDecimal(1), BigInteger.ONE, true, ApartmentStatus.AVAILABLE).getId();

        String jsonBody = objectMapper.writeValueAsString(new CheckOutServletIntegrationTest.ClientAppartmentIds(clientId, newApartmentId));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SneakyThrows
    @Test
    void checkOut_ValidClient_ShouldReturnStatusOK() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        String jsonBody = objectMapper.writeValueAsString(new CheckOutServletIntegrationTest.ClientAppartmentIds(clientId, apartmentId));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
    }

    @SneakyThrows
    @Test
    void checkOut_ValidApartments_ShouldReturnStatusOK() {
        final UUID clientId = client.getId();
        final UUID apartmentId = apartment.getId();
        String jsonBody = objectMapper.writeValueAsString(new CheckOutServletIntegrationTest.ClientAppartmentIds(clientId, apartmentId));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpServletResponse.SC_OK);
    }

    @Data
    class ClientAppartmentIds {
        private final UUID clientId;
        private final UUID apartmentId;
    }
}
