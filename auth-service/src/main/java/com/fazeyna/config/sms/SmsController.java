package com.fazeyna.config.sms;


import com.fazeyna.config.responses.ResponseHandler;
import com.fazeyna.constantes.TextConstant;
import com.fazeyna.dtos.otp.OtpRequest;
import com.fazeyna.dtos.otp.OtpResponse;
import com.fazeyna.dtos.otp.OtpStatus;
import com.fazeyna.exceptions.IncorrectFieldException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    private final OtpRepository otpRepository;

    Map<String, String> otpMap = new HashMap<>();

    @PostMapping(value = "/send")
    public ResponseEntity<OtpResponse> sendSMS(@RequestBody OtpRequest request) {

       try{
           OtpResponse response = smsService.sendSMS(request);
           return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
       } catch (Exception e){
           return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @PostMapping(value = "/validate")
    public ResponseEntity<String> validateOtp(@RequestBody OtpRequest request){

        try{
            String response = smsService.validateOtp(request);
            return ResponseHandler.generateSuccessResponse(TextConstant.SUCCESS, HttpStatus.OK, response);
        } catch (Exception e){
            return ResponseHandler.generateErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
