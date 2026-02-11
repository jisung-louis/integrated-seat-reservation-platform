package view;

import constant.SeatPolicy;
import controller.ReservationController;
import controller.StoreController;
import model.dto.SeatDto;
import model.dto.StoreDto;

import java.util.ArrayList;

public class AdminView {
    // [1] 싱글톤
    private AdminView(){}
    private static AdminView instance = new AdminView();
    public static AdminView getInstance() {
        return instance;
    }

    // [2] 컨트롤러 인스턴스 가져옴
    StoreController sc = StoreController.getInstance();

    /**
     *
     * @param store_no 매장 번호
     * @apiNote "좌석 배치 관리" 화면
     * @implNote 관리자 메인화면 -> 매장 선택 -> 2. 좌석배치관리 클릭 시 호출되는 화면
     */
    public void seatManagementView(int store_no){
        // STATUS
//        StoreDto store = sc.getStoreDetail(store_no);
//        String storeName = store.getName();

//        ArrayList<SeatDto> seats = sc.getSeats(store_no);

        //for test
        String storeName = "테스트 매장";
        ArrayList<SeatDto> seats = new ArrayList<>();
        seats.add(new SeatDto("1-A-3",store_no,"A","3",0));
        seats.add(new SeatDto("1-A-4",store_no,"A","4",0));
        seats.add(new SeatDto("1-B-5",store_no,"B","5",0));
        //for test end

        int totalSeats = seats.size();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                    좌석 배치 관리                   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("매장 : " + storeName);
        System.out.println("총 좌석 : " + totalSeats);
        for (int row = 1; row <= SeatPolicy.MAX_SEAT_ROW_COUNT; row++) {
            System.out.println();
            for(int column = 1; column <= SeatPolicy.MAX_SEAT_COLUMN_COUNT; column++){
                if(isSeatExist(seats, row, column)){
                    System.out.print("◼  ");
                }
                else {
                    System.out.print("☐  ");
                }
            }
        }
    }
    private boolean isSeatExist(ArrayList<SeatDto> seats, int rowcode, int colnum){
        for (SeatDto seat : seats) {
            char code = (char) (rowcode + 64);
            String row = Character.toString(code);
            String column = Integer.toString(colnum);
            if(seat.getRowCode().equals(row) && seat.getColNum().equals(column)){
                return true;
            }
        }
        return false;
    }
}
