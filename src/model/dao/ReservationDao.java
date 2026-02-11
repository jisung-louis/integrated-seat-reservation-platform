package model.dao;

import model.dto.ReservationDto;

import java.util.ArrayList;

public interface ReservationDao {
    /**
     *
     * @param user_no 유저 번호
     * @param seat_code 좌석 코드
     * @return 성공 : true, 실패 : false
     * @apiNote 예약하는 유저의 번호와 예약할 좌석 코드를 매개변수로 받아 성공/실패 여부를 반환
     */
    boolean doReservation(int user_no, String seat_code);
    /**
     *
     * @param user_no 유저 번호
     * @param current_seat_code 현재의 좌석 코드
     * @param updating_seat_code 변경할 좌석 코드
     * @return 성공 : true, 실패 : false
     * @사용예시: 1번 유저가 1-A-3에서 1-B-4로 바꾼다면, 매개변수는 (1, "1-A-3", "1-B-4")로 입력하면 됩니다.
     */
    boolean updateReservation(int user_no, String current_seat_code, String updating_seat_code);
    boolean deleteReservation(int user_no, String seat_code);
    ArrayList<ReservationDto> getReservationsByStoreNo(int store_no);
    ArrayList<ReservationDto> getReservationsByUserNo(int user_no);
}
