package model.dao;

import model.dto.SeatDto;

import java.util.ArrayList;

public interface SeatDao {
    /**
     *
     * @param store_no 매장 번호
     * @param rowcode 가로 번호
     * @param colnum 세로 번호
     * @return 좌석 추가 성공 여부(true or false)
     */
    boolean addSeats(int store_no, String rowcode, String colnum);
    int AVAILABLE = 0;
    int RESERVED = 1;
    /**
     *
     * @param store_no 매장 번호
     * @param rowcode 가로 번호
     * @param colnum 세로 번호
     * @param status 변경할 예약 상태 (AVAILABLE / RESERVED)
     * @return 좌석 상태 성공 여부(true or false)
     */
    boolean updateSeatStatus(int store_no, String rowcode, String colnum, int status);

    /**
     *
     * @param store_no 매장 번호
     * @param rowcode 가로 번호
     * @param colnum 세로 번호
     * @return 좌석 삭제 성공 여부(true or false)
     */
    boolean deleteSeats(int store_no, String rowcode, String colnum);

    /**
     *
     * @param store_no 매장 번호
     * @return 해당 매장의 좌석 정보 반환
     */
    ArrayList<SeatDto> getSeats(int store_no);
}
