package com.fazeyna.mailing;


import com.fazeyna.dtos.password.ForgotBody;
import com.fazeyna.dtos.user.UserResponse;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface EmailService {

    void sendEmailAfterRegistering(UserResponse userResponse) throws TemplateException, IOException, MessagingException;


    void sendResetPasswordMail(UserResponse user, String url) throws MessagingException, TemplateException, IOException;


}
