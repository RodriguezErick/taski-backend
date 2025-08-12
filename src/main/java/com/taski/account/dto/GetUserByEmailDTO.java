package com.taski.account.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class GetUserByEmailDTO {
    @Email
    @NotBlank(message = "Email" + Constants.IS_MANDATORY_FIELD)
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
