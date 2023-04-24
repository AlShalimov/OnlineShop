package com.shalimov.dao;

import com.shalimov.entity.User;

public interface UserDao {
    User getByEmail(String email);
    void add(User user);
    void deleteByEmail(String email);
}

