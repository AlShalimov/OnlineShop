package com.shalimov.web.filter;

import com.shalimov.security.SecurityService;
import com.shalimov.security.entity.Session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilter implements Filter {
    private SecurityService securityService;

    public AuthenticationFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = getToken(httpServletRequest);
        Session session = securityService.getSession(token);
        if (session != null) {
            httpServletRequest.setAttribute("user", session.getUser());
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}