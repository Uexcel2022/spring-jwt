package com.uexcel.spring.jwt.service.Impl;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.uexcel.spring.jwt.entity.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl {

    // generates token
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey() {
        byte[] key = Decoders.BASE64.decode("413F442847294862586553685660597033733676397924422645294848406351");
        return Keys.hmacShaKeyFor(key);

    }

    // Extracts claims
    public <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {

        final Claims claims = extractAllClaims(token);

        return claimResolvers.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey())
                .build().parseClaimsJws(token).getBody();
    }

    // to get user name;
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    // user and token validation
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    // tooken validation
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }
}
