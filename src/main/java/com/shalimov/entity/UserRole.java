package com.shalimov.entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public static UserRole getByUserRole(String name) {
        for (UserRole userRole : values()) {
            if (userRole.name.equalsIgnoreCase(name)) {
                return userRole;
            }
        }
        log.error("No user with name :{}", name);
        throw new IllegalArgumentException("No user with name " + name);
    }

    public String getName() {
        return name;
    }
}


