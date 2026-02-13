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

    // [1] 특정 매장 예약 내역 조회
    // 관리자 기능
    public void getStoreReservations(int store_no) {
        ArrayList<ReservationDto> result = reservationDao.getReservationsByStoreNo(store_no);

        System.out.println("\n========================================");
        System.out.printf("           예약 목록 (총 %d건)\n", result.size());
        System.out.println("========================================\n");

        if (result.isEmpty()) {
            System.out.println("   현재 예약된 내역이 없습니다.");
        } else {
            int index = 1;
            for (ReservationDto dto : result) {
                System.out.printf("[%d]\n", index++);
                System.out.println("예약자: " + dto.getUserName() + " (" + dto.getUserId() + ")");
                System.out.println("좌석: " + dto.getSeat_code());

                String date = dto.getReservedAt();
                if (date != null && date.length() > 19) {
                    date = date.substring(0, 19);
                }
                
                System.out.println("상태: ✅ 예약확정");
                System.out.println("예약일시: " + date);
                System.out.println("----------------------------------------");
            }
        }
        System.out.println("0. 뒤로 가기");
    }

    // [2] 특정 매장의 특정 좌석 예약하기
    public void doReservation(int user_no, int store_no ,String seat_code){
        ArrayList<SeatDto> seats = seatDao.getSeats(store_no);
        SeatDto target = null;
        for(SeatDto seat : seats){
            if(seat.getCode().equals(seat_code)){
                target = seat;
                break;
            }
        }
        if(target == null || target.getStatus() != 0){
            System.out.println("예약 불가능한 좌석입니다.");
            return;
        }


    }

    // [3]
    // [4]

}
