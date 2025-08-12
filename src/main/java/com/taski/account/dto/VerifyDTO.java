package com.taski.account.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public class VerifyDTO {
    @NotBlank(message = "Token" + Constants.IS_MANDATORY_FIELD)
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
