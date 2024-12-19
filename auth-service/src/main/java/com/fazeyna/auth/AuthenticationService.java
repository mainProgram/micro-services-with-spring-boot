package com.fazeyna.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazeyna.config.security.JwtService;
import com.fazeyna.dtos.user.UserResponse;
import com.fazeyna.exceptions.EntityNotFoundException;
import com.fazeyna.mailing.EmailService;
import com.fazeyna.profil.ProfilEntity;
import com.fazeyna.profil.ProfilRepository;
import com.fazeyna.token.Token;
import com.fazeyna.users.UserMapper;
import com.fazeyna.token.TokenRepository;
import com.fazeyna.token.TokenType;
import com.fazeyna.users.UserEntity;
import com.fazeyna.users.UserRepository;
import com.fazeyna.utils.CustomValidator;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final TokenRepository tokenRepository;

    private final ProfilRepository profilRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper mapper;

    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) throws TemplateException, MessagingException, IOException {

        validateRegisterRequest(request);

        UserEntity user = mapper.mapRequestToEntity(request);

        ProfilEntity profil = profilRepository.findById(request.getIdProfil()).orElseThrow(
                () -> new EntityNotFoundException("Le profil n'existe pas")
        );
        user.setProfil(profil);
        String password = "P@sser123";
        user.setPassword(passwordEncoder.encode(password));

        var savedUser = repository.save(user);
        UserResponse userResponse = mapper.mapEntityToResponse(savedUser);

        var jwtToken = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

       emailService.sendEmailAfterRegistering(userResponse);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void validateRegisterRequest(RegisterRequest request){

        CustomValidator.requireNonBlank(request);
        CustomValidator.requireNonBlank(request.getPrenom(), "Le prénom");
        CustomValidator.requireNonBlank(request.getNom(), "Le nom");
        CustomValidator.requireNonBlank(request.getTelephone(), "Le téléphone");
        CustomValidator.requireNonBlank(request.getIdProfil(), "Le profil");
        CustomValidator.requireNonBlank(request.getMatricule(), "Le matricule");
        CustomValidator.requireNonBlank(request.getEmail(), "L'adresse email");
        if(repository.findByEmail(request.getEmail()).isPresent()){
            CustomValidator.alreadyExists("Cette adresse email");
        }
        if(repository.findByMatricule(request.getMatricule()).isPresent()){
            CustomValidator.alreadyExists("Ce matricule");
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Entité non trouvée"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String generateToken(RegisterRequest request){
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);
    }

    public boolean isTokenValid(String token, String userEmail){
        var user = this.repository.findByEmail(userEmail)
                .orElseThrow();
        return jwtService.isTokenValid(token, user);
    }

    public Long getUserId(@AuthenticationPrincipal UserEntity user) {
        return user.getId();
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "";
    }
}