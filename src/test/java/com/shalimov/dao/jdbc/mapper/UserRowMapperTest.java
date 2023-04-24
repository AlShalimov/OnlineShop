package com.shalimov.dao.jdbc.mapper;

import com.shalimov.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("email")).thenReturn("test@.com");
        when(mockResultSet.getString("salt")).thenReturn("salt");
        when(mockResultSet.getString("user_role")).thenReturn("USER");

        User actual = userRowMapper.mapRow(mockResultSet);

        assertNotNull(actual);

        assertEquals(1L, actual.getId());
        assertEquals("test@.com", actual.getEmail());
        assertEquals("salt", actual.getSalt());
    }
}

