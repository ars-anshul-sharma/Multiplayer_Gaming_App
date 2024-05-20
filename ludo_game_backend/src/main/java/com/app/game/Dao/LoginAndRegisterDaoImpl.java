package com.app.game.Dao;

import com.app.game.entity.Admin;
import com.app.game.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class LoginAndRegisterDaoImpl implements LoginAndRegisterDao {

    @Autowired
    EntityManager em;

    @Override
    public void registerNewPhoneNumber(String phoneNumber, String userType){
        if(userType.equals("USER")) {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            em.persist(user);
        }else if (userType.equals("ADMIN")){
            Admin admin = new Admin();
            admin.setPhoneNumber(phoneNumber);
            em.persist(admin);
        }
    }

    @Override
    public boolean checkIfAlreadyRegistered(String phoneNumber, String userType){
        User user = null;
        Admin admin = null;
        if(userType.equals("USER")) {
            user = em.find(User.class, phoneNumber);
            if(user == null) return false;
        } else if (userType.equals("ADMIN")) {
            admin = em.find(Admin.class, phoneNumber);
            if(admin == null) return false;
        }
        return true;
    }

}
