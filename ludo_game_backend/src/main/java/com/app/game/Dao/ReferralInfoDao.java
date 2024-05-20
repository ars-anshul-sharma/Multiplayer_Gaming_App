package com.app.game.Dao;

import com.app.game.entity.ReferralInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferralInfoDao extends JpaRepository<ReferralInfo,String> {
    public ReferralInfo findByRefereeCode(String refereeCode);
    public ReferralInfo findByRefereePhoneNumber(String phoneNumber);
    public List<ReferralInfo> findByReferrerPhoneNumber(String phoneNumber);
}
