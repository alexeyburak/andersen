package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.application.CustomErrorMessage;
import com.andersenlab.hotel.usecase.exception.ApartmentChangeStatusException;
import com.andersenlab.hotel.usecase.exception.ApartmentNotfoundException;
import com.andersenlab.hotel.usecase.exception.ApartmentReservedException;
import com.andersenlab.hotel.usecase.exception.ApartmentWithSameIdExists;
import com.andersenlab.hotel.usecase.exception.ClientBannedException;
import com.andersenlab.hotel.usecase.exception.ClientIsAlreadyExistsException;
import com.andersenlab.hotel.usecase.exception.ClientNotfoundException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.Source;

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
