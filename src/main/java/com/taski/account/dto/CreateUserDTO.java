package com.taski.account.dto;
import com.taski.utils.Constants;
import jakarta.validation.constraints.*;
public class CreateUserDTO {

    @NotBlank(message = "Username" + Constants.IS_MANDATORY_FIELD)
    private String username;

    @Email
    @NotBlank(message = "Email" + Constants.IS_MANDATORY_FIELD)
    private String email;

    @NotBlank(message = "Password" + Constants.IS_MANDATORY_FIELD)
    private String password;

    public CreateUserDTO() {}

    public CreateUserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
