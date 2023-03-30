package com.shalimov.onlineShop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyReader {
    private Properties properties = new Properties();
    private String path;

    public PropertyReader(String path) {
        this.path = path;
    }

    public Properties readProperties() {
        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("No properties on path " + path);
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Can't read properties file " + path, e);
        }
    }

}