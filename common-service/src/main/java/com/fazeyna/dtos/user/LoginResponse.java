package com.fazeyna.dtos.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private UserResponse credentials;
}
