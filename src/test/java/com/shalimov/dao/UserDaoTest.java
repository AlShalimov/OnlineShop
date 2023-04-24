package com.shalimov.dao;

import com.shalimov.dao.jdbc.DataSourceFactory;
import com.shalimov.dao.jdbc.JdbcUserDao;
import com.shalimov.entity.User;
import com.shalimov.entity.UserRole;
import com.shalimov.util.PropertyReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserDaoTest {
    private UserDao userDao;

    @BeforeEach
    public void set() {
        String pathToProperty = "application.properties";
        PropertyReader propertyReader = new PropertyReader(pathToProperty);
        DataSourceFactory dataSourceFactory = new DataSourceFactory(propertyReader.readProperties());
        userDao = new JdbcUserDao(dataSourceFactory.getDataSource());
    }

    @Test()
    public void testDaoAddUserGetUserByEmailAndDelete() {
        User user = User.builder()
                .email("test@mail")
                .passwordHash("pasword").salt("test")
                .userRole(UserRole.USER)
                .build();
        userDao.add(user);
        User user1 = userDao.getByEmail(user.getEmail());
        assertNotNull(user1);
        userDao.deleteByEmail(user.getEmail());
//        throw RuntimeException because the user is deleted
        try {
            userDao.getByEmail(user.getEmail());
        } catch (RuntimeException exception){
            assertNotEquals("",exception.getMessage());
        }
    }
}
