package model.dao;

import controller.ReservationController;

public class SeatDao {
    // [1] 싱글톤
    private SeatDao(){}
    private static SeatDao instance = new SeatDao();
    public static SeatDao getInstance() {
        return instance;
    }
}
