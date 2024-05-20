package com.app.game.controller;

import com.app.game.Dao.ReferralInfoDao;
import com.app.game.dto.ResponseMessage;
import com.app.game.entity.ReferralInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/referral")
@CrossOrigin(origins = "*")
public class ReferralController {

    @Autowired
    ReferralInfoDao referralInfoDao;

    @PostMapping("/set-referrer")
    public ResponseEntity<?> setReferrer(@RequestBody Map<String,Object> data){
        ReferralInfo rf = referralInfoDao.findByRefereeCode(data.get("refereeCode").toString());
        System.out.println(rf);
        ReferralInfo rfr = referralInfoDao.findByRefereeCode(data.get("referrerCode").toString());
        if(rfr == null) return new ResponseEntity<Error>(new Error("Referrer Not Valid"), HttpStatus.INTERNAL_SERVER_ERROR);
        rf.setReferrerCode(data.get("referrerCode").toString());
        rf.setReferrerPhoneNumber(rfr.getRefereePhoneNumber());
        rf.setReferrerCommission(rfr.getMyCommission());
        referralInfoDao.save(rf);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Referred"), HttpStatus.OK);
    }

    @PostMapping("/find-referrer")
    public ReferralInfo findReferrer(@RequestBody Map<String,Object> data){
        ReferralInfo rf = referralInfoDao.findByRefereeCode(data.get("refereeCode").toString());
        return rf;
    }

    @PostMapping("/generate")
    public ReferralInfo generateReferralCode(@RequestBody Map<String,Object> data){
        ReferralInfo referralInfo = new ReferralInfo();
        referralInfo.setRefereePhoneNumber(data.get("phoneNumber").toString());
        referralInfoDao.save(referralInfo);
        return referralInfo;
    }

    @PostMapping("/my-referral-code")
    public ReferralInfo findMyReferralCode(@RequestBody Map<String,Object> data){
        return referralInfoDao.findByRefereePhoneNumber(data.get("phoneNumber").toString());
    }

    @PostMapping("/set-mycommission")
    public ResponseEntity<?> setMyCommission(@RequestBody Map<String,Object> data){
        ReferralInfo referralInfo = referralInfoDao.findByRefereePhoneNumber(data.get("phoneNumber").toString());
        if(referralInfo == null) return new ResponseEntity<Error>(new Error("Something Went Wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        referralInfo.setMyCommission(Double.valueOf(data.get("myCommission").toString()));
        referralInfoDao.save(referralInfo);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successful!"), HttpStatus.OK);
    }

    @PostMapping("/update-referrer-commissions")
    public ResponseEntity<?> updateReferrerCommissions(@RequestBody Map<String,Object> data){

        List<ReferralInfo> referralInfo = referralInfoDao.findByReferrerPhoneNumber(data.get("phoneNumber").toString());

        if(referralInfo == null) return new ResponseEntity<Error>(new Error("You have no referee!"), HttpStatus.INTERNAL_SERVER_ERROR);

        try {
            for(ReferralInfo rInfo : referralInfo){
                rInfo.setReferrerCommission(Double.valueOf(data.get("myCommission").toString()));
                referralInfoDao.save(rInfo);
            }
        }catch (Exception e){
            return new ResponseEntity<Error>(new Error("Something Went Wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successful!"), HttpStatus.OK);
    }

    @PostMapping("/load-my-referees")
    public List<ReferralInfo> loadMyReferees(@RequestBody Map<String,Object> data){
        return referralInfoDao.findByReferrerPhoneNumber(data.get("phoneNumber").toString());
    }

}
