package com.shalimov.dao.jdbc.mapper;

import com.shalimov.entity.User;
import com.shalimov.entity.UserRole;

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
