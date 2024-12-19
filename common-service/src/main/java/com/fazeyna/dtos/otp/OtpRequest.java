package com.fazeyna.dtos.otp;

import lombok.Data;

@Data
public class OtpRequest {

    private String phoneNumber;

    private String username;

    private String otp;
}
