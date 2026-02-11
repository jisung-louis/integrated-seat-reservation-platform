package controller;

import model.dao.JdbcReservationDao;
import model.dao.ReservationDao;
import model.dto.ReservationDto;

import java.util.ArrayList;

public class ReservationController {
    // [1] 싱글톤
    private ReservationController(){}
    private static ReservationController instance = new ReservationController();
    public static ReservationController getInstance() {
        return instance;
    }

    private ReservationDao reservationDao = JdbcReservationDao.getInstance();

    // [1] 특정 매장 예약 내역 조회
    public void getStoreReservations(int store_no){
        ArrayList<ReservationDto> result = reservationDao.getReservationsByStoreNo(store_no);

        System.out.println("\n===== 매장(No." + store_no + ") 예약 내역 =====");
        if(result.isEmpty()){
            System.out.println("현재 예약된 내역이 없습니다");
        } else {
            for(ReservationDto dto : result){
                System.out.printf("예약번호: %d | 유저번호: %d | 좌석: %s | 일시: %s\n", dto.getNo(), dto.getUser_no(), dto.getSeat_code(), dto.getReservedAt());
            }
        }
    }

    // [2] 특정 매장의 특정 좌석 예약하기


    // [3]
    // [4]

}
