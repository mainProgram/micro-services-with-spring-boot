package com.fazeyna.auth;

import com.fazeyna.users.UserEntity;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws TemplateException, MessagingException, IOException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody RegisterRequest request){
        return  service.generateToken(request);
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token, @RequestParam String email ){
        return  service.isTokenValid(token, email);
    }

    @GetMapping(value = "/user-id")
    public Long getUserId(@AuthenticationPrincipal UserEntity user) {
        return service.getUserId(user);
    }
}
