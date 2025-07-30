package com.taski.account.repository;

import com.taski.account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createUser(User user) {
        jdbcTemplate.update("CALL sp_create_user(?, ?, ?, ?, ?)",
                user.getUsername(), user.getEmail(), user.getPassword(), user.getEmailVerified(), user.getVerificationToken());
    }

    public Optional<User> getUserByEmail(String email) {
        return jdbcTemplate.query(
                "CALL sp_get_user_by_email(?)",
                rs -> {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setEmailVerified(rs.getBoolean("email_verified"));
                        user.setVerificationToken(rs.getString("verification_token"));
                        user.setPassword(rs.getString("password_hash"));
                        return Optional.of(user);
                    }
                    return Optional.empty();
                },
                email
        );
    }

    public boolean updateVerificationToken(Long id, String token) {
        int rows = jdbcTemplate.update(
                "UPDATE tbl_users SET verification_token = ? WHERE id = ?",
                token, id
        );
        return rows > 0;
    }

    public boolean verifyEmail(String token) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_verify_email")
                .declareParameters(
                        new SqlParameter("p_token", Types.VARCHAR),
                        new SqlOutParameter("p_result", Types.INTEGER)
                );

        Map<String, Object> params = new HashMap<>();
        params.put("p_token", token);

        Map<String, Object> result = jdbcCall.execute(params);

        Integer rowsAffected = (Integer) result.get("p_result");

        return rowsAffected != null && rowsAffected > 0;
    }

    public boolean updateResetPasswordToken(Long id, String token, LocalDateTime expiration){
        int rows = jdbcTemplate.update(
                "UPDATE tbl_users SET reset_token = ?, reset_token_expiration = ? WHERE id = ?",
                token, expiration, id
        );
        return rows > 0;
    }

    public Optional<User> getUserByResetPassToken(String token) {
        return jdbcTemplate.query(
                "CALL sp_get_user_by_reset_pass_token(?)",
                rs -> {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setResetToken(rs.getString("reset_token"));
                        user.setResetTokenExpiration(rs.getTimestamp("reset_token_expiration").toLocalDateTime());
                        return Optional.of(user);
                    }
                    return Optional.empty();
                },
                token
        );
    }

    public boolean updatePassword(Long id, String newPassword){
        int rows = jdbcTemplate.update("CALL sp_update_user_password(?, ?)", id, newPassword);
        return rows > 0;
    }

    public Optional<User> getUserById(Long id){
        return jdbcTemplate.query(
                "CALL sp_get_user_by_id(?)",
                rs -> {
                    if (rs.next()){
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password_hash"));
                        return Optional.of(user);
                    }
                    return Optional.empty();
                }, id
        );
    }
}
