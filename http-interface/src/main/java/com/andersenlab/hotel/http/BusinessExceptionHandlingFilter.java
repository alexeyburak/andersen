package com.andersenlab.hotel.http;

import com.andersenlab.hotel.usecase.exception.ApartmentChangeStatusException;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentReservedException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import com.andersenlab.hotel.usecase.exception.ClientBannedException;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BusinessExceptionHandlingFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (ApartmentWithSameIdExists | ApartmentChangeStatusException | ClientIsAlreadyExistsException |
                 ClientNotfoundException | ApartmentNotfoundException | ClientBannedException |
                 ApartmentReservedException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
