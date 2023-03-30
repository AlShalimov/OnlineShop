package com.shalimov.onlineShop.dao.jdbs;

import com.shalimov.onlineShop.dao.ProductDao;
import com.shalimov.onlineShop.dao.jdbs.mapper.ProductRowMapper;
import com.shalimov.onlineShop.entity.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final String GET_ALL_PRODUCTS = "SELECT id, name, price, description FROM online_shop;";
    private static final String ADD_PRODUCT = "INSERT INTO online_shop  (name, price, description) " +
            "VALUES (?, ?, ?);";
    private static final String GET_PRODUCT_BY_ID = "SELECT id, name, price, description FROM online_shop " +
            "WHERE id=?;";
    private static final String GET_PRODUCT_BY_NAME = "SELECT id, name, price, description FROM online_shop " +
            "WHERE name=?;";

    private static final String EDIT_PRODUCT = "UPDATE online_shop SET name=?, price=?, description=?" +
            "WHERE id=?;";
    private static final String DELETE_PRODUCT = "DELETE FROM online_shop WHERE id=?;";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> allProducts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException e) {
            throw new RuntimeException("Products aren't found", e);
        }
    }

    @Override
    public void add(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Product don't inserted", e);
        }
    }

    @Override
    public Product getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("Product with id " + id + " not found");
                }
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Product with id " + id + " not found", e);
        }
    }

    @Override
    public void edit(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setLong(4, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Product with id " + product.getId() + " not edit", e);
        }
    }


    @Override
    public void deleteById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Product with id " + id + " has not been removed", e);

        }
    }

    @Override
    public Product getByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("Product with id " + name + " not found");
                }
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Product with id " + name + " not found", e);
        }
    }
}