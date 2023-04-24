package com.shalimov.web.filter;

import com.shalimov.entity.User;
import com.shalimov.entity.UserRole;
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
        if (user == null) {
            httpServletResponse.sendRedirect("/login");
        } else if (acceptedRoles().contains(user.getUserRole())) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
    }

    abstract Set<UserRole> acceptedRoles();
}
