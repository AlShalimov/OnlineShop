package com.shalimov.onlineShop.web.servlet.security;

import com.shalimov.onlineShop.security.SecurityService;
import com.shalimov.onlineShop.security.entity.Session;
import com.shalimov.onlineShop.web.templeter.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        pageGenerator.writePage(response.getWriter(), "login.html");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Session session = securityService.login(email, password);
        Cookie cookie = new Cookie("user-token", session.getToken());
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
