package com.shalimov.onlineShop.web.filter;

import com.shalimov.onlineShop.entity.User;
import com.shalimov.onlineShop.entity.UserRole;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
public abstract class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        User user = (User) httpServletRequest.getAttribute("user");
        if (user != null) {
            if (acceptedRoles().contains(user.getUserRole())) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
        httpServletResponse.sendRedirect("/login");

    }

    @Override
    public void destroy() {
    }

    abstract Set<UserRole> acceptedRoles();
}
