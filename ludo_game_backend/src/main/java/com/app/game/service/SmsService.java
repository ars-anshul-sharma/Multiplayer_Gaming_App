package com.app.game.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.app.game.Dao.LoginAndRegisterDao;
import com.app.game.config.TwilioConfig;
import com.app.game.dto.OtpRequestDto;
import com.app.game.dto.OtpResponseDto;
import com.app.game.dto.OtpStatus;
import com.app.game.dto.OtpValidationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsService {

    @Autowired
    private LoginAndRegisterDao loginAndRegisterDao;

    @Autowired
    private TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();

    public boolean checkIfAlreadyRegistered(String phoneNumber, String userType){
         if(loginAndRegisterDao.checkIfAlreadyRegistered(phoneNumber,userType)) return true;
         return false;
    }


    public OtpResponseDto sendSMS(OtpRequestDto otpRequest) {
        OtpResponseDto otpResponseDto = null;
        try {
             PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
             PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
             String otp = generateOTP();
             String otpMessage = "Dear Customer, Your OTP is " + otp + " Please use this to verify your mobile. Thank You.";
             Message message = Message
                     .creator(to, from,
                             otpMessage)
                     .create();
             otpMap.put(otpRequest.getPhoneNumber(), otp);
             otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

    public boolean validateRegisterOtp(OtpValidationRequestDto otpValidationRequest) {
        String otp = otpMap.get(otpValidationRequest.getPhoneNumber());
        if (otpValidationRequest.getOtpNumber().equals(otp)) {
            otpMap.remove(otpValidationRequest.getPhoneNumber(),otp);
            loginAndRegisterDao.registerNewPhoneNumber(otpValidationRequest.getPhoneNumber(), otpValidationRequest.getUserType());
            return true;
        } else {
            return false;
        }
    }

    public boolean validateLoginOtp(OtpValidationRequestDto otpValidationRequest) {
        String otp = otpMap.get(otpValidationRequest.getPhoneNumber());
        if (otpValidationRequest.getOtpNumber().equals(otp)) {
            otpMap.remove(otpValidationRequest.getPhoneNumber(),otp);
            return true;
        } else {
            return false;
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}