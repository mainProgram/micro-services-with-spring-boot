package com.fazeyna.users.password;

import com.fazeyna.dtos.password.ChangePasswordDto;
import com.fazeyna.dtos.password.ForgotBody;
import com.fazeyna.dtos.password.ResetPasswordBody;
import com.fazeyna.dtos.user.UserResponse;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/account")
public interface PasswordApi {

    @PutMapping("/{id}/change-password")
    ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto);

    @PostMapping("/forgot-password")
    ResponseEntity<?> sendResetPasswordMail(@RequestBody ForgotBody forgotBody) throws MessagingException, TemplateException, IOException;


    @PostMapping("/reset-password")
    ResponseEntity<UserResponse> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @RequestParam String token);

    }
