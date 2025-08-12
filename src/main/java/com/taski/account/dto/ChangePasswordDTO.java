package com.taski.account.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {
    @NotBlank(message = "Current Password" + Constants.IS_MANDATORY_FIELD)
    private String currentPassword;

    @NotBlank(message = "New Password" + Constants.IS_MANDATORY_FIELD)
    private String newPassword;

    public ChangePasswordDTO(){}

    public ChangePasswordDTO(String currentPassword, String newPassword){
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
