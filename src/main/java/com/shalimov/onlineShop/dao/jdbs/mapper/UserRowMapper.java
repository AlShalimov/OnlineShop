package com.shalimov.onlineShop.dao.jdbs.mapper;

import com.shalimov.onlineShop.entity.User;
import com.shalimov.onlineShop.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .passwordHash(resultSet.getString("password_hash"))
                .salt(resultSet.getString("salt"))
                .userRole(UserRole.getByUserRole(resultSet.getString("user_role"))).build();
    }
}
