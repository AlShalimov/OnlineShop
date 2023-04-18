package com.shalimov.onlineShop.dao;

import com.shalimov.onlineShop.dao.jdbs.DataSourceFactory;
import com.shalimov.onlineShop.dao.jdbs.JdbcUserDao;
import com.shalimov.onlineShop.entity.User;
import com.shalimov.onlineShop.util.PropertyReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void set() {
        String pathToProperty = "application.properties";
        PropertyReader propertyReader = new PropertyReader(pathToProperty);
        DataSourceFactory dataSourceFactory = new DataSourceFactory(propertyReader.readProperties());
        userDao = new JdbcUserDao(dataSourceFactory.getDataSource());
    }

    @Test(expected = RuntimeException.class)
    public void testDaoAddUserGetUserByEmailAndDelete() {
        User user = User.builder()
                .email("test@mail")
                .passwordHash("pasword").salt("test").build();
        userDao.add(user);
        User user1 = userDao.getByEmail(user.getEmail());
        assertNotNull(user1);
        userDao.deleteByEmail(user.getEmail());
//        throw RuntimeException because the user is deleted
        userDao.getByEmail(user.getEmail());
    }
}
