package com.app.game.controller;

import com.app.game.Dao.*;
import com.app.game.dto.ResponseMessage;
import com.app.game.entity.PaymentOrders;
import com.app.game.entity.User;
import com.app.game.entity.WithdrawDetails;
import com.app.game.entity.WithdrawlRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    PaymentOrderDao paymentOrderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    WithdrawDetailsDao withdrawDetailsDao;

    @Autowired
    WithdrawlRequestDao withdrawlRequestDao;

    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String,Object> data) throws RazorpayException {
        double amount = Double.parseDouble(data.get("amount").toString());
        RazorpayClient client = new RazorpayClient("rzp_test_VEca5dqOUnQdVj", "59DYylDVGwKKiiZqMvTnTF2i");

        JSONObject json_ob = new JSONObject();
        json_ob.put("amount", amount*100);
        json_ob.put("currency", "INR");
        json_ob.put("receipt", "txn_235425");

        Order order = client.orders.create(json_ob);

        PaymentOrders paymentOrders = new PaymentOrders();
        paymentOrders.setAmount(order.get("amount")+"");
        paymentOrders.setOrderId(order.get("id"));
        paymentOrders.setStatus("created");
        paymentOrders.setReceipt(order.get("receipt"));
        paymentOrders.setUser(userDao.findByPhoneNumber(data.get("phoneNumber").toString()));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
        String formattedDateTime = now.format(formatter);
        paymentOrders.setTime(formattedDateTime);

        paymentOrderDao.save(paymentOrders);

        return order.toString();
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String,Object> data){
        PaymentOrders myorder = paymentOrderDao.findByOrderId(data.get("order_id").toString());
        myorder.setPaymentId(data.get("payment_id").toString());
        myorder.setStatus(data.get("status").toString());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
        String formattedDateTime = now.format(formatter);
        myorder.setTime(formattedDateTime);
        paymentOrderDao.save(myorder);

        User u = userDao.findByPhoneNumber(myorder.getUser().getPhoneNumber());
        u.setBalance(u.getBalance()+(Double.parseDouble(myorder.getAmount())/100));
        userDao.save(u);
        return ResponseEntity.ok(Map.of("msg","updated"));
    }

    @PostMapping("/transactions")
    public List<PaymentOrders> getTransactions(@RequestBody Map<String,Object> data){
        Comparator<PaymentOrders> idComparator = Comparator.comparingLong(PaymentOrders::getId).reversed();
        User u = userDao.findByPhoneNumber(data.get("phoneNumber").toString());
        List<PaymentOrders> pList = paymentOrderDao.findByUser(u);
        Collections.sort(pList,idComparator);
        return pList;
    }

    @PostMapping("/upi-withdrawl")
    public ResponseEntity<?> upiWithdrawl(@RequestBody Map<String,Object> data){
        double withdarwAmount = Double.valueOf(data.get("withdrawAmount").toString());
        if(withdarwAmount < 100){
            return new ResponseEntity<Error>(new Error("Withdrawl Amount Can't Be Less Than 100"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        WithdrawDetails withdrawDetails = withdrawDetailsDao.findByRegPhoneNumber(data.get("phoneNumber").toString());
        withdrawDetails.setUpiId(data.get("upiAddress").toString());
        withdrawDetails.setName(data.get("accountHolderName").toString());
        withdrawDetailsDao.save(withdrawDetails);
        User user = userDao.findByPhoneNumber(data.get("phoneNumber").toString());
        if(user.getBalance() >= withdarwAmount){
            user.setBalance(user.getBalance()-withdarwAmount);
            WithdrawlRequest withdrawlRequest = new WithdrawlRequest();
            withdrawlRequest.setWithdrawlAmount(withdarwAmount);
            withdrawlRequest.setStatus("Pending");
            withdrawlRequest.setWithdrawDetails(withdrawDetails);
            withdrawlRequestDao.save(withdrawlRequest);
        }else{
            return new ResponseEntity<Error>(new Error("Insuffient Balance"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Withdraw Accepted"), HttpStatus.OK);
    }

    @PostMapping("/bank-withdrawl")
    public ResponseEntity<?> bankWithdrawl(@RequestBody Map<String,Object> data){
        double withdarwAmount = Double.valueOf(data.get("withdrawAmount").toString());
        if(withdarwAmount < 100){
            return new ResponseEntity<Error>(new Error("Withdrawl Amount Can't Be Less Than 100"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        WithdrawDetails withdrawDetails = withdrawDetailsDao.findByRegPhoneNumber(data.get("phoneNumber").toString());
        withdrawDetails.setName(data.get("accountHolderName").toString());
        withdrawDetails.setBankName(data.get("bankName").toString());
        withdrawDetails.setAccountNumber(data.get("accountNumber").toString());
        withdrawDetails.setIfscCode(data.get("ifscCode").toString());
        withdrawDetailsDao.save(withdrawDetails);
        User user = userDao.findByPhoneNumber(data.get("phoneNumber").toString());
        if(user.getBalance() >= withdarwAmount){
            user.setBalance(user.getBalance()-withdarwAmount);
            WithdrawlRequest withdrawlRequest = new WithdrawlRequest();
            withdrawlRequest.setWithdrawlAmount(withdarwAmount);
            withdrawlRequest.setStatus("Pending");
            withdrawlRequest.setWithdrawDetails(withdrawDetails);
            withdrawlRequestDao.save(withdrawlRequest);
        }else{
            return new ResponseEntity<Error>(new Error("Insuffient Balance"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Withdraw Accepted"), HttpStatus.OK);
    }
}
