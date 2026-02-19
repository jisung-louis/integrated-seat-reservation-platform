package view;

import controller.ReservationController;
import controller.SeatController;
import controller.StoreController;
import model.dto.SeatDto;
import model.dto.StoreDto;
import model.dto.UserDto;
import session.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserView {
    // [1] 싱글톤
    private UserView(){}
    private static UserView instance = new UserView();
    public static UserView getInstance() {
        return instance;
    }

    Scanner scan = new Scanner(System.in);
    StoreController storeController = StoreController.getInstance();
    SeatController seatController = SeatController.getInstance();
    ReservationController reservationController = ReservationController.getInstance();

    public void index(){
        UserDto currentUser = Session.getLoginUser();
        for(;;){
            System.out.printf("""
                    ╔══════════════════════════════════════════════════╗
                    ║                좌석 예약 시스템 - 사용자               ║
                    ║                 환영합니다, %s님!                 ║
                    ╚══════════════════════════════════════════════════╝
                    \n""", currentUser.getName());
            System.out.printf("오늘 날짜: %s\n", LocalDateTime.now());
            System.out.println();
            System.out.println("""
                    🔹예약 서비스
                    1. 매장 검색 및 예약하기
                    2. 내 예약 조회/변경/취소
                    3. 예약 내역(이용완료)
                    
                    🔹내 정보
                    4. 마이페이지
                    
                    🔹기타
                    5. 로그아웃
                    
                    """);
            System.out.print("입력 > ");
            int ch = scan.nextInt();
            if( ch == 1 ) {
                storeListView();
            }
            else if ( ch == 2 ) {
                myReservationListView();
                // TODO : 내 예약 조회/변경/취소
            }
            else if ( ch == 3 ) {
                // TODO : 예약 내역

            }
            else if ( ch == 4 ) {
                // TODO : 마이페이지

            }
            else if ( ch == 5 ) {
                Session.logout();
                break;
            }
        }
    }

    public void storeListView(){
        for(;;) {
            // STATUS
            UserDto currentUser = Session.getLoginUser();
            ArrayList<StoreDto> stores = storeController.getStores();

            System.out.printf("""
                    ╔══════════════════════════════════════════════════╗
                    ║                좌석 예약 시스템 - 사용자               ║
                    ║                 환영합니다, %s님!                 ║
                    ╚══════════════════════════════════════════════════╝
                    \n""", currentUser.getName());
            System.out.printf("오늘 날짜: %s\n", LocalDateTime.now());
            System.out.println();
            System.out.println("""
                    ================================================
                                        매장 리스트
                    ================================================
                    """);
            stores.forEach(store -> {
                System.out.printf("| %d | %s |\n", store.getNo(), store.getName());
            });
            System.out.println("| 0 | 뒤로가기 |");
            System.out.println();
            System.out.print("좌석을 예약할 매장 번호를 입력하세요 > ");
            int ch = scan.nextInt();
            if (ch != 0) {
                reservationView(ch);
            } else {
                break;
            }
        }
    }
    public void myReservationListView(){

    }
    public void reservationView(int store_no){
        for(;;) {
            // STATUS
            UserDto currentUser = Session.getLoginUser();
            StoreDto selectedStore = storeController.getStore(store_no);
            // [1] store_no가 존재하는 매장인지 체크 (없으면 break)
            if(selectedStore == null) {
                System.out.println("해당 매장이 존재하지 않습니다. 다시 매장을 선택해주세요.");
                System.out.println();
                break;
            }
            ArrayList<SeatDto> seats = seatController.getSeats(store_no);

            System.out.printf("""
                    ╔══════════════════════════════════════════════════╗
                    ║                좌석 예약 시스템 - 사용자               ║
                    ║                 환영합니다, %s님!                 ║
                    ╚══════════════════════════════════════════════════╝
                    \n""", currentUser.getName());
            System.out.printf("오늘 날짜: %s\n", LocalDateTime.now());
            System.out.println();
            SeatChart.showSeatingChartForReservationManagement(seats, selectedStore.getName()); // 좌석 배치도 출력

            boolean isReservationViewEnd = false;
            for(;;) {
                System.out.print("예약하고 싶은 좌석을 입력하세요 (예 : B-7) (뒤로가기 : 0) : > ");
                String rawSeatCode = scan.next();
                if (rawSeatCode.equals("0")) {
                    isReservationViewEnd = true;
                    break;
                } else {
                    int result = reservationController.doReservation(currentUser.getNo(), store_no, rawSeatCode);
                    if (result == 1) {
                        int todo = reservationSuccessView(selectedStore.getName(), seats, rawSeatCode);
                        if( todo == 1 ) { isReservationViewEnd = false;}
                        else { isReservationViewEnd = true; }
                        break;
                    } // 예약 성공
                    else if (result == 0) {
                        System.out.println("서버 오류로 예약에 실패했습니다. 다시 시도해주세요.");
                    } else if (result == -1) {
                        System.out.println(rawSeatCode + " 좌석은 이미 예약된 자리입니다. 다시 선택해주세요.");
                    } else if (result == -2) {
                        System.out.println(rawSeatCode + " 좌석은 " + selectedStore.getName() + " 매장에 존재하지 않습니다. 다시 선택해주세요.");
                    }
                }
            }
            if(isReservationViewEnd) { break; }
        }
    }
    public int reservationSuccessView(String storeName, ArrayList<SeatDto> seats, String selectedRawSeatCode){
        UserDto currentUser = Session.getLoginUser();
        System.out.printf("""
                    ╔══════════════════════════════════════════════════╗
                    ║                좌석 예약 시스템 - 사용자               ║
                    ║                 환영합니다, %s님!                 ║
                    ╚══════════════════════════════════════════════════╝
                    \n""", currentUser.getName());
        System.out.printf("오늘 날짜: %s\n", LocalDateTime.now());
        System.out.println();
        SeatChart.showSeatingChartForUser(seats, selectedRawSeatCode, storeName); // 좌석 배치도 출력
        System.out.println();
        System.out.println("""
                성공적으로 예약되었습니다!
                
                1. 이 매장에서 추가 예약하기
                2. 홈으로
                
                """);
        System.out.print("입력 > ");
        int ch = scan.nextInt();
        if( ch == 1 ){
            return 1;
        }
        else if( ch == 2 ){
            return 2;
        }
        else{ // 다른 숫자를 입력해도 홈으로 이동
            return 2;
        }
    }
}
