package com.fazeyna.dtos.password;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPasswordBody {

    private String newPassword;

    private String newPasswordConfirm;
}
