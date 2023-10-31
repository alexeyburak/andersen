package com.andersenlab;

import com.andersenlab.hotel.HotelModule;
import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentEntity;
import com.andersenlab.hotel.service.ContextBuilder;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.andersenlab.hotel.model.ApartmentStatus.RESERVED;

class ApartmentServletIntegrationTest {

    private ApartmentService apartmentService;
    private URI uri;
    private UUID id;
    private ObjectMapper objectMapper;
    private String path;
    private ServletStarter servletStarter;
    private HotelModule context;

    @BeforeEach
    void setUp() throws URISyntaxException {
        path = "test-db.json";
        context = new ContextBuilder().initFile(path)
                .initServices()
                .initCheckInCheckOut(true)
                .build();

        apartmentService = (ApartmentService) context.apartmentService();
        uri = new URI("http://localhost:8080/apartments");
        id = UUID.randomUUID();
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
    void getApartmentByNonExistingId_shouldRespondBadRequest() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request;
        try {

            request = HttpRequest.newBuilder()
                    .uri(new URI(uri + "/" + id))
                    .GET()
                    .build();

        } catch (URISyntaxException e) {
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isIn(400);

    }

    @Test
    @Tag("GET")
    void getApartmentById_shouldRespondStatusOk() {
        apartmentService.save(new Apartment(id));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri + "/" + id))
                    .GET()
                    .build();

        } catch (URISyntaxException e) {
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);

    }


    @Test
    @Tag("GET")
    void getApartmentWithNonExistingId_shouldRespondEmptyApartment() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri + "/" + id))
                    .GET()
                    .build();

        } catch (URISyntaxException e) {
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(response.body()).isEqualTo("");
    }

    @Test
    @Tag("GET")
    void getListOfApartments_shouldReturnStatusCode200() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("DELETE")
    void deleteApartmentWithExistingId_shouldReturnStatusCode200() {
        apartmentService.save(new Apartment(id));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\"" + id + "\"}"))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    @Tag("DELETE")
    void deleteApartmentNonWithExistingId_shouldReturnBadRequest() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\"" + id + "\"}"))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Request was failed " + e);
        }


        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }


    @Test
    @Tag("POST")
    void addApartment_shouldAddApartmentAndReturnStatusCodeOk() {
        String jsonBody;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Request was failed. Try to also check object mapper " + e);
        }


        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed");
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("POST")
    void addExistingApartment_shouldReturnBadRequest() {
        apartmentService.save(new Apartment(id));
        String jsonBody;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Request was failed. Try to also check object mapper " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @Tag("adjust-apartment-price")
    void adjustApartmentPrice_shouldReturnStatusOk() {
        apartmentService.save(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        BigDecimal newPrice = new BigDecimal(245);
        String jsonBody;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            jsonBody = objectMapper.writeValueAsString(new Apartment(id, newPrice, BigInteger.valueOf(4), true, RESERVED));
            request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/apartments/adjust"))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Request was failed. Try to also check object mapper " + e);
        }


        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        ApartmentEntity apartment = apartmentService.getById(id);

        Assertions.assertThat(apartment.price()).isEqualTo(newPrice);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("adjust-apartment-price")
    void notAdjustApartmentPriceIfIdNotExist_shouldReturnBadRequest() {
        String jsonBody;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;

        try {
            jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(678), BigInteger.valueOf(4), true, RESERVED));
            request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/apartments/adjust"))
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

        } catch (IOException | URISyntaxException e){
            throw new RuntimeException("Request was failed " + e);
        }

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Response was failed " + e);
        }

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }
}