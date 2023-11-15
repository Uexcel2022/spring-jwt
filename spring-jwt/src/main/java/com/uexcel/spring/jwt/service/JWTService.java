package com.uexcel.spring.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    public String generateToken(UserDetails userDetail);

    public String extractUserName(String token);

    public boolean isValidToken(String token, UserDetails userDetails);

}
