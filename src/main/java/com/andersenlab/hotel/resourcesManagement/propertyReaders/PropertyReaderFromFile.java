package com.andersenlab.hotel.resourcesManagement.propertyReaders;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.Properties;

@Data
public class PropertyReaderFromFile implements PropertyReader{
    private final String propertyFile;

    @SneakyThrows
    @Override
    public String readProperty(String property) {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile));

        return properties.getProperty(property);
    }
}
