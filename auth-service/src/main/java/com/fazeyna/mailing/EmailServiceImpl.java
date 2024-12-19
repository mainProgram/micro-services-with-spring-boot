package com.fazeyna.mailing;

import com.fazeyna.dtos.user.UserResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    private final Configuration freemarkerConfig;

    @Value("${spring.mail.username}")
    String mailSender;

    @Value("${signature.mail}")
    String signature;

    @Value("${url.server}")
    String url;

    private InternetAddress from;

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        from = new InternetAddress(mailSender, "MICRO-SERVICES");
    }

    public void sendEmailAfterRegistering(UserResponse userResponse) throws MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();

        Map<String, Object> model = new HashMap<>();
        model.put("nomComplet", userResponse.getPrenom() + " " + userResponse.getNom());
        model.put("url", url);

        String content = generateEmailContent("account_creation.ftl", model);

        sendMail(message, from, userResponse.getEmail(), content, "Création du compte") ;
    }

    public void sendResetPasswordMail(UserResponse user, String url) throws MessagingException, TemplateException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, Object> model = setUserInTemplate(user);
        model.put("url", url);

        String content = generateEmailContent("forgot-password.ftl", model);
        sendMail(message, from, user.getEmail(), content,"Réinitialisation du mot de passe");
    }

    private void sendMail(MimeMessage message, InternetAddress from, String to, String content, String subject) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content, true);
        helper.setSubject(subject);
        new Thread(() -> {
            javaMailSender.send(message);
        }).start();
    }

    private Map<String, Object> setUserInTemplate( UserResponse user) {
        Map<String, Object> model = new HashMap();
        model.put("nomComplet", user.getPrenom() + " " + user.getNom());

        return model;
    }

    private String generateEmailContent(String templateName, Map<String, Object> model) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
