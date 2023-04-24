package com.shalimov.dao.jdbc;

import com.shalimov.dao.UserDao;
import com.shalimov.dao.jdbc.mapper.UserRowMapper;
import com.shalimov.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JdbcUserDao implements UserDao {
    private String GET_USER_BY_EMAIL = "SELECT id, email, password_hash, salt, user_role FROM Users WHERE email=?;";
    private String ADD_USER = "INSERT INTO Users (email, password_hash, salt, user_role) VALUES (?, ?, ?, ?);";
    private String DELETE_USER_BY_EMAIL = "DELETE FROM Users WHERE email=?;";
    private UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getByEmail(String email) {
        log.info("Get user with email :{}", email);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    log.error("User with email :{} not found", email);
                    throw new RuntimeException("User with email " + email + " not found");
                }
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                return user;
            }
        } catch (SQLException e) {
            log.error("User with email :{} not found", email, e);
            throw new RuntimeException("User with email " + email + " not found", e);
        }
    }

    @Override
    public void add(User user) {
        log.info("Add new user");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getUserRole().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("New user is not added", e);
            throw new RuntimeException("New user is not added", e);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User with email " + email + " has not been removed", e);
        }
    }
}
