package com.app.game.Dao;

import com.app.game.entity.WithdrawlRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WithdrawlRequestDao extends JpaRepository<WithdrawlRequest,Integer> {
    public WithdrawlRequest findByReqId(int reqId);
    public List<WithdrawlRequest> findByStatus(String status);
}
