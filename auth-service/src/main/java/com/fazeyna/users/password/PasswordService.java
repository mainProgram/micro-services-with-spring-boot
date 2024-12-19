package com.fazeyna.users.password;

import com.fazeyna.dtos.password.ChangePasswordDto;
import com.fazeyna.dtos.password.ForgotBody;
import com.fazeyna.dtos.password.ResetPasswordBody;
import com.fazeyna.dtos.user.UserResponse;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface PasswordService {

    UserResponse changePassword(Long id, ChangePasswordDto changePasswordDto);

    boolean sendResetPasswordMail(@RequestBody ForgotBody forgotBody) throws MessagingException, TemplateException, IOException;

    UserResponse resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @RequestParam String token);

    }
