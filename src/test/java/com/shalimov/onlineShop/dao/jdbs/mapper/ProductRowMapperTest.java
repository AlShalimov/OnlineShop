package com.shalimov.onlineShop.dao.jdbs.mapper;

import com.shalimov.onlineShop.entity.Product;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("iphone");
        when(mockResultSet.getDouble("price")).thenReturn(48000.00);
        when(mockResultSet.getString("description")).thenReturn("apple iphone 15");

        Product actual = productRowMapper.mapRow(mockResultSet);

        assertNotNull(actual);

        assertEquals(1L, actual.getId());
        assertEquals("iphone", actual.getName());
        assertEquals(48000.00, actual.getPrice());
        assertEquals("apple iphone 15", actual.getDescription());

    }

}