package com.fazeyna.dtos.password;

import lombok.Builder;

@Builder
public record ChangePasswordDto(
        String oldPassword,

        String newPassword,

        String confirmPassword
) {
}
