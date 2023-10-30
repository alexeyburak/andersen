package com.andersenlab.hotel.servlets;

import com.andersenlab.hotel.model.Apartment;
import com.andersenlab.hotel.model.ApartmentStatus;
import com.andersenlab.hotel.repository.infile.InFileApartmentRepository;
import com.andersenlab.hotel.service.impl.ApartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.andersenlab.hotel.model.ApartmentStatus.RESERVED;

public class ApartmentServletIntegrationTest {

    private ApartmentService apartmentService;
    private URI uri;
    private UUID id;
    private ObjectMapper objectMapper;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        File databaseFile = new File("C:\\Users\\Aibar\\Desktop\\andersenlab-hotel\\db.json");
        apartmentService = new ApartmentService(new InFileApartmentRepository(databaseFile));
        uri = new URI("http://localhost:8080/apartments");
        id = UUID.randomUUID();
        objectMapper = new ObjectMapper();
    }


    @Test
    @Tag("GET")
    @SneakyThrows
    void getApartmentByNonExistingId_shouldRespondBadRequest() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(apartmentService.has(id)).isFalse();
        Assertions.assertThat(response.statusCode()).isIn(400);

    }
    @Test
    @Tag("GET")
    @SneakyThrows
    void getApartmentById_shouldRespondStatusOk() {
        apartmentService.save(new Apartment(id));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(apartmentService.has(id)).isTrue();
        Assertions.assertThat(response.statusCode()).isIn(200);

    }


    @Test
    @Tag("GET")
    @SneakyThrows
    void getApartmentWithNonExistingId_shouldRespondEmptyApartment() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.body()).isEqualTo("");
    }

    @Test
    @Tag("GET")
    @SneakyThrows
    void getListOfApartments_shouldReturnStatusCode200() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("DELETE")
    @SneakyThrows
    void deleteApartmentWithExistingId_shouldReturnStatusCode200() {
        apartmentService.save(new Apartment(id));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\"" + id + "\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }


    @Test
    @Tag("DELETE")
    @SneakyThrows
    void deleteApartmentNonWithExistingId_shouldReturnBadRequest() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("DELETE", HttpRequest.BodyPublishers.ofString("{\"id\":\""+ id +"\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(apartmentService.has(id)).isFalse();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }


    @Test
    @Tag("POST")
    @SneakyThrows
    void addApartment_shouldAddApartmentAndReturnStatusCodeOk() {
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Tag("POST")
    @SneakyThrows
    void addExistingApartment_shouldReturnBadRequest() {
        apartmentService.save(new Apartment(id));
        String jsonBody = objectMapper.writeValueAsString(new Apartment(id, new BigDecimal(34), BigInteger.valueOf(4), true, RESERVED));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(apartmentService.has(id)).isTrue();

        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }
}

