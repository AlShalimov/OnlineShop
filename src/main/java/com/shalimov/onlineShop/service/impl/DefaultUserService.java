package com.shalimov.onlineShop.service.impl;

import com.shalimov.onlineShop.dao.UserDao;
import com.shalimov.onlineShop.entity.User;
import com.shalimov.onlineShop.service.UserService;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userDao.add(user);
    }

    @Override
    public void deleteByEmail(String email) {
        userDao.deleteByEmail(email);
    }
}
