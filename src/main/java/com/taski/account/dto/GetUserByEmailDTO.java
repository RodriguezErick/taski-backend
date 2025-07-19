package com.taski.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class GetUserByEmailDTO {
    @Email
    @NotBlank(message = "Email is required.")
    private String email;

    public GetUserByEmailDTO(){}

    public GetUserByEmailDTO(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
