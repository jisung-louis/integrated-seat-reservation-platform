package view;

import constant.SeatPolicy;
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
    public static AdminView getInstance() {return instance;}
    Scanner scan=new Scanner(System.in);
    StoreController sc=StoreController.getInstance();
    SeatController seatC = SeatController.getInstance();
    LocalDate now=LocalDate.now();
    ArrayList<StoreDto> result=sc.getStores();
    public void index(){
        for(;;){
            try {
                System.out.println(
                        "╔══════════════════════════════════════════════════╗\n" +
                        "║              좌석 예약 시스템 - 관리자              ║\n" +
                        "║                환영합니다, %s님!                     ║\n" +
                        "╚══════════════════════════════════════════════════╝\n");
                System.out.println("관리자: %s");
                System.out.printf("오늘 날짜: %s\n",now);
                System.out.println("\n" +
                        "========================================\n" +
                        "        관리할 매장을 선택하세요\n" +
                        "========================================\n");

                int count=1;
                for(StoreDto stores:result){
                    System.out.println(count+". "+stores.getName());
                    count++;
                }
                System.out.println("100.매장추가\n200.로그아웃\n");
                System.out.print("선택>");
                int ch=scan.nextInt();
                if (ch>=1&&ch<=result.size()) {
                    StoreDto selectedStore=result.get(ch-1);
                    Management(selectedStore);
                }
                else if (ch == 100) {addView();}
                else if (ch == 200) {}
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
    public void Management(StoreDto selectedStore){
        System.out.printf(
                "╔══════════════════════════════════════════════════╗\n" +
                "║           <%s 매장의>좌석 예약 시스템     ║\n" +
                "║               관리자님 환영합니다!                  ║\n" +
                "╚══════════════════════════════════════════════════╝\n\n",selectedStore.getName());
        System.out.printf("\uD83D\uDCC5 오늘 날짜: %s\n",now);
        System.out.println("\n========================================\n");
        System.out.printf("\uD83D\uDCCB 매장명: %s\n" +"========================================\n\n",selectedStore.getName());
        System.out.println(
                "\uD83D\uDD39 매장 관리\n" +
                "1. 매장 정보 관리\n2. 좌석 배치 관리\n" +
                "\uD83D\uDD39 예약 관리\n" +
                "3. 예약 내역 조회"+
                "\uD83D\uDD39 기타\n" +
                "8. 로그아웃\n" +
                "\n" +
                "선택 >> ");int ch=scan.nextInt();
                switch (ch){
                    case 1:
                        StoreManagement(selectedStore);
                    case 2:break;
                    case 3:break;
                    case 4:break;
                }
    }
    public void StoreManagement (StoreDto selectedStore){
        System.out.println(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                  매장 정보 관리                    ║\n" +
                "╚══════════════════════════════════════════════════╝");
        System.out.printf("========================================\n현재 매장 정보\n========================================\n매장명: %s\n", selectedStore.getName());
        System.out.printf("카테고리: %s\n\n", selectedStore.getCategory());
        System.out.printf("주소: %s\n",selectedStore.getAddress());
        System.out.printf("연락처: %s\n",selectedStore.getContact());
        System.out.printf("이메일: admin@cafeonlview.com\n\n",selectedStore.getEmail());
        System.out.printf("영업시간:\n 평일 : %s\n",selectedStore.getBh_weekdays());
        System.out.printf("  토요일: %s\n",selectedStore.getBh_saturday());
        System.out.printf("  일요일/공휴일: %s\n\n",selectedStore.getBh_sunday());
        System.out.println("총 좌석: 48석\n" );     //아직
        System.out.printf("운영 상태: \uD83D\uDFE2 %s\n\n",selectedStore.getStatus());
        System.out.println(
                "등록일: 2026-02-09\n" +   //아직
                "\n" +
                "========================================\n" +
                "\n" +
                "1. 매장 수정\n" +
                "2. 매장 삭제\n" +
                "3. 뒤로 가기\n" +
                "\n" +
                "선택 >>"); int ch=scan.nextInt();
        switch (ch){
            case 1:
                StoreUpdate(selectedStore);break;
            case 2: break;
            case 3:
                Management(selectedStore);break;
            default: break;
        }
    }
    // [3] 게시물 수정
    public void StoreUpdate(StoreDto selectedStore){
        System.out.printf(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                    매장 정보 수정                  ║\n" +
                "╚══════════════════════════════════════════════════╝\n\n" +
                "매장: %s\n\n" +
                "※ 매장명 변경은 고객센터 문의 필요\n" +
                "========================================\n\n" +
                "\uD83D\uDCDE 매장 연락처\n" +
                "현재: %s\n" +
                "변경: (변경 안하려면 Enter) >> 02-1234-99909\n" +
                "✓ 변경됨\n" +
                "\n" +
                "\uD83D\uDCE7 이메일\n" +
                "현재: admin@cafeonlview.com\n" +
                "변경: (변경 안하려면 Enter) >> \n" +
                "\n" +
                "\uD83D\uDCCD 주소\n" +
                "현재: 서울특별시 강남구 테헤란로 123, 2층\n" +
                "변경: (변경 안하려면 Enter) >> 서울특별시 강남구 테헤란로 123, 3층\n" +
                "✓ 변경됨\n" +
                "\n" +
                "\uD83C\uDFF7\uFE0F 매장 카테고리\n" +
                "현재: 카페\n" +
                "변경: 스터디카페\n" +
                "선택: (변경 안하려면 Enter) >> \n" +
                "✓ 변경됨\n" +
                "\n" +
                "\uD83D\uDFE2 운영 상태\n" +
                "현재: 정상 영업중\n" +
                "변경:\n" +
                "  1. \uD83D\uDFE2 정상 영업중\n" +
                "  2. \uD83D\uDFE1 예약 일시중단\n" +
                "  3. \uD83D\uDD34 영업 중단\n" +
                "선택: (변경 안하려면 Enter) >> \n" +
                "\n" +
                "========================================\n" +
                "\n" +
                "정말 저장하시겠습니까? (Y/N) >> Y\n" +
                "\n" +
                "✓ 매장 정보가 업데이트되었습니다!\n" +
                "\n" +
                "1. 계속 수정하기\n" +
                "2. 매장 정보 관리로\n" +
                "3. 뒤로 가기\n" +
                "\n" +
                "선택 >>",selectedStore.getName(),selectedStore.getContact());int ch=scan.nextInt();
    }
//    // 게시물 삭제
//    public void StoreDelete(){
//        System.out.print("삭제할 게시물 번호:"); int bno=sc.nextInt();
//        boolean result=bc.delete(bno);
//        if(result){
//            System.out.println("[안내]삭제 성공");
//        }else{
//            System.out.println("[경고]삭제 실패");
//        }
//    }
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

    // [2] 게시물 전체 조회
    public void findAll(){
        ArrayList<BoardDto> boards=bc.findAll();
        for(BoardDto board:boards){
            System.out.printf("번호:%d, 작성일:%s, 작성자:%s, 내용:%s\n",
                    board.getBno(),board.getBwriter(),board.getBwriter(),board.getBcontent());
        }
    }
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
            int maxSeats = SeatPolicy.MAX_SEAT_COLUMN_COUNT * SeatPolicy.MAX_SEAT_ROW_COUNT;
            int totalSeats = seats.size();

            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                    좌석 배치 관리                   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("매장 : " + storeName);
            System.out.println("총 좌석 : " + totalSeats);
            System.out.println();

            SeatChart.showSeatingChartForSeatManagement(seats); // 좌석 배치도 출력

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
                int result = seatC.activateAllSeat(store_no);
                if(result == maxSeats){
                    System.out.println("✓ 모든 좌석이 활성화되었습니다!");
                }
                else{
                    System.out.printf("✕ %d개의 좌석 중 %d개의 좌석만 활성화에 성공했습니다.\n",maxSeats, result);
                }
            } else if (ch == 3) {
                int result = seatC.deactivateAllSeat(store_no);
                if(result == totalSeats){
                    System.out.println("✓ 모든 좌석이 비활성화되었습니다!");
                }
                else{
                    System.out.printf("✕ %d개의 좌석 중 %d개의 좌석만 비활성화에 성공했습니다.\n",totalSeats, result);
                }
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
            SeatChart.showSeatingChartForSeatManagement(seats);
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
}
