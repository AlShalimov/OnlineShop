package com.shalimov.onlineShop.dao.jdbs;

import com.shalimov.onlineShop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product rowMap(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        product.setImagePath(resultSet.getString("image_path"));
        return product;
    }
}
