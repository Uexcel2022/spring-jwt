package com.uexcel.spring.jwt.service;

import com.uexcel.spring.jwt.dto.JWTAuthenticationResponse;
import com.uexcel.spring.jwt.dto.RefrestTokenRquest;
import com.uexcel.spring.jwt.dto.SignUpRequest;
import com.uexcel.spring.jwt.dto.SignInRequest;
import com.uexcel.spring.jwt.entity.User;

public interface AuthenticationService {

    public User saveUser(SignUpRequest signUpRequest);

    public JWTAuthenticationResponse login(SignInRequest singInRequest);

    public JWTAuthenticationResponse refreshToken(RefrestTokenRquest refrestTokenRquest);
}
