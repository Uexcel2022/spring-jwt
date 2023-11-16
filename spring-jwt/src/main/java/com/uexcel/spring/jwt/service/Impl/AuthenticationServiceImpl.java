package com.uexcel.spring.jwt.service.Impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uexcel.spring.jwt.dto.JWTAuthenticationResponse;
import com.uexcel.spring.jwt.dto.RefrestTokenRquest;
import com.uexcel.spring.jwt.dto.SignUpRequest;
import com.uexcel.spring.jwt.dto.SignInRequest;
import com.uexcel.spring.jwt.entity.Role;
import com.uexcel.spring.jwt.entity.User;
import com.uexcel.spring.jwt.repository.UserRepository;
import com.uexcel.spring.jwt.service.AuthenticationService;
import com.uexcel.spring.jwt.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User saveUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public JWTAuthenticationResponse login(SignInRequest singInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(singInRequest.getEmail(), singInRequest.getPassword()));
        var user = userRepository.findByEmail(singInRequest.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Bad Credentials");
        }

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JWTAuthenticationResponse jResponse = new JWTAuthenticationResponse();
        jResponse.setToken(jwt);
        jResponse.setRefreshToken(refreshToken);
        return jResponse;

    }

    @Override
    public JWTAuthenticationResponse refreshToken(RefrestTokenRquest refrestTokenRquest) {
        String userEmail = jwtService.extractUserName(refrestTokenRquest.getToken());

        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        if (jwtService.isValidToken(refrestTokenRquest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JWTAuthenticationResponse jResponse = new JWTAuthenticationResponse();
            jResponse.setToken(jwt);
            jResponse.setRefreshToken(refrestTokenRquest.getToken());
            return jResponse;
        }

        return null;

    }

}
