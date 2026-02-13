package model.dao;

import model.dto.SeatDto;

import java.util.ArrayList;

public class JdbcSeatDao implements SeatDao {
    // [1] 싱글톤
    private JdbcSeatDao(){}
    private static JdbcSeatDao instance = new JdbcSeatDao();
    public static JdbcSeatDao getInstance() {
        return instance;
    }

    @Override
    public boolean addSeats(int store_no, String rowcode, String colnum) {
        return false;
    }

    @Override
    public boolean updateSeatStatus(int store_no, String rowcode, String colnum, int status) {
        return false;
    }

    @Override
    public boolean deleteSeats(int store_no, String rowcode, String colnum) {
        return false;
    }

    @Override
    public ArrayList<SeatDto> getSeats(int store_no) {
        return null;
    }
}
