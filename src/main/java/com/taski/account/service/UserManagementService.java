package com.taski.account.service;

import com.taski.account.dto.ChangePasswordDTO;
import com.taski.account.model.User;
import com.taski.account.repository.UserRepository;
import com.taski.email.service.EmailService;
import com.taski.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }
    public void changePassword(Long userId, ChangePasswordDTO passDTO){
        Optional<User> userOPT = userRepository.getUserById(userId);
        if (userOPT.isEmpty()){
            throw new IllegalStateException("User not found, invalid or expired token.");
        }

        User user = userOPT.get();

        if (!passwordEncoder.matches(passDTO.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Invalid Credentials.");
        }

        String hashedPassword = passwordEncoder.encode(passDTO.getNewPassword());

        boolean updated = userRepository.updatePassword(user.getId(), hashedPassword);

        if(!updated){
            throw new IllegalStateException("Password was not updated.");
        }
    }
}
