package com.fazeyna.config.sms;

import com.fazeyna.auth.AuthenticationService;
import com.fazeyna.dtos.otp.OtpRequest;
import com.fazeyna.dtos.otp.OtpResponse;
import com.fazeyna.dtos.otp.OtpStatus;
import com.fazeyna.exceptions.IncorrectFieldException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final TwilioConfig twilioConfig;

    private final OtpRepository otpRepository;

    public OtpResponse sendSMS(@RequestBody OtpRequest request) {

        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        OtpResponse otpResponse = null;

        try
        {
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            PhoneNumber to = new PhoneNumber(request.getPhoneNumber());
            String otp = generateOTP();
            String otpMessage = "Cher Utilisateur, voici votre code OTP : " + otp;

            Message.creator(to, from, otpMessage).create();
            otpResponse = new OtpResponse(otpMessage, OtpStatus.DELIVERED);

            OTP codeOTP = OTP.builder()
                    .code(otp)
                    .build();

            otpRepository.save(codeOTP);
        }
        catch (Exception ex){
            otpResponse = new OtpResponse(ex.getMessage(), OtpStatus.FAILED);
        }
        return otpResponse;
    }


    private String generateOTP() {
        return new DecimalFormat("0000")
                .format(new Random().nextInt(9999));
    }

    public String validateOtp(@RequestBody OtpRequest request){

        OTP code = otpRepository.findByCode(request.getOtp()).orElseThrow(() -> new IncorrectFieldException("Code invalide"));

        if(code.isExpired()){
            throw new IncorrectFieldException("Code expiré !");
        }
        else {
            code.setExpired(true);
            otpRepository.save(code);
            return ("Valid otp !");
        }
    }

}
