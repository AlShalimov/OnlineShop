package com.shalimov.onlineShop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {
    private String path;

    public PropertyReader(String path) {
        this.path = path;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        String serverPort = System.getenv("PORT");
        String dbUri = System.getenv("DATABASE_URL");
        Map<String, String> mapDbProperty = parseUri(dbUri);
        properties.setProperty("PORT", serverPort);
        properties.setProperty("JDBC_SERVER", mapDbProperty.get("JDBC_SERVER"));
        properties.setProperty("JDBC_PORT", mapDbProperty.get("JDBC_PORT"));
        properties.setProperty("JDBC_DATABASE", mapDbProperty.get("JDBC_DATABASE"));
        properties.setProperty("JDBC_LOGIN", System.getenv("JDBC_DATABASE_USERNAME"));
        properties.setProperty("JDBC_PASSWORD", System.getenv("JDBC_DATABASE_PASSWORD"));
        return properties;
    }


    private Map<String, String> parseUri(String dbUri) {
        String dbParameters = dbUri.substring(dbUri.indexOf("@") + 1);
        Map<String, String> mapDbProperty = new HashMap<>();
        mapDbProperty.put("JDBC_SERVER", dbParameters.substring(0, dbParameters.indexOf(":")));
        mapDbProperty.put("JDBC_PORT", dbParameters.substring(dbParameters.indexOf(":") + 1, dbParameters.indexOf("/")));
        mapDbProperty.put("JDBC_DATABASE", dbParameters.substring(dbParameters.indexOf("/") + 1));
        return mapDbProperty;
    }

}
