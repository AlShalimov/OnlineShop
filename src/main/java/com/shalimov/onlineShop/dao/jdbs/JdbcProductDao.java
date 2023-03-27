package com.shalimov.onlineShop.dao.jdbs;

import com.shalimov.onlineShop.dao.ProductDao;
import com.shalimov.onlineShop.entity.Product;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final String GET_ALL_PRODUCTS = "SELECT id, name, price, description, image_path FROM Products;";
    private static final String GET_PRODUCT_BY_ID = "SELECT id, name, price, description, image_path FROM Products WHERE id=?;";
    private static final String EDIT_PRODUCT_BY_ID = "UPDATE Products SET name=?, price=?, description=?, image_path=? WHERE id=?;";
    private static final String INSERT_PRODUCT = "INSERT INTO Products (name, price, description, image_path) VALUES (?, ?, ?, ?);";
    private ProductRowMapper productRowMapper = new ProductRowMapper();
    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAll() {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_ALL_PRODUCTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = productRowMapper.rowMap(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Products aren't founded", e);
        }
    }

    public Product getById(long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = dataSource.getConnection().prepareStatement(GET_PRODUCT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Product with id= " + id + " isn't founded");
            }
            Product product = productRowMapper.rowMap(resultSet);
            if (resultSet.next()) {
                throw new RuntimeException("Products with id= " + id + " several");
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Product with id= " + id + " isn't founded", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void edit(Product product) {
        long id = product.getId();
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(EDIT_PRODUCT_BY_ID)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImagePath());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Product with id= " + id + " isn't edited", e);
        }

    }

    public void insert(Product product) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImagePath());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Product isn't inserted", e);
        }
    }

}