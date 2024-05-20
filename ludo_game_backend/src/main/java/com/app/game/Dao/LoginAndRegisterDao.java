package com.app.game.Dao;

public interface LoginAndRegisterDao {
    public void registerNewPhoneNumber(String phoneNumber, String userType);
    public boolean checkIfAlreadyRegistered(String phoneNumber, String userType);
}
