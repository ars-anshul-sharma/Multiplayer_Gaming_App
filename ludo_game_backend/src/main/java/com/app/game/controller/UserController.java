package com.app.game.controller;

import com.app.game.Dao.UserDao;
import com.app.game.Dao.WithdrawDetailsDao;
import com.app.game.Dao.WithdrawlRequestDao;
import com.app.game.dto.ResponseMessage;
import com.app.game.entity.User;
import com.app.game.entity.WithdrawDetails;
import com.app.game.entity.WithdrawlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    WithdrawDetailsDao withdrawDetailsDao;

    @Autowired
    WithdrawlRequestDao withdrawlRequestDao;

    @PostMapping("/update")
    public ResponseEntity<?> updateUserName(@RequestBody User user){
        User u = userDao.findByPhoneNumber(user.getPhoneNumber());
        if(u != null){
            u.setUserName(user.getUserName());
            try {
                userDao.save(u);
            }catch (Exception e){
                System.out.println(e);
                return new ResponseEntity<Error>(new Error("Username Already Exists"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<ResponseMessage>(new ResponseMessage("Successfully Updated"), HttpStatus.OK);
        }
        return new ResponseEntity<Error>(new Error("Phone Number Does Not Exist"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/get")
    public User getUserName(@RequestBody Map<String,Object> data){
        return userDao.findByPhoneNumber(data.get("phoneNumber").toString());
    }

    @PostMapping("/get-balance")
    public double getBalance(@RequestBody Map<String,Object> data){
        return userDao.findByPhoneNumber(data.get("phoneNumber").toString()).getBalance();
    }

    @PostMapping("/approve-withdrawl-request")
    public ResponseEntity<?> approveWithdrawl(@RequestBody Map<String,Object> data){
        int id = Integer.parseInt(data.get("reqId").toString());
        WithdrawlRequest wr = withdrawlRequestDao.findByReqId(id);
        if (wr == null){
            return new ResponseEntity<Error>(new Error("Something Went Wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        wr.setStatus("Completed");
        withdrawlRequestDao.save(wr);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Withdrawl Approved"), HttpStatus.OK);
    }

    @PostMapping("/cancel-withdrawl-request")
    public ResponseEntity<?> cancelWithdrawl(@RequestBody Map<String,Object> data){
        int id = Integer.parseInt(data.get("reqId").toString());
        WithdrawlRequest wr = withdrawlRequestDao.findByReqId(id);
        if (wr == null){
            return new ResponseEntity<Error>(new Error("Something Went Wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User u = userDao.findByPhoneNumber(wr.getWithdrawDetails().getRegPhoneNumber());
        if(u == null) return new ResponseEntity<Error>(new Error("Something Went Wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        u.setBalance(u.getBalance() + wr.getWithdrawlAmount());
        userDao.save(u);
        wr.setStatus("Cancelled");
        withdrawlRequestDao.save(wr);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Withdrawl Cancelled"), HttpStatus.OK);
    }

    @PostMapping("/save-withdrawl-details")
    public void saveWithdrwalDetails(@RequestBody WithdrawDetails withdrawDetails){
        withdrawDetailsDao.save(withdrawDetails);
    }

    @PostMapping("/get-withdrawl-details")
    public WithdrawDetails getWithdrawlDetails(@RequestBody Map<String,Object> data){
        return withdrawDetailsDao.findByRegPhoneNumber(data.get("regPhoneNumber").toString());
    }

    @PostMapping("/credit-amount")
    public ResponseEntity<?> creditAmount(@RequestBody Map<String,Object> data){
        User user = userDao.findByPhoneNumber(data.get("phoneNumber").toString());
        if(user == null) return new ResponseEntity<Error>(new Error("Couldn't Find The User!"), HttpStatus.INTERNAL_SERVER_ERROR);

        user.setBalance(user.getBalance() + Double.valueOf(data.get("creditAmount").toString()));
        userDao.save(user);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Credited Successfully!"), HttpStatus.OK);
    }

    @PostMapping("/debit-amount")
    public ResponseEntity<?> debitAmount(@RequestBody Map<String,Object> data){
        User user = userDao.findByPhoneNumber(data.get("phoneNumber").toString());
        if(user == null) return new ResponseEntity<Error>(new Error("Couldn't Find The User!"), HttpStatus.INTERNAL_SERVER_ERROR);

        if(user.getBalance() < Double.valueOf(data.get("debitAmount").toString())){
            return new ResponseEntity<Error>(new Error("Insufficient Balance!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setBalance(user.getBalance() - Double.valueOf(data.get("debitAmount").toString()));
        userDao.save(user);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Debited Successfully!"), HttpStatus.OK);
    }

    @GetMapping("/withdrawl-requests")
    public List<WithdrawlRequest> getWithdrawlRequests(){
        return withdrawlRequestDao.findByStatus("Pending");
    }

}
