package model.dao;

import model.dto.StoreDto;

import java.util.ArrayList;

public interface StoreDao {
    boolean addStore(int owner_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status);
    boolean updateStore(int store_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status);
    boolean deleteStore(int store_no);
    StoreDto getStore(int store_no);
    ArrayList<StoreDto> getStores();
    ArrayList<StoreDto> getMyStores(int owner_no);
}
