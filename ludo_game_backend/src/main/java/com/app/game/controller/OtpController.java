package com.app.game.controller;

import com.app.game.dto.*;
import com.app.game.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@Slf4j
@CrossOrigin(origins = "*")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequestDto otpRequest) {
        return smsService.sendSMS(otpRequest);
    }

    @PostMapping("/validate-register-otp")
    public OtpValidationResponseDto validateRegisterOtp(@RequestBody OtpValidationRequestDto otpValidationRequest) {
        OtpValidationResponseDto validationResponseDto = new OtpValidationResponseDto();
        if(!smsService.checkIfAlreadyRegistered(otpValidationRequest.getPhoneNumber(),otpValidationRequest.getUserType())) {
            if (smsService.validateRegisterOtp(otpValidationRequest)) {
                validationResponseDto.setStatus(ValidationStatus.SUCCESS);
                validationResponseDto.setMessage("Validation Successful");
            } else {
                validationResponseDto.setStatus(ValidationStatus.FAILURE);
                validationResponseDto.setMessage("Validation Not Successful");
            }
        } else {
            validationResponseDto.setStatus(ValidationStatus.FAILURE);
            if(otpValidationRequest.getUserType().equals("ADMIN") || otpValidationRequest.getUserType().equals("USER"))
                validationResponseDto.setMessage("Phone Number Already Registered!");
            else validationResponseDto.setMessage("Please select correct UserType");
        }
        return validationResponseDto;
    }

    @PostMapping("/validate-login-otp")
    public OtpValidationResponseDto validateLoginOtp(@RequestBody OtpValidationRequestDto otpValidationRequest) {
        OtpValidationResponseDto validationResponseDto = new OtpValidationResponseDto();
        if(smsService.checkIfAlreadyRegistered(otpValidationRequest.getPhoneNumber(),otpValidationRequest.getUserType())) {
            if (smsService.validateLoginOtp(otpValidationRequest)) {
                validationResponseDto.setStatus(ValidationStatus.SUCCESS);
                validationResponseDto.setMessage("Validation Successful");
            } else {
                validationResponseDto.setStatus(ValidationStatus.FAILURE);
                validationResponseDto.setMessage("Validation Not Successful");
            }
        } else {
            validationResponseDto.setStatus(ValidationStatus.FAILURE);
            validationResponseDto.setMessage("Phone Number Not Registered");
        }
        return validationResponseDto;
    }

}