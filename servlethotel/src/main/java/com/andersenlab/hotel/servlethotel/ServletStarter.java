package com.andersenlab.hotel.servlethotel;

import com.andersenlab.hotel.HotelModule;
import jakarta.servlet.DispatcherType;
import lombok.SneakyThrows;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;

public class ServletStarter {
    private final Server server;

    public ServletStarter(Server server) {
        this.server = server;
    }

    public static ServletStarter forModule(HotelModule module) {
        var server = new Server();

        var connector = new ServerConnector(server);
        connector.setPort(8080);

        var servletHandler = new ServletHandler();
        addServlets(module, servletHandler);
        addFilters(servletHandler);

        server.setConnectors(new Connector[]{connector});
        server.setHandler(servletHandler);
        return new ServletStarter(server);
    }

    private static void addFilters(ServletHandler servletHandler) {
        servletHandler.addFilterWithMapping(
                new FilterHolder(new BusinessExceptionHandlingFilter()),
                "/*",
                EnumSet.of(DispatcherType.REQUEST)
        );
        servletHandler.addFilterWithMapping(
                new FilterHolder(new GenericExceptionHandlingFilter()),
                "/*",
                EnumSet.of(DispatcherType.REQUEST)
        );
    }

    private static void addServlets(HotelModule module, ServletHandler servletHandler) {
        servletHandler.addServletWithMapping(
                new ServletHolder(
                        new HelperServlet()
                ),
                "/"
        );
        servletHandler.addServletWithMapping(
                new ServletHolder(
                        new ApartmentsServlet(module.apartmentService(),
                                module.listApartmentsUseCase())
                ),
                "/apartments"
        );
    }

    @SneakyThrows
    public void run() {
        server.start();
    }

    @SneakyThrows
    public void stop() {
        server.stop();
    }
}
