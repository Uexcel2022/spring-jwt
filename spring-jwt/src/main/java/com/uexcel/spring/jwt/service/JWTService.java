package com.uexcel.spring.jwt.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    public String generateToken(UserDetails userDetail);

    public String extractUserName(String token);

    public boolean isValidToken(String token, UserDetails userDetails);

    // public Object generateRefreshToken(UserDetails user);

    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails user);

}
