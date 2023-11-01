package com.andersenlab.hotel;

import com.andersenlab.hotel.reader.PropertyReaderFromFile;
import com.andersenlab.hotel.service.ContextBuilder;
import com.andersenlab.hotel.http.ServletStarter;
import com.andersenlab.hotel.repository.jdbc.JdbcConnector;

public class Main {

    public static void main(String[] args) {
        final HotelModule context = initContext();
        getStarter(context).run();
    }

    public static ServletStarter getStarter(HotelModule context) {
        return ServletStarter.forModule(context);
    }

    public static HotelModule initContext() {
        PropertyReaderFromFile propertyReaderFromFile = new PropertyReaderFromFile("application.properties");
        String location = propertyReaderFromFile.readProperty("location");
        String abilityApartmentToChange = propertyReaderFromFile.readProperty("apartment.change.enabled");
        String jdbcUrl = propertyReaderFromFile.readProperty("jdbc.url");
        String jdbcUser = propertyReaderFromFile.readProperty("jdbc.user");
        String jdbcPassword = propertyReaderFromFile.readProperty("jdbc.password");
        JdbcConnector jdbc = new JdbcConnector(jdbcUrl, jdbcUser, jdbcPassword)
                .migrate();

        return new ContextBuilder().initFile(location).initServices()
                .initCheckInCheckOut(Boolean.parseBoolean(abilityApartmentToChange))
                .build();
    }
}