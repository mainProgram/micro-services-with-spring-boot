package com.fazeyna.config.auditing;

import com.fazeyna.users.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            return Optional.empty();
        }

        UserEntity user = (UserEntity) authentication.getPrincipal();
        if (user == null || user.getId() == null) {
            return Optional.empty();
        }

        return Optional.of(user.getId().toString());
    }

}
