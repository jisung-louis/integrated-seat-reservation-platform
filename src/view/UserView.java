package view;

import controller.ReservationController;
import controller.SeatController;
import controller.StoreController;
import model.dto.ReservationDto;
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
        for(;;){
            // STATUS
            UserDto currentUser = Session.getLoginUser();
            int userNo = currentUser.getNo();
            ArrayList<ReservationDto> reservationList = reservationController.getStoreReservationsByUserNo(userNo);
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
                                        내 예약 목록
                    ================================================
                    
                    """);
            reservationList.forEach((reservation) -> {
                String fullSeatCode = reservation.getSeat_code(); // "12-B-4" 꼴
                String[] splitSeatCode = fullSeatCode.split("-",2);
                int storeNo = Integer.parseInt(splitSeatCode[0]);

                int no = reservation.getNo();
                String storeName = storeController.getStore(storeNo).getName();
                String rawSeatCode = splitSeatCode[1];
                String reservedAt = reservation.getReservedAt();
                System.out.printf("| %d | %s | %s 좌석 예약 완료 | %s에 예약함\n",no, storeName, rawSeatCode, reservedAt);
            });
            System.out.println();
            System.out.println("자세한 좌석 위치를 확인하거나");
            System.out.println("좌석 변경/취소하고 싶은 예약 번호를 입력하세요(뒤로가기 : 0)");
            System.out.print("입력 > ");
            int reservationNo = scan.nextInt();
            if( reservationNo == 0 ){
                break;
            }
            else{
                reservationDetailView(reservationNo);
            }
        }
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

    public void reservationDetailView(int reservationNo){
        UserDto currentUser = Session.getLoginUser();
        ReservationDto reservation = reservationController.getReservationByNo(reservationNo);
        String fullSeatCode = reservation.getSeat_code(); // "12-B-4" 꼴
        String[] splitSeatCode = fullSeatCode.split("-",2);
        int storeNo = Integer.parseInt(splitSeatCode[0]);
        String selectedRawSeatCode = splitSeatCode[1]; // "B-4" 꼴

        ArrayList<SeatDto> seats = seatController.getSeats(storeNo);
        StoreDto store = storeController.getStore(storeNo);
        String storeName = store.getName();

        System.out.printf("""
                    ╔══════════════════════════════════════════════════╗
                    ║                좌석 예약 시스템 - 사용자               ║
                    ║                 환영합니다, %s님!                 ║
                    ╚══════════════════════════════════════════════════╝
                    \n""", currentUser.getName());
        System.out.printf("오늘 날짜: %s\n", LocalDateTime.now());
        System.out.println();

        SeatChart.showSeatingChartForUser(seats, selectedRawSeatCode,storeName);

        System.out.println("""
                1. 좌석 변경하기
                2. 예약 취소하기
                3. 뒤로가기
                
                """);
        System.out.print("입력 > ");
        int ch = scan.nextInt();
        if( ch == 1 ){ // 좌석 변경하기
            System.out.println("--- 좌석 변경 ---");
            System.out.printf("현재 %s 좌석을 어느 좌석으로 변경할까요? > ", selectedRawSeatCode);
            String newSeatCode = scan.next();

            int userNo = currentUser.getNo();
            int result = reservationController.updateReservation(userNo, storeNo, selectedRawSeatCode, newSeatCode);
            if( result == 0 ){ // 좌석 예약 변경 성공
                System.out.printf("성공적으로 %s 좌석에서 %s 좌석으로 예약이 변경되었습니다!\n\n",selectedRawSeatCode, newSeatCode);
            }
            else if( result == 1 ){
                System.out.printf("%s 좌석은 이미 예약되었거나 존재하지 않는 자리입니다.\n",selectedRawSeatCode);
            }
            else if( result == 2 ) { // DB 오류
                System.out.println("예약 변경에 실패했습니다. (DB 오류)");
            }
        }
        else if ( ch == 2 ) { // 예약 취소하기
            System.out.println("--- 좌석 예약 취소 ---");
            System.out.printf("정말 %s 좌석을 취소하시겠습니까? (y/n) > ", selectedRawSeatCode);
            String answer = scan.next();
            if(answer.equals("y")){
                int userNo = currentUser.getNo();
                boolean result = reservationController.deleteReservation(userNo, storeNo, selectedRawSeatCode);
                if ( result ){
                    System.out.println("예약이 취소되었습니다!");
                }
                else {
                    System.out.println("예약 취소에 실패했습니다.");
                }
            }
            else{
                return;
            }
        }
        else if ( ch == 3 ){ // 뒤로가기
            return;
        }
    }
}
