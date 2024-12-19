package com.fazeyna.users.password;

import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.password.ChangePasswordDto;
import com.fazeyna.dtos.password.ForgotBody;
import com.fazeyna.dtos.password.ResetPasswordBody;
import com.fazeyna.dtos.user.UserResponse;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PasswordController implements PasswordApi {

    private final PasswordService service;

    @Override
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto) {

        try {
            UserResponse response = service.changePassword(id, changePasswordDto);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> sendResetPasswordMail(@RequestBody ForgotBody forgotBody) throws MessagingException, TemplateException, IOException {
        try {
            service.sendResetPasswordMail(forgotBody);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserResponse> resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @RequestParam String token){
        try {
            UserResponse response = service.resetPassword(resetPasswordBody, token);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
