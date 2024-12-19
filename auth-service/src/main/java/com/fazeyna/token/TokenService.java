package com.fazeyna.token;

import com.fazeyna.config.security.JwtService;
import com.fazeyna.exceptions.EntityNotFoundException;
import com.fazeyna.exceptions.IncorrectFieldException;
import com.fazeyna.users.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    public boolean tokenValidator(UserDetails user, String jwtToken) {

        jwtService.isTokenValidOrExpired(user, jwtToken);
        return tokenRepository.findByToken(jwtToken)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    public UserEntity findUserByToken(String token){
        return tokenRepository.findUserByToken(token).orElseThrow(
                () ->  new IncorrectFieldException("Lien de récupération invalide !")
        );
    }

    public Token findByToken(String token)  {
        return tokenRepository.findByToken(token).orElseThrow(
                () ->  new IncorrectFieldException("Token non trouvé !")
        );
    }

    public Token save(Token token){
        return tokenRepository.save(token);
    }


}
