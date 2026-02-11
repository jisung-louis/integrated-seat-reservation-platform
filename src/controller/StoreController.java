package controller;

import model.dao.JdbcStoreDao;
import model.dto.StoreDto;

import java.util.ArrayList;

public class StoreController {
    // [1] 싱글톤
    private StoreController() {}
    private static StoreController instance = new StoreController();
    public static StoreController getInstance() {
        return instance;
    }
    private JdbcStoreDao sd = JdbcStoreDao.getInstance();
    public boolean addStore(int owner_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status) {
        boolean result = sd.addStore(owner_no, name, category, address, contact, email, bh_weekdays, bh_saturday, bh_sunday, status);
        return result;
    }
    public boolean updateStore(int store_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status) {
        boolean result = sd.updateStore(store_no, name, category, address, contact, email, bh_weekdays, bh_saturday, bh_sunday, status);
        return result;
    }
    public boolean deleteStore(int store_no){
        boolean result=sd.deleteStore(store_no);
        return result;
    }
    public StoreDto getStore(int store_no) {
        StoreDto result=sd.getStore(store_no);
        return result;
    }
    public ArrayList<StoreDto> getStores() {
        ArrayList<StoreDto> result=sd.getStores();
        return result;
    }
    public ArrayList<StoreDto> getMyStores(int owner_no) {
        ArrayList<StoreDto> result=sd.getMyStores(owner_no);
        return result;
    }
}