package com.shalimov.onlineShop.dao;

import com.shalimov.onlineShop.dao.jdbs.DataSourceFactory;
import com.shalimov.onlineShop.dao.jdbs.JdbcProductDao;
import com.shalimov.onlineShop.entity.Product;
import com.shalimov.onlineShop.util.PropertyReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ProductDaoTest {

    private ProductDao productDao;

    @BeforeEach
    public void set() {
        String pathToProperty = "application.properties";
        PropertyReader propertyReader = new PropertyReader(pathToProperty);
        DataSourceFactory dataSourceFactory = new DataSourceFactory(propertyReader.readProperties());
        productDao = new JdbcProductDao(dataSourceFactory.getDataSource());
    }

    @Test
    public void testDaoReturnListProduct() {
        Product product1 = Product.builder()
                .name("name").description("text").price(12).build();
        productDao.add(product1);
        List<Product> products = productDao.getAll();
        assertNotNull(products);
        Product product = productDao.getByName("name");
        productDao.deleteById(product.getId());

    }

    @Test
    public void testDaoAddProductAndDelete() {
        Product product1 = Product.builder()
                .name("name").description("text").price(12).build();
        productDao.add(product1);
        Product product2 = productDao.getByName("name");
        assertNotNull(product2);
        productDao.deleteById(product2.getId());
    }

    @Test
    public void testDaoEditProduct() {
        Product product = Product.builder()
                .name("name").description("text").price(12).build();
        productDao.add(product);
        product = productDao.getByName("name");
        product.setName("newName");
        product.setDescription("newText");
        product.setPrice(13);
        product.setId(product.getId());
        productDao.edit(product);
        product = productDao.getByName("newName");
        assertEquals("newName", product.getName());
        assertEquals("newText", product.getDescription());
        assertEquals(13, product.getPrice());
        productDao.deleteById(product.getId());

    }

}