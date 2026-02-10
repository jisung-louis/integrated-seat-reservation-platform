package model.dao;

import controller.ReservationController;

public class UserDao {
    // [1] 싱글톤
    private UserDao(){}
    private static UserDao instance = new UserDao();
    public static UserDao getInstance() {
        return instance;
    }
}
