package com.shalimov.onlineShop.security;

import com.shalimov.onlineShop.security.entity.Session;

public interface SecurityService {
    void register(String email, String password);
    Session login(String email, String password);
    void logout(Session session);
    Session getSession(String token);
}
