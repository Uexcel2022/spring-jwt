package com.uexcel.spring.jwt.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uexcel.spring.jwt.service.JWTService;
import com.uexcel.spring.jwt.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt;
        final String userEmail;
        final String authHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authHeader) ||
                !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {

            filterChain.doFilter(request, response);
            return;

        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userEmail) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            if (jwtService.isValidToken(jwt, userDetails)) {

            }

        }

        filterChain.doFilter(request, response);
    }

}
