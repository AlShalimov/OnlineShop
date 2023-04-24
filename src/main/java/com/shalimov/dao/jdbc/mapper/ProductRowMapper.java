package com.shalimov.dao.jdbc.mapper;

import com.shalimov.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getDouble("price"))
                .description(resultSet.getString("description")).build();
    }
}
