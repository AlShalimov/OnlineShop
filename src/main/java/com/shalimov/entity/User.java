package com.shalimov.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class User {
    private long id;
    private String email;
    private String passwordHash;
    private String salt;
    private UserRole userRole;
}
