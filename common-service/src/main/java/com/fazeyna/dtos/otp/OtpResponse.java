package com.fazeyna.dtos.otp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {

    private String message;

    private OtpStatus otpStatus;
}
