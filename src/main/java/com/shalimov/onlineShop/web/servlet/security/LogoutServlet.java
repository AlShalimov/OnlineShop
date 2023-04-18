package com.shalimov.onlineShop.web.servlet.security;

import com.shalimov.onlineShop.security.SecurityService;
import com.shalimov.onlineShop.security.entity.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    private SecurityService securityService;

    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Session session = (Session) request.getAttribute("session");
        securityService.logout(session);
        Cookie cookieRemove = new Cookie("user-token", "");
        cookieRemove.setMaxAge(0);
        response.addCookie(cookieRemove);
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            throw new RuntimeException("Logout error", e);
        }
    }
}
