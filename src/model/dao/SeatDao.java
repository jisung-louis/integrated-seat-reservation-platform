package model.dao;

import model.dto.SeatDto;

import java.util.ArrayList;

public interface SeatDao {
    /**
     *
     * @param seatCode 좌석 코드 (매장번호-colCode-rowNum)
     * @return 좌석 추가 성공 여부(true or false)
     */
    boolean addSeat(String seatCode);
    int AVAILABLE = 0;
    int RESERVED = 1;
    /**
     *
     * @param seatCode 좌석 코드 (매장번호-colCode-rowNum)
     * @param status 변경할 예약 상태 (AVAILABLE / RESERVED)
     * @return 좌석 상태 성공 여부(true or false)
     */
    boolean updateSeatStatus(String seatCode, int status);

    /**
     *
     * @param seatCode 좌석 코드 (매장번호-colCode-rowNum)
     * @return 좌석 삭제 성공 여부(true or false)
     */
    boolean deleteSeat(String seatCode);

    /**
     *
     * @param store_no 매장 번호
     * @return 해당 매장의 좌석 정보 반환
     */
    ArrayList<SeatDto> getSeats(int store_no);

    boolean isSeatExist(String seatCode);
}
