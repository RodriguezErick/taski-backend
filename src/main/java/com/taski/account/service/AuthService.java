package com.taski.account.service;

import com.taski.account.dto.*;
import com.taski.account.model.User;
import com.taski.account.repository.UserRepository;
import com.taski.email.service.EmailService;
import com.taski.security.JwtService;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }

    public void createUser(CreateUserDTO userDTO){
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO.getEmail(), userDTO.getUsername(), hashedPassword);
        user.setEmailVerified(false);
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.createUser(user);

        try{
            emailService.sendEmailVerificationToken(user.getEmail(), user.getVerificationToken());
        } catch (MailException ex) {
            throw new IllegalStateException("Fail to sent verification email to "  + user.getEmail() + " : " + ex.getMessage());
        }
    }

    public void verifyUser(String token){
        boolean verified = userRepository.verifyEmail(token);

        if (!verified){
            throw new IllegalStateException("Failure verifying Email: token may be invalid or expired.");
        }
    }

    public void resendVerification(GetUserByEmailDTO userDTO) {
        Optional<User> userOPT = userRepository.getUserByEmail(userDTO.getEmail());

        if (userOPT.isEmpty()) {
            throw new IllegalStateException("User not found for email: " + userDTO.getEmail());
        }

        User user = userOPT.get();

        if (user.getEmailVerified()) {
            throw new IllegalStateException("Email is already verified.");
        }

        String newToken = UUID.randomUUID().toString();
        user.setVerificationToken(newToken);

        boolean updated = userRepository.updateVerificationToken(user.getId(), newToken);
        if (!updated) {
            throw new IllegalStateException("Token not updated, User might not exist");
        }

        emailService.sendEmailVerificationToken(user.getEmail(), newToken);
    }

    public void forgotPassword(GetUserByEmailDTO userDTO){
        Optional<User> userOPT = userRepository.getUserByEmail(userDTO.getEmail());

        if (userOPT.isEmpty()) {
            throw new IllegalStateException("User not found for email: " + userDTO.getEmail());
        }

        User user = userOPT.get();

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(1);

        boolean updated = userRepository.updateResetPasswordToken(user.getId(), token, expiration);

        if (!updated){
            throw new IllegalStateException("Token not updated, User might not exist.");
        }

        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }

    public void resetPassword(ResetPasswordDTO passDTO){
        Optional<User> userOPT = userRepository.getUserByResetPassToken(passDTO.getToken());
        if (userOPT.isEmpty()){
            throw new IllegalStateException("User not found, token may not be generated.");
        }

        User user = userOPT.get();
        String hashedPassword = passwordEncoder.encode(passDTO.getNewPassword());

        boolean updated = userRepository.updatePassword(user.getId(), hashedPassword);

        if(!updated){
            throw new IllegalStateException("Password was not updated.");
        }
    }

    public String login(LoginDTO loginDTO) {
        String invalid = "Invalid credentials.";
        Optional<User> userOPT = userRepository.getUserByEmail(loginDTO.getEmail());
        if (userOPT.isEmpty()){
            throw new IllegalStateException(invalid);
        }

        User user = userOPT.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new IllegalStateException(invalid);
        }

        if (!user.getEmailVerified()) {
            throw new IllegalStateException("Email not verified.");
        }

        return jwtService.generateToken(user);
    }

}
