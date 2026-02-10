package model.dao;

import controller.ReservationController;

public class ReservationDao {
    // [1] 싱글톤
    private ReservationDao(){}
    private static ReservationDao instance = new ReservationDao();
    public static ReservationDao getInstance() {
        return instance;
    }
}
