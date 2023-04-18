package com.shalimov.onlineShop.security.impl;

import com.shalimov.onlineShop.entity.User;
import com.shalimov.onlineShop.entity.UserRole;
import com.shalimov.onlineShop.security.SecurityService;
import com.shalimov.onlineShop.security.entity.Session;
import com.shalimov.onlineShop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class DefaultSecurityService implements SecurityService {
    private List<Session> sessions = new ArrayList<>();
    private UserService userService;
    private String passwordParameter;

    public DefaultSecurityService(UserService userService, String passwordParameter) {
        this.userService = userService;
        this.passwordParameter = passwordParameter;
    }

    @Override
    public void register(String email, String password) {
        String salt = UUID.randomUUID().toString();
        String securityPassword = password + salt + passwordParameter;
        String passwordHash = DigestUtils.sha512Hex(securityPassword);

        User user = User.builder()
                .email(email)
                .passwordHash(passwordHash)
                .salt(salt)
                .userRole(UserRole.USER).build();
        userService.addUser(user);
    }

    @Override
    public Session login(String email, String password) {
        User user = userService.getUserByEmail(email);
        String passwordHash = user.getPasswordHash();
        String salt = user.getSalt();
        String expectedPassword = DigestUtils.sha512Hex(password + salt + passwordParameter);
        if (!passwordHash.equals(expectedPassword)) {
            log.error("Login or password is incorrect");
            throw new IllegalArgumentException("Login or password is incorrect");
        }
        Session session = createSession(user);
        return session;

    }

    @Override
    public void logout(Session session) {
        sessions.remove(session);
    }

    @Override
    public Session getSession(String token) {
        if (token != null) {
            for (Session session : sessions) {
                if (session.getToken().equals(token)) {
                    if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                        return session;
                    } else {
                        log.info("Session with token {} has been expired already", token);
                        sessions.remove(session);
                    }
                }
            }
        }
        return null;
    }


    private Session createSession(User user) {
        String token = UUID.randomUUID().toString();
        Session session = Session.builder()
                .user(user)
                .token(token)
                .expireDate(LocalDateTime.now().plusHours(4)).build();
        sessions.add(session);
        return session;
    }
}
