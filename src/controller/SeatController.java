package controller;

import constant.SeatPolicy;
import model.dao.JdbcSeatDao;
import model.dao.SeatDao;
import model.dto.SeatDto;
import util.CodeNumberConverter;

import java.util.ArrayList;

public class SeatController {
    // [1] 싱글톤
    private SeatController(){}
    private static SeatController instance = new SeatController();
    public static SeatController getInstance() {
        return instance;
    }
    SeatDao sd = JdbcSeatDao.getInstance();

    public int toggleSeatStatus(int store_no, String rawSeatCode){
        // [1] rawSeatCode 유효성 체크
        // [1-1] "B-4" 꼴로 되어 있는지
        String[] parts = rawSeatCode.split("-");
        if(parts.length != 2) { return 3; } // "-"로 나눈 문자열이 2개가 아닐 때
        String colCode = parts[0];
        String rowNum = parts[1];
        if (!colCode.matches("^[A-Z]+$")) { return 3; } // colCode가 영문자(대문자)로 이루어져있지 않으면
        if (!rowNum.matches("^[0-9]+$")) { return 3; } // rowNum이 숫자로 이루어져있지 않으면
        // [1-2] rowCode와 colNum이 최대값보다 같거나 작은지
        int colNumber = CodeNumberConverter.convertColCodeToNumber(colCode);
        int rowNumber = Integer.parseInt(rowNum);
        if (colNumber > SeatPolicy.MAX_SEAT_COLUMN_COUNT) { return 3; }
        if (rowNumber > SeatPolicy.MAX_SEAT_ROW_COUNT) { return 3; }

        // [2] rawSeatCode(예:"B-4")를 "매장번호-colCode-rowNum"(예 : "1-B-4") 꼴로 정규화
        String seatCode = Integer.toString(store_no) + "-" + rawSeatCode;

        // [3] 해당 자리가 추가되어 있으면 제거, 없으면 추가 (토글)
        boolean isSeatExist = sd.isSeatExist(seatCode);
        if(isSeatExist){
            boolean result = sd.deleteSeat(seatCode); // 제거
            return result ? 2 : 0;
        }
        else {
            boolean result = sd.addSeat(seatCode); // 추가
            return result ? 1 : 0;
        }
    }
    public ArrayList<SeatDto> getSeats(int store_no){
        return sd.getSeats(store_no);
    }

    public int activateAllSeat(int store_no){
        int successCount = 0;
        for (int row = 1; row <= SeatPolicy.MAX_SEAT_ROW_COUNT; row++) {
            for (int column = 1; column <= SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++) {
                String rowNum = Integer.toString(row);
                String colCode = CodeNumberConverter.convertNumberToColCode(column);
                String seatCode = Integer.toString(store_no) + "-" + colCode + "-" + rowNum;
                boolean result = sd.addSeat(seatCode);
                if(result) { successCount++; }
            } // 만약 잘 되다가 중간에 다오 실패(db 트랜잭션 실패)시 어쩌지?
        }
        return successCount;
    }

    public int deactivateAllSeat(int store_no){
        return sd.deleteSeatByStoreNo(store_no);
    }
}
