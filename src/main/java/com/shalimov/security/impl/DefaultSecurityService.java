package com.shalimov.security.impl;

import com.shalimov.entity.User;
import com.shalimov.entity.UserRole;
import com.shalimov.security.SecurityService;
import com.shalimov.security.entity.Session;
import com.shalimov.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class DefaultSecurityService implements SecurityService {
    private List<Session> sessions = new CopyOnWriteArrayList<>();
    private UserService userService;
    private Properties properties;

    public DefaultSecurityService(UserService userService, Properties properties) {
        this.userService = userService;
        this.properties = properties;
    }

    @Override
    public void register(String email, String password) {
        String salt = UUID.randomUUID().toString();
        String securityPassword = password + salt;
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
        String expectedPasswordHash = DigestUtils.sha512Hex(password + salt);
        if (!passwordHash.equals(expectedPasswordHash)) {
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
        long sessionExpireTime = Long.parseLong(properties.getProperty("session.TimeToLive"));
        Session session = Session.builder()
                .user(user)
                .token(token)
                .expireDate(LocalDateTime.now().plusHours(sessionExpireTime)).build();
        sessions.add(session);
        return session;
    }
}
