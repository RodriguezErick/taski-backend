package com.taski.account.controller;

import com.taski.account.dto.ChangePasswordDTO;
import com.taski.account.service.AuthService;
import com.taski.account.service.UserManagementService;
import com.taski.security.JwtService;
import com.taski.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/user")
public class UserManagementController {
    private final UserManagementService userManagementService;
    private final JwtService jwtService;

    public UserManagementController(UserManagementService userManagementService, JwtService jwtService){
        this.userManagementService = userManagementService;
        this.jwtService = jwtService;
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody ChangePasswordDTO passDTO){
        try{
            String token = authHeader.replace("Bearer ", "");
            Long userId = jwtService.extractUserId(token);
            userManagementService.changePassword(userId, passDTO);

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
}
