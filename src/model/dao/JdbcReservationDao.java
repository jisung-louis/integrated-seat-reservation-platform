package model.dao;

import model.dto.ReservationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcReservationDao implements ReservationDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    // [1] 싱글톤
    private JdbcReservationDao() {DBConnection.connect();}
    private static JdbcReservationDao instance = new JdbcReservationDao();

    public static JdbcReservationDao getInstance() {
        return instance;
    }

    @Override
    public boolean doReservation(int user_no, String seat_code) {
        return false;
    }

    @Override
    public boolean updateReservation(int user_no, String current_seat_code, String updating_seat_code) {
        return false;
    }

    @Override
    public boolean deleteReservation(int user_no, String seat_code) {
        return false;
    }

    // 특정 매장의 예약 내역 목록 조회
    @Override
    public ArrayList<ReservationDto> getReservationsByStoreNo(int store_no) {
        ArrayList<ReservationDto> reservations = new ArrayList<>();
        // 예약
        String sql = "select r. * from reservation r join seat s on r.seat_code = s.code where s.store_no = ?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            rs = ps.executeQuery();
            while (rs.next()){
                reservations.add(new ReservationDto(
                        rs.getInt("no"),
                        rs.getInt("user_no"),
                        rs.getString("seat_code"),
                        rs.getString("reservedAt")
                ));
            }
        }catch (SQLException e){
            System.out.println(" sql 문법 오류");
        }
        return reservations;
    }

    @Override
    public ArrayList<ReservationDto> getReservationsByUserNo(int user_no) {
        return null;
    }
}
