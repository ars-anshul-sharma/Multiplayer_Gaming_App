package com.app.game.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Entity
@Table(name="referral_info")
public class ReferralInfo {

    @Id
    @Column(name = "referee_code")
    private String refereeCode;

    @Column(name = "referee_phone_number", unique = true)
    private String refereePhoneNumber;

    @Column(name = "referrer_code")
    private String referrerCode;

    @Column(name = "referrer_phone_number")
    private String referrerPhoneNumber;

    @Column(name = "referrer_commission")
    private double referrerCommission;

    @Column(name = "my_commission")
    private double myCommission;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;

    public ReferralInfo() {
        this.refereeCode = generateReferralCode();
    }

    private String generateReferralCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public String getRefereeCode() {
        return refereeCode;
    }

    public String getRefereePhoneNumber() {
        return refereePhoneNumber;
    }

    public void setRefereePhoneNumber(String refereePhoneNumber) {
        this.refereePhoneNumber = refereePhoneNumber;
    }

    public String getReferrerCode() {
        return referrerCode;
    }

    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    public String getReferrerPhoneNumber() {
        return referrerPhoneNumber;
    }

    public void setReferrerPhoneNumber(String referrerPhoneNumber) {
        this.referrerPhoneNumber = referrerPhoneNumber;
    }

    public double getReferrerCommission() {
        return referrerCommission;
    }

    public void setReferrerCommission(double referrerCommission) {
        this.referrerCommission = referrerCommission;
    }

    public double getMyCommission() {
        return myCommission;
    }

    public void setMyCommission(double myCommission) {
        this.myCommission = myCommission;
    }
}
