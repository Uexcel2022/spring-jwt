package com.uexcel.spring.jwt.model;

import lombok.Data;

@Data
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String matchingPassword;
}
