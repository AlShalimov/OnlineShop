package com.shalimov.onlineShop.web.filter;

import com.shalimov.onlineShop.entity.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class UserAuthenticationFilter extends AuthorizationFilter  {
    @Override
    Set<UserRole> acceptedRoles() {
        return EnumSet.of(UserRole.USER);
    }
}