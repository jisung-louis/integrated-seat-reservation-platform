package model.dao;

import controller.ReservationController;

public class StoreDao {
    // [1] 싱글톤
    private StoreDao(){}
    private static StoreDao instance = new StoreDao();
    public static StoreDao getInstance() {
        return instance;
    }
}
