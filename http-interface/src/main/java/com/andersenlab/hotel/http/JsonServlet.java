package com.andersenlab.hotel.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public abstract class JsonServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    Response get(String uri, Map<String, String[]> parameters) {
        return new Response(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    Response post(String uri, Map<String, String> body) {
        return new Response(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    Response put(String uri, Map<String, String> body) {
        return new Response(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    Response delete(String uri, Map<String, String> body) {
        return new Response(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void respond(HttpServletResponse resp, Response response) throws IOException {
        resp.setStatus(response.status());
        resp.addHeader("Content-Type", "application/json");
        var body = response.body();
        if (body.isEmpty()) {
            return;
        }
        objectMapper.writeValue(resp.getWriter(), body.get());
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        respond(resp, get(req.getRequestURI(), req.getParameterMap()));
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        respond(
                resp,
                post(
                        req.getRequestURI(),
                        objectMapper.readValue(req.getReader(), new TypeReference<>() {})
                )
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        respond(
                resp,
                put(
                        req.getRequestURI(),
                        objectMapper.readValue(req.getReader(), new TypeReference<>() {})
                )
        );
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        respond(
                resp,
                delete(
                        req.getRequestURI(),
                        objectMapper.readValue(req.getReader(), new TypeReference<>() {})
                )
        );
    }

    record Response(int status, Optional<Object> body) {
        Response(int status) {
            this(status, Optional.empty());
        }

        Response(Object body) {
            this(HttpServletResponse.SC_OK, Optional.ofNullable(body));
        }
    }
}
