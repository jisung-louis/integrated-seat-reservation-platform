package view;

import constant.SeatPolicy;
import model.dto.SeatDto;
import util.CodeNumberConverter;

import java.util.ArrayList;

public class SeatChart {
    private SeatChart(){} // 인스턴스 생성 방지
    public static void showSeatingChartForSeatManagement(ArrayList<SeatDto> seats){
        System.out.println("""
                    ================================================
                                        좌석 배치도
                    ================================================
                    """);

        // ===== 좌석 배치도 출력 =====
        System.out.print("   ");
        for (int column = 0; column < SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++) { // A, B, C, ... (colCode 출력)
            char colCode = (char) (column + 65);
            System.out.print(colCode + "  ");
        }
        System.out.println();
        for (int row = 1; row <= SeatPolicy.MAX_SEAT_ROW_COUNT; row++) {
            System.out.print(row + "  "); //1, 2, 3, ... (rowNum 출력)
            for(int column = 1; column <= SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++){
                if (seatStatus(seats, column, row) > 0) { // 좌석 예약 가능
                    System.out.print("■  ");
                } else { // 좌석 미존재 (seatStatus(seats,column,row) == 0)
                    System.out.print("□  ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("□ = 빈 공간   ■ = 좌석");
        System.out.println();
        System.out.println("================================");
        System.out.println();
        // ===== 좌석 배치도 출력 end =====
    }

    /**
     *
     * @param seats 좌석 객체 목록
     * @param reservedRawSeatCode 예약된 좌석 코드(강조할 좌석 코드)
     */
    public static void showSeatingChartForUser(ArrayList<SeatDto> seats, String reservedRawSeatCode, String storeName){
        String[] parts = reservedRawSeatCode.split("-");
        int colNum = CodeNumberConverter.convertColCodeToNumber(parts[0]);
        int rowNum = Integer.parseInt(parts[1]);
        System.out.printf("""
                    ================================================
                                <%s> 좌석 배치도
                    ================================================
                    \n""", storeName);

        // ===== 좌석 배치도 출력 =====
        System.out.print("   ");
        for (int column = 0; column < SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++) { // A, B, C, ... (colCode 출력)
            char colCode = (char) (column + 65);
            System.out.print(colCode + "  ");
        }
        System.out.println();
        for (int row = 1; row <= SeatPolicy.MAX_SEAT_ROW_COUNT; row++) {
            System.out.print(row + "  "); //1, 2, 3, ... (rowNum 출력)
            for(int column = 1; column <= SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++){
                if(row == rowNum && column == colNum){
                    System.out.print("✅ ");
                }
                else {
                    int status = seatStatus(seats, column, row);
                    if (status == 2) { // 좌석 이미 예약됨
                        System.out.print("■  ");
                    } else if (status == 1) { // 좌석 예약 가능
                        System.out.print("□  ");
                    } else { // 좌석 미존재 (seatStatus(seats,column,row) == 0)
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("□ = 빈 좌석   ■ = 이미 예약된 자리   ✅ = 예약한 자리");
        System.out.println();
        System.out.println("================================");
        System.out.println();
        // ===== 좌석 배치도 출력 end =====
    }

    public static void showSeatingChartForReservationManagement(ArrayList<SeatDto> seats, String storeName){
        System.out.printf("""
                    ================================================
                                <%s> 좌석 배치도
                    ================================================
                    \n""", storeName);

        // ===== 좌석 배치도 출력 =====
        System.out.print("   ");
        for (int column = 0; column < SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++) { // A, B, C, ... (colCode 출력)
            char colCode = (char) (column + 65);
            System.out.print(colCode + "  ");
        }
        System.out.println();
        for (int row = 1; row <= SeatPolicy.MAX_SEAT_ROW_COUNT; row++) {
            System.out.print(row + "  "); //1, 2, 3, ... (rowNum 출력)
            for(int column = 1; column <= SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++){
                if (seatStatus(seats, column, row) == 2){ // 이미 예약된 좌석
                    System.out.print("■  ");
                }
                else if (seatStatus(seats, column, row) == 1) { // 예약 가능한 좌석
                    System.out.print("□  ");
                } else { // 좌석 미존재 (seatStatus(seats,column,row) == 0)
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("□ = 빈 좌석   ■ = 예약된 좌석");
        System.out.println();
        System.out.println("================================");
        System.out.println();
        // ===== 좌석 배치도 출력 end =====
    }

    /**
     *
     * @param seats 좌석 목록
     * @param colCode 가로 코드
     * @param rowNum 세로 숫자
     * @return 0 : 좌석 미존재, 1 : 좌석 예약 가능, 2 : 좌석 예약됨
     */
    private static int seatStatus(ArrayList<SeatDto> seats, int colCode, int rowNum){
        for (SeatDto seat : seats) {
            char code = (char) (colCode + 64);
            String column = Character.toString(code);
            String row = Integer.toString(rowNum);
            if(seat.getColCode().equals(column) && seat.getRowNum().equals(row)){ // 해당 좌석이 존재한다면
                if(seat.getStatus() == 0) { return 1; } // 예약 가능 상태일 때
                else if(seat.getStatus() == 1) { return 2; } // 이미 예약된 상태일 때
            }
        }
        return 0; // 해당 좌석이 없을 때
    }
}
