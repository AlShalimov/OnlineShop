package com.shalimov.onlineShop.dao;

import com.shalimov.onlineShop.entity.User;

public interface UserDao {
    User getByEmail(String email);
    void add(User user);
    void deleteByEmail(String email);
}

