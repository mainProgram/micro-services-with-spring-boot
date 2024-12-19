package com.fazeyna.dtos.password;

import lombok.Builder;

@Builder
public record ForgotBody(
        String email
) {
}
