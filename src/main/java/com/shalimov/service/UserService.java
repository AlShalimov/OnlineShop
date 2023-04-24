package com.shalimov.service;

import com.shalimov.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    void addUser(User user);
    void deleteByEmail(String email);
}
