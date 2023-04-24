package com.shalimov.service.impl;

import com.shalimov.dao.UserDao;
import com.shalimov.entity.User;
import com.shalimov.service.UserService;

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
