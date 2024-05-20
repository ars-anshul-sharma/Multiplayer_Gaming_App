package com.app.game.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="withdraw_info")
public class WithdrawDetails {

    @Id
    @Column(name = "registered_number")
    private String regPhoneNumber;

    @Column(name = "paytm_number")
    private String paytmNumber;

    @Column(name = "phonepay_number")
    private String phonePayNumber;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "account_holder_name")
    private String name;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    public String getRegPhoneNumber() {
        return regPhoneNumber;
    }

    public void setRegPhoneNumber(String regNhoneNumber) {
        this.regPhoneNumber = regNhoneNumber;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public String getPhonePayNumber() {
        return phonePayNumber;
    }

    public void setPhonePayNumber(String phonePayNumber) {
        this.phonePayNumber = phonePayNumber;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
