package com.taski.account.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public class ResetPasswordDTO {
    @NotBlank(message = "Token" + Constants.IS_MANDATORY_FIELD)
    private String token;

    @NotBlank(message = "New Password" + Constants.IS_MANDATORY_FIELD)
    private String newPassword;

    public ResetPasswordDTO(){}

    public ResetPasswordDTO(String token, String newPassword){
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
