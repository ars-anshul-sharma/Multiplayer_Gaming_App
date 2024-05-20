package com.app.game.Dao;

import com.app.game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,String> {
    public User findByPhoneNumber(String phoneNumber);
    public User findByUserName(String username);
}
