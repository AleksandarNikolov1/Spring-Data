package com.example.gamestore.models.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserLoginDto {
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "[A-Za-z\\d]{6,}", message = "Invalid password")
    private String password;

    public UserLoginDto(){
    }

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
