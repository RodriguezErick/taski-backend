package com.taski.account.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyDTO {
    @NotBlank
    private String token;

    public VerifyDTO(){}

    public VerifyDTO(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
