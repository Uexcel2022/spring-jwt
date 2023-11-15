package com.uexcel.spring.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uexcel.spring.jwt.dto.SignUpRequest;
import com.uexcel.spring.jwt.entity.User;
import com.uexcel.spring.jwt.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> singup(@RequestBody SignUpRequest singInRequest) {

        return ResponseEntity.ok(authenticationService.saveUser(singInRequest));

    }

}
