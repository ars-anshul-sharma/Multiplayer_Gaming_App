package com.app.game.Dao;

import com.app.game.entity.PaymentOrders;
import com.app.game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOrderDao extends JpaRepository<PaymentOrders,Long> {
    public PaymentOrders findByOrderId(String order_id);
    public List<PaymentOrders> findByUser(User user);
}
