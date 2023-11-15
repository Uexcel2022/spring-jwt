package com.uexcel.spring.jwt.dto;

import lombok.Data;

@Data
public class SingInRequest {
    private String email;
    private String password;

}
