package view;

import constant.SeatPolicy;
import controller.ReservationController;
import controller.SeatController;
import controller.StoreController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import model.dto.SeatDto;
import model.dto.StoreDto;

public class AdminView {
    // [1] 싱글톤
    private AdminView(){}
    private static AdminView instance = new AdminView();
    public static AdminView getInstance() {
        return instance;
    }
    Scanner scan=new Scanner(System.in);
    StoreController sc=StoreController.getInstance();
    SeatController seatC = SeatController.getInstance();
    public void index(){
        for(;;){
            try {
                System.out.println(
                        "╔══════════════════════════════════════════════════╗\n" +
                        "║              좌석 예약 시스템 - 관리자              ║\n" +
                        "║                환영합니다, %s님!                     ║\n" +
                        "╚══════════════════════════════════════════════════╝\n");
                System.out.println("관리자: %s");
                LocalDate now=LocalDate.now();
                System.out.printf("오늘 날짜: %s\n",now);
                System.out.println("\n" +
                        "========================================\n" +
                        "        관리할 매장을 선택하세요\n" +
                        "========================================" +
                        "\n");

                System.out.println("a.매장추가\nb.로그아웃\n");
                System.out.print("선택>");
                int ch=scan.nextInt();
                if (ch == 1) {}//
                else if (ch == 2) {addView();}
                else if (ch == 3) {}
                else {System.out.println("[경고]없는 기능 번호입니다.");}
            }catch (InputMismatchException i){
                System.out.println("[경고]잘못된 입력 방식입니다.[재입력]");
                scan=new Scanner(System.in);  // 입력객체 초기화
            }catch (Exception e){
                System.out.println("[시스템오류]관리자에게 문의");
                break;
            }
        }
    }
    public void addView(){
        System.out.println(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                   매장 추가                       ║\n" +
                "╚══════════════════════════════════════════════════╝\n" +
                "\n" +
                "새로운 매장 정보를 입력하세요.");
        scan.nextLine();
        System.out.print("========================================\n" +
                "\n" +
                "\uD83D\uDCCB 매장명: ");String name=scan.nextLine();
        System.out.print(
                "\uD83C\uDFF7\uFE0F 카테고리:");String category=scan.nextLine();
        System.out.print(
                "\uD83D\uDCCD 주소:");String address=scan.nextLine();
        System.out.print(
                "\uD83D\uDCDE 연락처: ");String contact=scan.nextLine();
        System.out.print("\uD83D\uDCE7 이메일: ");String email=scan.next();
        scan.nextLine();
        System.out.print("⏰[평일] 영업시간: ");String bh_weekdays= scan.nextLine();
        System.out.print(" ⏰[토요일] 영업시간: ");String bh_saturday= scan.nextLine();
        System.out.print(" ⏰[일요일] 영업시간: ");String bh_sunday= scan.nextLine();

        System.out.println("\uD83D\uDFE2 운영 상태");
        System.out.println("1.정상 영업중");
        System.out.println("2.예약 일시 중단");
        System.out.print("3.영업 중단\n선택>");
        int status=scan.nextInt();
        scan.nextLine();
        System.out.println("========================================\n" +
                            "이대로 매장을 추가하시겠습니까? (Y/N) >>");char add=scan.next().charAt(0);
        if(add=='Y'){
            sc.addStore(1,name,category,address,contact,email,bh_weekdays,bh_saturday,bh_sunday,status);
            System.out.println("✓ 매장이 등록되었습니다!\n매장 선택 화면으로 이동");
        }else if(add=='N'){
            System.out.println("매장등록을 취소하셨습니다");
        }else{
            System.out.println("[경고]잘못된 입력입니다.");
        }
    }
    /*
    public void writeView(){
        sc.nextLine();
        System.out.print("내용:");String content=sc.nextLine();
        System.out.print("작성자:");String writer=sc.next();
        boolean result=bc.write(content,writer);  // 컨트롤러에게 입력받은 content,writer 전달하여 결과 받아오기
        // 받은 결과에 따른 화면 출력
        if(result){
            System.out.println("[안내]성공");
        }else{
            System.out.println("[경고]실패");
        }
    }
    // 게시물 삭제
    public void delete(){
        System.out.print("삭제할 게시물 번호:"); int bno=sc.nextInt();
        boolean result=bc.delete(bno);
        if(result){
            System.out.println("[안내]삭제 성공");
        }else{
            System.out.println("[경고]삭제 실패");
        }
    }
    // [2] 게시물 전체 조회
    public void findAll(){
        ArrayList<BoardDto> boards=bc.findAll();
        for(BoardDto board:boards){
            System.out.printf("번호:%d, 작성일:%s, 작성자:%s, 내용:%s\n",
                    board.getBno(),board.getBwriter(),board.getBwriter(),board.getBcontent());
        }
    }
    // [3] 게시물 수정
    public void update(){
        System.out.print("수정할 게시물번호:");int bno=sc.nextInt();sc.nextLine();
        System.out.print("수정할 내용:"); String bcontent=sc.nextLine();
        boolean result=bc.update(bno,bcontent);
        if(result){
            System.out.println("[안내]수정 성공");
        }else{
            System.out.println("[경고]수정 실패");
        }
    }*/
    /**
     *
     * @param store_no 매장 번호
     * @apiNote "좌석 배치 관리" 화면
     * @implNote 관리자 메인화면 -> 매장 선택 -> 2. 좌석배치관리 클릭 시 호출되는 화면
     */
    public void seatManagementView(int store_no){
        for(;;) {
            // STATUS
            StoreDto store = sc.getStore(store_no);
            String storeName = store.getName();
            ArrayList<SeatDto> seats = seatC.getSeats(store_no);

            int totalSeats = seats.size();

            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                    좌석 배치 관리                   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("매장 : " + storeName);
            System.out.println("총 좌석 : " + totalSeats);
            System.out.println();

            showSeatingChart(seats); // 좌석 배치도 출력

            System.out.println("1. 좌석 활성화/비활성화");
            System.out.println("2. 모든 좌석 활성화");
            System.out.println("3. 모든 좌석 비활성화");
            System.out.println("4. 뒤로가기");
            System.out.println();
            System.out.print("선택 > ");
            int ch = scan.nextInt();
            if (ch == 1) {
                seatActivatingView(store_no);
            } else if (ch == 2) {
                // TODO : 모든 좌석 활성화하는 Controller 함수 호출
            } else if (ch == 3) {
                // TODO : 모든 좌석 비활성화하는 Controller 함수 호출
            } else if (ch == 4) {
                return;
            }
        }
    }
    public void seatActivatingView(int store_no){
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  좌석 활성화/비활성화                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        for(;;) {
            ArrayList<SeatDto> seats = seatC.getSeats(store_no);
            showSeatingChart(seats);
            System.out.println("활성화/비활성화할 좌석의 좌표를 입력하세요 (예 : B-4)");
            System.out.print("입력 (뒤로가기 : 0) > ");
            String input = scan.next();
            if(Objects.equals(input, "0")) { return; }
            int result = seatC.toggleSeatStatus(store_no, input);
            if (result == 0) {
                System.out.println("실패");
            } else if (result == 1) {
                System.out.println("활성화 성공");
            } else if (result == 2) {
                System.out.println("비활성화 성공");
            } else if (result == 3) {
                System.out.println("적절하지 않은 좌표 코드 입력 (rowCode-colNum 꼴로 입력해야 함)");
            }
        }
    }
    private void showSeatingChart(ArrayList<SeatDto> seats){
        System.out.println("================================");
        System.out.println("            좌석 배치도");
        System.out.println("================================");

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
                if(isSeatExist(seats, column, row)){
                    System.out.print("■  ");
                }
                else {
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
    private boolean isSeatExist(ArrayList<SeatDto> seats, int colCode, int rowNum){
        for (SeatDto seat : seats) {
            char code = (char) (colCode + 64);
            String column = Character.toString(code);
            String row = Integer.toString(rowNum);
            if(seat.getColCode().equals(column) && seat.getRowNum().equals(row)){
                return true;
            }
        }
        return false;
    }

    public void adminReservationView(int store_no, String storeName) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║                    전체 예약 내역                     ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println("매장: " + storeName);
            System.out.println("조회일: " + LocalDate.now());
            System.out.println();

            // [C 담당자 영역] 좌석 배치도 출력
            ArrayList<SeatDto> seats = seatC.getSeats(store_no);

            // [B 담당자 영역] 예약 목록 출력
            ReservationController.getInstance().getStoreReservations(store_no);

            System.out.println("n. 다음 페이지 | p. 이전 페이지 | 0. 뒤로 가기");
            System.out.print("선택 >> ");
            String ch = scan.next();

            if (ch.equals("0")) {
                break;
            } else if (ch.equalsIgnoreCase("n")) {
                System.out.println(">> 다음 페이지로 이동합니다.");
            } else if (ch.equalsIgnoreCase("p")) {
                System.out.println(">> 이전 페이지로 이동합니다.");
            } else {
                System.out.println(">> 잘못된 입력입니다.");
            }
        }
    }
}
