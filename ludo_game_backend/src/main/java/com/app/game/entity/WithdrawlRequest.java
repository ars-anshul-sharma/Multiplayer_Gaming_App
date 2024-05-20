package com.app.game.entity;

import jakarta.persistence.*;

@Entity
@Table(name="withdrawl_request")
public class WithdrawlRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reqId;

    @Column(name = "withdrawl_amount")
    private double withdrawlAmount;

    @Column(name = "status")
    private String status;

    @JoinColumn(name = "withdraw_info")
    @ManyToOne
    private WithdrawDetails withdrawDetails;

    public int getReqId() {
        return reqId;
    }

    public double getWithdrawlAmount() {
        return withdrawlAmount;
    }

    public void setWithdrawlAmount(double withdrawlAmount) {
        this.withdrawlAmount = withdrawlAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WithdrawDetails getWithdrawDetails() {
        return withdrawDetails;
    }

    public void setWithdrawDetails(WithdrawDetails withdrawDetails) {
        this.withdrawDetails = withdrawDetails;
    }
}
