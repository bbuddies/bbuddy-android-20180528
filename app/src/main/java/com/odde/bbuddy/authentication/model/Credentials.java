package com.odde.bbuddy.authentication.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Credentials {

    private final String email;
    private final String password;

}
