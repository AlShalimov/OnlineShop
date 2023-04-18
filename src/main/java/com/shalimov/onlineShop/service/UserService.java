package com.shalimov.onlineShop.service;

import com.shalimov.onlineShop.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    void addUser(User user);
    void deleteByEmail(String email);
}
