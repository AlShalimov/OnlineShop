package com.shalimov.web.servlet.security;

import com.shalimov.security.SecurityService;
import com.shalimov.web.templeter.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterUserServlet extends HttpServlet {
    private SecurityService securityService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public RegisterUserServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        pageGenerator.writePage(response.getWriter(), "register.html");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        securityService.register(email, password);
        response.sendRedirect("login");
    }
}
