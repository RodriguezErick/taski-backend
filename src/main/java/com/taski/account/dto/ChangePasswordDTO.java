package com.taski.account.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {
    @NotBlank
    private String currentPassword;

    @NotBlank
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
