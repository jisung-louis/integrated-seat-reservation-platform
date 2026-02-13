package model.dao;

import model.dto.SeatDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcSeatDao implements SeatDao {
    // [0] 연동 인터페이스 선언
    Connection conn;
    // [1] 싱글톤
    private JdbcSeatDao(){ conn = DBConnection.connect(); }
    private static JdbcSeatDao instance = new JdbcSeatDao();
    public static JdbcSeatDao getInstance() {
        return instance;
    }

    @Override
    public boolean addSeat(String seatCode) {
        // seatCode 분해
        String[] parts = seatCode.split("-");
        int store_no = Integer.parseInt(parts[0]);
        String colCode = parts[1];
        String rowNum = parts[2];

        try {
            String sql = "insert into seat values(?, ?, ?, ?, 0)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, seatCode);
            ps.setInt(2, store_no);
            ps.setString(3, colCode);
            ps.setString(4, rowNum);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public boolean updateSeatStatus(String seatCode, int status) {
        // TODO : updateSeatStatus 구현
        return false;
    }

    @Override
    public boolean deleteSeat(String seatCode) {
        try {
            String sql = "delete from seat where code = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, seatCode);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public ArrayList<SeatDto> getSeats(int store_no) {
        ArrayList<SeatDto> seats = new ArrayList<>();
        try {
            String sql = "select * from seat where store_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String code = rs.getString("code");
                String colCode = rs.getString("colcode");
                String rowNum = rs.getString("rownum");
                int status = rs.getInt("status");
                SeatDto seat = new SeatDto(code, store_no, colCode, rowNum, status);
                seats.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return seats;
    }

    @Override
    public int deleteSeatByStoreNo(int store_no) {
        try {
            String sql = "delete from seat where store_no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return 0;
    }

    @Override
    public boolean isSeatExist(String seatCode) {
        try {
            String sql = "select * from seat where code = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, seatCode);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false; // 에러나면 일단 false (해당 좌석이 DB에 없음) 으로 취급됨
    }
}
