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

    /**
     *
     * @param user_no 유저 번호
     * @param store_no 매장 번호
     * @param rawSeatCode 좌석 코드
     * @return [1] : 예약 성공, [0] : DB 오류로 예약 실패, [-1]: 이미 예약된 자리라서 실패, [-2]: 해당 좌석이 존재하지 않아 실패
     */
    public int doReservation(int user_no, int store_no ,String rawSeatCode){
        ArrayList<SeatDto> seats = seatDao.getSeats(store_no);
        SeatDto target = null;
        String seatCode = store_no + "-" + rawSeatCode; // "B-4" 꼴을 "1-B-4" 꼴로 만듦
        for(SeatDto seat : seats){
            if(seat.getCode().equals(seatCode)){
                target = seat;
                break;
            }
        }
        if(target == null){
            return -2;
        } // 해당 좌석은 매장에 존재하지 않음
        if (target.getStatus() != 0){
            return -1;
        } // 해당 좌석은 이미 예약된 자리임

        boolean result1 = reservationDao.doReservation(user_no,seatCode);
        boolean result2 = seatDao.updateSeatStatus(seatCode, 1);

        // TODO : 둘 중 하나라도 실패시 롤백하는 로직 있어야 함. (예 : result1 성공했지만 result2 실패 시 해당 예약 deleteReservation DAO 호출)

        return result1 && result2 ? 1 : 0; // 예약 성공 & 좌석 상태 업데이트 성공 시 1, 실패시 0 반환
    }

    // [3]
    // [4]

}
