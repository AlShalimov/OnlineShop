package com.shalimov.onlineShop.dao.jdbs;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceFactory {
    private Properties properties;

    public DataSourceFactory(Properties properties) {
        this.properties = properties;
    }

    public DataSource getDataSource() {
        String dbUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(dbUrl);
        if (user != null) {
            pgSimpleDataSource.setUser(user);
            pgSimpleDataSource.setPassword(password);
        }
        return pgSimpleDataSource;
    }
}

