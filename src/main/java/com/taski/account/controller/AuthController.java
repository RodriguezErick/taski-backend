package com.taski.account.controller;

import com.taski.account.dto.*;
import com.taski.account.service.AuthService;
import com.taski.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taski.utils.ApiResponse;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        try {
            authService.createUser(userDTO);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", userDTO.getUsername());
            userInfo.put("email", userDTO.getEmail());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "User created successfully",
                    HttpStatus.CREATED.value(),
                    userInfo
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataAccessException e) {
            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            if (rootMessage != null && rootMessage.contains("This email is already registered")) {
                ApiResponse<Object> response = new ApiResponse<>("error", rootMessage, HttpStatus.CONFLICT.value());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            if (rootMessage != null && rootMessage.contains("Duplicate entry")) {
                ApiResponse<Object> response = new ApiResponse<>("error", "Username already exists, please try a different one", HttpStatus.CONFLICT.value());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/re-send-verification")
    public ResponseEntity<ApiResponse<?>> resendVerification(@Valid @RequestBody GetUserByEmailDTO emailDTO){
        try{
            authService.resendVerification(emailDTO);

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Verification token sent to: " + emailDTO.getEmail(),
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyEmail(@RequestParam("token") String token){
        try{
            authService.verifyUser(token);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Email verified successfully",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> sendResetPasswordToken(@Valid @RequestBody GetUserByEmailDTO userDTO){
        try{
            authService.forgotPassword(userDTO);

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Reset password token sent to: " + userDTO.getEmail(),
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@Valid @RequestBody ResetPasswordDTO passDTO){
        try{
            authService.resetPassword(passDTO);

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Password changed, now you can Login with your new password",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@Valid @RequestBody LoginDTO loginDTO){
        try{
            String token = authService.login(loginDTO);

            String username = jwtService.extractUsername(token);

            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("username", username);

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Login Success",
                    HttpStatus.OK.value(),
                    data
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }



}
