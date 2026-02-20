package model.dao;

import model.dto.ReservationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcReservationDao extends DBConnection implements ReservationDao{
    private Connection conn;

    private PreparedStatement ps;
    private ResultSet rs;

    // [1] 싱글톤
    private JdbcReservationDao() {
        conn = connect();
    }

    private static JdbcReservationDao instance = new JdbcReservationDao();

    public static JdbcReservationDao getInstance() {
        return instance;
    }

    // [2] 특정 매장의 특정 좌석 예약하기
    @Override
    public boolean doReservation(int user_no, String seat_code) {
        String sql = "insert into reservation(user_no,seat_code) values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_no);
            ps.setString(2, seat_code);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // [4] 예약 변경
    @Override
    public boolean updateReservation(int user_no, String current_seat_code, String updating_seat_code) {
        String sql = "update reservation set seat_code = ? where user_no = ? and seat_code = ?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1, updating_seat_code);
            ps.setInt(2, user_no);
            ps.setString(3, current_seat_code);
            return ps.executeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // [3] 예약 취소
    @Override
    public boolean deleteReservation(int user_no, String seat_code) {
        String sql = "delete from reservation where user_no=? and seat_code=?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_no);
            ps.setString(2, seat_code);
            return ps.executeUpdate() == 1;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // [1] 특정 매장의 예약 내역 목록 조회
    // 관리자 기준
    @Override
    public ArrayList<ReservationDto> getReservationsByStoreNo(int store_no) {
        ArrayList<ReservationDto> reservations = new ArrayList<>();
        String sql =
                "select r.*, u.name as userName, u.id as userId from reservation r join " +
                "user u on r.user_no = u.no join seat s on r.seat_code = s.code where s.store_no = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, store_no);
            rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(new ReservationDto(
                        rs.getInt("no"),
                        rs.getInt("user_no"),
                        rs.getString("seat_code"),
                        rs.getString("reservedAt"),
                        rs.getString("userName"), // 추가된 필드
                        rs.getString("userId")    // 추가된 필드
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // 사용자 기준
    // [예약 + 유저 + 좌석 + 매장] 4단 조인 구현
    @Override
    public ArrayList<ReservationDto> getReservationsByUserNo(int user_no) {
        ArrayList<ReservationDto> list = new ArrayList<>();
        String sql = "select r.*, u.name as userName, u.id as userId, st.name as storeName " +
                     "from reservation r " +
                     "join user u on r.user_no = u.no " +
                     "join seat s on r.seat_code = s.code " +
                     "join store st on s.store_no = st.no " +
                     "where r.user_no = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_no);
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new ReservationDto(
                    rs.getInt("no"),
                    rs.getInt("user_no"),
                    rs.getString("seat_code"),
                    rs.getString("reservedAt"),
                    rs.getString("userName"),
                    rs.getString("userId"),
                    rs.getString("storeName")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public ReservationDto getReservationByNo(int reservationNo) {
        String sql = "select * from reservation where no = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int no = rs.getInt("no");
                int user_no = rs.getInt("user_no");
                String seatCode = rs.getString("seat_code");
                String reservedAt = rs.getDate("reservedAt").toString();
                return new ReservationDto(no, user_no, seatCode, reservedAt);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
