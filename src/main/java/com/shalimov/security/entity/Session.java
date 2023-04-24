package com.shalimov.security.entity;

import com.shalimov.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;
}