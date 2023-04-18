package com.shalimov.onlineShop.security.entity;

import com.shalimov.onlineShop.entity.User;
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