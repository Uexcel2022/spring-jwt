package com.uexcel.spring.jwt.service;

import com.uexcel.spring.jwt.dto.SignUpRequest;
import com.uexcel.spring.jwt.entity.User;

public interface AuthenticationService {

    public User saveUser(SignUpRequest signUpRequest);
}
