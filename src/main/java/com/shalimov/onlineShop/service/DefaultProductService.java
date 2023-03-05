package com.shalimov.onlineShop.service;

import com.shalimov.onlineShop.entity.Product;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultProductService implements ProductService {
    private static final String GET_ALL_PRODUCTS = "SELECT id, name, price, description;";
    private static final String GET_PRODUCT_BY_ID = "SELECT id, name, price, description FROM Products WHERE id=?;";
    private static final String EDIT_PRODUCT_BY_ID = "UPDATE Products SET name=?, price=?, description=? WHERE id=?;";
    private static final String INSERT_PRODUCT = "INSERT INTO Products (name, price, description) VALUES (?, ?, ?);";
    private DataSource dataSource;

    @Override
    public Product getById(long id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_ALL_PRODUCTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = rowMap(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Products aren't founded", e);
        }
    }

    @Override
    public void edit(Product product) {

    }

    @Override
    public void insert(Product product) {

    }

    private Product rowMap(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDescription(resultSet.getString("description"));
        return product;
    }
}
