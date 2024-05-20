package com.app.game.Dao;

import com.app.game.entity.WithdrawDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawDetailsDao extends JpaRepository<WithdrawDetails,String> {
    public WithdrawDetails findByRegPhoneNumber(String phoneNumber);
}
