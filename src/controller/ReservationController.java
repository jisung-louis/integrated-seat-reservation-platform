package controller;

import model.dao.JdbcReservationDao;
import model.dao.JdbcSeatDao;
import model.dao.ReservationDao;
import model.dao.SeatDao;
import model.dto.ReservationDto;
import model.dto.SeatDto;

import java.util.ArrayList;

public class ReservationController {
    // [1] 싱글톤
    private ReservationController(){}
    private static ReservationController instance = new ReservationController();
    public static ReservationController getInstance() {
        return instance;
    }

    private ReservationDao reservationDao = JdbcReservationDao.getInstance();
    private SeatDao seatDao = JdbcSeatDao.getInstance();

    // [1-1] 특정 매장 예약 내역 조회 (관리자용)
    public ArrayList<ReservationDto> getStoreReservations(int store_no) {
        return reservationDao.getReservationsByStoreNo(store_no);
    }

    // [1-2] 특정 유저의 전체 예약 내역 조회 (사용자용)
    public ArrayList<ReservationDto> getStoreReservationsByUserNo(int user_no) {
        return reservationDao.getReservationsByUserNo(user_no);
    }

    // [2] 특정 매장의 특정 좌석 예약하기
    /**
     *
     * @param user_no 유저 번호
     * @param store_no 매장 번호
     * @param rawSeatCode 좌석 코드
     * @return [1] : 예약 성공, [0] : DB 오류로 예약 실패, [-1]: 이미 예약된 자리라서 실패, [-2]: 해당 좌석이 존재하지 않아 실패
     */
    public int doReservation(int user_no, int store_no, String rawSeatCode) {
        ArrayList<SeatDto> seats = seatDao.getSeats(store_no);
        SeatDto target = null;
        String seatCode = store_no + "-" + rawSeatCode; // "B-4" 꼴을 "1-B-4" 꼴로 만듦
        for (SeatDto seat : seats) {
            if (seat.getCode().equals(seatCode)) {
                target = seat;
                break;
            }
        }
        if (target == null) {
            return -2; // 해당 좌석은 매장에 존재하지 않음
        }
        if (target.getStatus() != 0) {
            return -1; // 해당 좌석은 이미 예약된 자리임
        }

        boolean result1 = reservationDao.doReservation(user_no, seatCode);
        boolean result2 = seatDao.updateSeatStatus(seatCode, 1);

        // TODO : 둘 중 하나라도 실패시 롤백하는 로직 있어야 함. (예 : result1 성공했지만 result2 실패 시 해당 예약 deleteReservation DAO 호출)

        return result1 && result2 ? 1 : 0; // 예약 성공 & 좌석 상태 업데이트 성공 시 1, 실패시 0 반환
    }

    // [3] 예약 취소
    public boolean deleteReservation(int user_no, int store_no, String seatCode) {
        if (reservationDao.deleteReservation(user_no, seatCode)) {
            return seatDao.updateSeatStatus(seatCode, 0);
        }
        // TODO : 둘 중 하나라도 실패시 롤백하는 로직 있어야 함. (예 : result1 성공했지만 result2 실패 시 해당 예약 deleteReservation DAO 호출)
        return false;
    }

    // [4] 예약 변경
    public int updateReservation(int user_no, int store_no, String oldRawCode, String newRawCode) {
        ArrayList<SeatDto> seats = seatDao.getSeats(store_no);
        SeatDto target = null;

        String oldCode = store_no + "-" + oldRawCode;
        String newCode = store_no + "-" + newRawCode;

        for (SeatDto s : seats) {
            String fullSeatCode = s.getCode(); // "1-A-3"
            String[] parts = fullSeatCode.split("-", 2);
            String rawSeatCode = parts[1]; // "A-3"

            if (rawSeatCode.equals(newRawCode)) {
                target = s;
                break;
            }
        }
        System.out.println(target);
        if (target == null || target.getStatus() != 0) {
            return 1; // 대상 좌석 불가능
        }
        if (reservationDao.updateReservation(user_no, oldCode, newCode)) {
            seatDao.updateSeatStatus(oldCode, 0); 
            seatDao.updateSeatStatus(newCode, 1);
            return 0; // 성공
        }
        return 2; // DB 오류
    }
    public ReservationDto getReservationByNo(int reservationNo){
        return reservationDao.getReservationByNo(reservationNo);
    }
}

