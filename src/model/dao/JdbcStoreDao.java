package model.dao;

import model.dto.StoreDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcStoreDao extends DBConnection implements StoreDao{
    // [0] 연동 인터페이스 선언
    private Connection conn;
    // [1] 싱글톤
    private JdbcStoreDao(){ conn = connect(); }
    private static JdbcStoreDao instance = new JdbcStoreDao();
    public static JdbcStoreDao getInstance() {
        return instance;
    }

    @Override
    public boolean addStore(int owner_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status) {
        try {
            String sql = "insert into store values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setInt(3, owner_no);
            ps.setString(4, address);
            ps.setString(5, contact);
            ps.setString(6, email);
            ps.setString(7, bh_weekdays);
            ps.setString(8, bh_saturday);
            ps.setString(9, bh_sunday);
            ps.setInt(10, status);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public boolean updateStore(int store_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status) {
        try {
            String sql = "update store set name = ?, category = ?, address = ?, contact = ?, email = ?, bh_weekdays = ?, bh_saturday = ?, bh_sunday = ?, status = ? where no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setString(5, email);
            ps.setString(6, bh_weekdays);
            ps.setString(7, bh_saturday);
            ps.setString(8, bh_sunday);
            ps.setInt(9, status);
            ps.setInt(10, store_no);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public boolean deleteStore(int store_no) {
        try {
            String sql = "delete from store where no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public StoreDto getStore(int store_no) {
        try {
            String sql = "select * from store where no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int no = rs.getInt("no");
                int owner_no = rs.getInt("owner_no");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("name");
                String bh_weekdays = rs.getString("bh_weekdays");
                String bh_saturday = rs.getString("bh_saturday");
                String bh_sunday = rs.getString("bh_sunday");
                int status = rs.getInt("status");
                return new StoreDto(no,owner_no,name,category,address,contact,email,bh_weekdays,bh_saturday,bh_sunday,status);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return null;
    }

    @Override
    public ArrayList<StoreDto> getStores() {
        ArrayList<StoreDto> stores = new ArrayList<>();
        try {
            String sql = "select * from store";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int no = rs.getInt("no");
                int owner_no = rs.getInt("owner_no");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("email");
                String bh_weekdays = rs.getString("bh_weekdays");
                String bh_saturday = rs.getString("bh_saturday");
                String bh_sunday = rs.getString("bh_sunday");
                int status = rs.getInt("status");
                StoreDto store = new StoreDto(no, owner_no, name, category, address, contact, email, bh_weekdays, bh_saturday, bh_sunday, status);
                stores.add(store);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return stores;
    }

    @Override
    public ArrayList<StoreDto> getMyStores(int owner_no) {
        ArrayList<StoreDto> myStores = new ArrayList<>();
        try {
            String sql = "select * from store where owner_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, owner_no);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int no = rs.getInt("no");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String email = rs.getString("email");
                String bh_weekdays = rs.getString("bh_weekdays");
                String bh_saturday = rs.getString("bh_saturday");
                String bh_sunday = rs.getString("bh_sunday");
                int status = rs.getInt("status");
                StoreDto store = new StoreDto(no, owner_no, name, category, address, contact, email, bh_weekdays, bh_saturday, bh_sunday, status);
                myStores.add(store);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return myStores;
    }
}
