package com.fazeyna.users.password;

import com.fazeyna.auth.AuthenticationService;
import com.fazeyna.config.security.JwtService;
import com.fazeyna.dtos.password.ChangePasswordDto;
import com.fazeyna.dtos.password.ForgotBody;
import com.fazeyna.dtos.password.ResetPasswordBody;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.exceptions.RequiredFieldException;
import com.fazeyna.mailing.EmailService;
import com.fazeyna.token.Token;
import com.fazeyna.token.TokenService;
import com.fazeyna.users.UserEntity;
import com.fazeyna.users.UserService;
import com.fazeyna.utils.CustomValidator;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fazeyna.exceptions.IncorrectFieldException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenService tokenService;

    private final EmailService emailService;

    private final AuthenticationService authenticationService;

    private final Environment environment;

    private final PasswordEncoder encoder;


    public boolean sendResetPasswordMail(@RequestBody ForgotBody forgotBody) throws MessagingException, TemplateException, IOException {
            var user = userService.findByMail(forgotBody.email());

            var jwtToken = jwtService.generateToken(user);
            authenticationService.saveUserToken(user, jwtToken);

            StringBuilder sb = new StringBuilder();
            sb.append("http://").append(environment.getProperty("front.host"))
                    .append("/").append(environment.getProperty("front.context")).append("/")
                    .append("account").append("/")
                    .append("reset-password")
                    .append("?token=")
                    .append(jwtToken);
            String url = sb.toString();

            emailService.sendResetPasswordMail(userService.mapEntityToResponse(user), url);
            return true;
    }

    public UserResponse changePassword(Long id, ChangePasswordDto changePasswordDto) {

        UserEntity userEntity = userService.findById(id);

        if (!passwordEncoder.matches(changePasswordDto.oldPassword(), userEntity.getPassword())) {
            throw new RequiredFieldException("L'ancien mot de passe est incorrect.");
        }
        CustomValidator.requireNonBlank(changePasswordDto.newPassword(), "Le mot de passe");
        CustomValidator.validatePassword(changePasswordDto.newPassword());
        CustomValidator.equalValues(changePasswordDto.newPassword(), changePasswordDto.confirmPassword());

        userEntity.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userEntity.setNew(false);

        return userService.save(userEntity);
    }

    public UserResponse changePasswordWithoutOld(Long id, ChangePasswordDto changePasswordDto) {

        UserEntity userEntity = userService.findById(id);

        CustomValidator.requireNonBlank(changePasswordDto.newPassword(), "Le mot de passe");
        CustomValidator.validatePassword(changePasswordDto.newPassword());
        CustomValidator.equalValues(changePasswordDto.newPassword(), changePasswordDto.confirmPassword());

        userEntity.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userEntity.setNew(false);

        return userService.save(userEntity);
    }

    public UserResponse resetPassword(@RequestBody ResetPasswordBody resetPasswordBody, @RequestParam String token){

        UserEntity user = tokenService.findUserByToken(token);
        Token tokenToEdit = tokenService.findByToken(token);
        if(user != null) {

            if(tokenService.tokenValidator(user, token)) {
                CustomValidator.validatePassword(resetPasswordBody.getNewPassword());
                CustomValidator.equalValues(resetPasswordBody.getNewPassword(), resetPasswordBody.getNewPasswordConfirm());

                user.setPassword(passwordEncoder.encode(resetPasswordBody.getNewPassword()));
                user.setNew(false);
                tokenToEdit.setExpired(true); tokenToEdit.setRevoked(true);

                UserResponse userResponse = userService.save(user);
                if (userResponse != null) {
                    tokenService.save(tokenToEdit);
                    return userResponse;
                }
            }
            else {
                throw new IncorrectFieldException("Lien de récupération invalide !");
            }
        }
        return null;
    }


}
