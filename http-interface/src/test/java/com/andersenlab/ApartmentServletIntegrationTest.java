package com.andersenlab;

import com.andersenlab.hotel.HotelModule;
import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.service.ContextBuilder;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import static com.andersenlab.hotel.model.ApartmentStatus.RESERVED;

class ApartmentServletIntegrationTest {
    private ApartmentService apartmentService;
    private final String uri = "http://localhost:8080/apartments";
    private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private ObjectMapper objectMapper;
    private String path;
    private ServletStarter servletStarter;
    private HotelModule context;

    @BeforeEach
    void setUp() {
        path = "test-db.json";
        context = new ContextBuilder().initFile(path)
                .initServices()
                .initCheckInCheckOut(true)
                .build();
        apartmentService = (ApartmentService) context.apartmentService();
        objectMapper = new ObjectMapper();
        servletStarter = ServletStarter.forModule(context);
        servletStarter.run();
    }

    @AfterEach
    void teardown() {
        new File(path).delete();
        servletStarter.stop();
    }

    @Test
    @Tag("GET")
    void getApartmentByNonExistingId_shouldRespondBadRequest(){
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/" + id))
                .GET()
                .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isIn(400);

    }

    @Test
    @Tag("GET")
    void getApartmentById_shouldRespondStatusOk() {
        apartmentService.save(new Apartment(id));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/" + id))
                    .GET()
                    .build();
        try {
            response =  HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("GET")
    void getApartmentWithNonExistingId_shouldRespondEmptyApartment() {
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/" + id))
                    .GET()
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(response.body()).isEqualTo("");
    }

    @Test
    @Tag("GET")
    void getListOfApartments_shouldReturnStatusCode200() {
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("DELETE")
    void deleteApartmentWithExistingId_shouldReturnStatusCode200() {
        apartmentService.save(new Apartment(id));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\"" + id + "\"}"))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("DELETE")
    void deleteApartmentNonWithExistingId_shouldReturnBadRequest() {
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\"" + id + "\"}"))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @Tag("POST")
    void addApartment_shouldAddApartmentAndReturnStatusCodeOk() throws JsonProcessingException {
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("POST")
    void addExistingApartment_shouldReturnBadRequest() throws JsonProcessingException {
        apartmentService.save(new Apartment(id));
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @Tag("adjust-apartment-price")
    void adjustApartmentPrice_shouldReturnStatusOk() throws JsonProcessingException {
        apartmentService.save(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        BigDecimal newPrice = new BigDecimal(245);
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, newPrice, BigInteger.valueOf(4), true, RESERVED));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/adjust"))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        ApartmentEntity apartment = apartmentService.getById(id);

        Assertions.assertThat(apartment.price()).isEqualTo(newPrice);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("adjust-apartment-price")
    void notAdjustApartmentPriceIfIdNotExist_shouldReturnBadRequest() throws JsonProcessingException {
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(678), BigInteger.valueOf(4), true, RESERVED));
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri + "/adjust"))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }
}