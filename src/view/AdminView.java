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

import controller.UserController;
import model.dto.SeatDto;
import model.dto.StoreDto;
import model.dto.UserDto;
import session.Session;

public class AdminView {
    private AdminView(){}
    private static AdminView instance = new AdminView();
    public static AdminView getInstance() {return instance;}
    Scanner scan = new Scanner(System.in);
    StoreController sc = StoreController.getInstance();
    SeatController seatC = SeatController.getInstance();
    LocalDate now = LocalDate.now();
    public void index(UserDto user){
        for(;;){
            try {
                System.out.printf("╔══════════════════════════════════════════════════╗\n");
                System.out.printf("║              좌석 예약 시스템 - 관리자              ║\n");
                System.out.printf("║              환영합니다, %-24s ║\n",user.getName()+"님!");
                System.out.printf("╚══════════════════════════════════════════════════╝\n");
                System.out.printf("관리자: %s\n",user.getName());
                System.out.printf("오늘 날짜: %s\n",now);
                System.out.println("\n" +
                        "========================================\n" +
                        "        관리할 매장을 선택하세요\n" +
                        "========================================\n");

                int count = 1;
                ArrayList<StoreDto> result = sc.getMyStores(user.getNo());
                for(StoreDto stores:result){
                    System.out.println(count+". "+stores.getName());
                    count++;
                }
                System.out.println("100.매장추가\n200.로그아웃\n");
                System.out.print("선택>");
                int ch=scan.nextInt();
                if (ch>=1&&ch<=result.size()) {
                    StoreDto selectedStore=result.get(ch-1);
                    managementView(selectedStore);
                }
                else if (ch == 100) {addView();}
                else if (ch == 200) {Session.logout();break;}
                else {System.out.println("[경고]없는 기능 번호입니다.");}
            }catch (InputMismatchException i){
                System.out.println("[경고]잘못된 입력 방식입니다.[재입력]");
                scan = new Scanner(System.in);
            }catch (Exception e){
                System.out.println("[시스템오류]관리자에게 문의");
                break;
            }
        }
    }
    public void addView(){
        System.out.println(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                      매장 추가                    ║\n" +
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
        int status = scan.nextInt();
        scan.nextLine();
        System.out.println("========================================\n" +
                            "이대로 매장을 추가하시겠습니까? (Y/N) >>");char add=scan.next().charAt(0);
        if(add == 'Y'){
            sc.addStore(Session.loginUser.getNo(), name,category,address,contact,email,bh_weekdays,bh_saturday,bh_sunday,status);
            System.out.println("✓ 매장이 등록되었습니다!\n매장 선택 화면으로 이동");
        }else if(add == 'N'){
            System.out.println("매장등록을 취소하셨습니다");
        }else{
            System.out.println("[경고]잘못된 입력입니다.");
        }
    }
    public void managementView(StoreDto selectedStore){
        for(;;) {
            System.out.printf("╔══════════════════════════════════════════════════╗\n");
            System.out.printf("║           <%-31s║\n",selectedStore.getName()+"매장>의좌석 예약 시스템");
            System.out.printf("║                  %-27s ║\n",Session.getLoginUser().getName()+"님 환영합니다!");
            System.out.printf("╚══════════════════════════════════════════════════╝\n\n");
            System.out.printf("\uD83D\uDCC5 오늘 날짜: %s\n", now);
            System.out.println("\n========================================\n");
            System.out.printf("\uD83D\uDCCB 매장명: %s\n" + "========================================\n\n", selectedStore.getName());
            System.out.print(
                    "\uD83D\uDD39 매장 관리\n" +
                            "1. 매장 정보 관리\n" +
                            "2. 좌석 배치 관리\n" +
                            "3. 영업시간 설정(미완)\n" +
                            "\n" +
                            "\uD83D\uDD39 예약 관리\n" +
                            "4. 예약 내역 조회\n" +
                            "5. 예약 수동 등록(미완)\n" +
                            "6. 취소/환불 처리(미완)\n" +
                            "\n" +
                            "\uD83D\uDD39 설정\n" +
                            "7. 관리자 정보 수정\n" +
                            "\n" +
                            "\uD83D\uDD39 기타\n" +
                            "8. 뒤로가기\n" +
                            "\n" +
                            "선택 >> ");
            String input = scan.nextLine();
            if (input.equals("1")) {storeManagementView(selectedStore);} // 매장 정보 관리 선택 시 -> storeManagementView 로 이동
            else if (input.equals("2")) {seatManagementView(selectedStore.getNo());} // 좌석 배치 관리 선택 시 -> setManagementView 로 이동
            else if (input.equals("3")) {}// TODO : 영업시간 설정 뷰로 이동}
            else if (input.equals("4")) {adminReservationView(selectedStore.getNo(),selectedStore.getName());} // 예약 내역 조회 선택시 -> adminReservationView 로 이동
            else if (input.equals("5")) {} // TODO : 예약 수동 등록 뷰로 이동
            else if (input.equals("6")) {} // TODO : 취소/환불 처리 뷰로 이동
            else if (input.equals("7")) {adminUpdate();} // TODO : 관리자 정보 수정 뷰로 이동
            else if (input.equals("8")) {break;} // TODO : 뒤로가기
            else if(input.isEmpty()||Integer.parseInt(input)<1||Integer.parseInt(input)>8) {    // 입력 번호가 다를때
                System.out.println("[오류]입력하신 번호가 잘못 되었습니다.다시 입력해주세요");
            }
            else {System.out.println("[오류]잘못된 입력입니다.");}
        }
    }
    public void storeManagementView (StoreDto selectedStore){
        int totalSeatCount=seatC.getSeats(selectedStore.getNo()).size();
        for(;;) {
            System.out.println(
                    "╔══════════════════════════════════════════════════╗\n" +
                            "║                  매장 정보 관리                    ║\n" +
                            "╚══════════════════════════════════════════════════╝");
            System.out.printf("========================================\n현재 매장 정보\n========================================\n매장명: %s\n", selectedStore.getName());
            System.out.printf("카테고리: %s\n\n", selectedStore.getCategory());
            System.out.printf("주소: %s\n", selectedStore.getAddress());
            System.out.printf("연락처: %s\n", selectedStore.getContact());
            System.out.printf("이메일: %s\n\n", selectedStore.getEmail());
            System.out.printf("영업시간:\n 평일 : %s\n", selectedStore.getBh_weekdays());
            System.out.printf("  토요일: %s\n", selectedStore.getBh_saturday());
            System.out.printf("  일요일/공휴일: %s\n\n", selectedStore.getBh_sunday());
            System.out.printf("총 좌석: %d\n",totalSeatCount);
            System.out.printf("운영 상태: \uD83D\uDFE2 %s\n\n", selectedStore.getStatus());
            System.out.print(
                    "========================================\n" +
                    "\n" +
                    "1. 매장 수정\n" +
                    "2. 매장 삭제\n" +
                    "3. 뒤로 가기\n" +
                    "\n" +
                    "선택 >>");
            String input = scan.nextLine();
            if (input.equals("1")) {storeUpdateView(selectedStore);} // 매장 수정 선택 시 -> storeUpdateView 로 이동
            else if (input.equals("2")) {storeDeleteView(selectedStore);} // 매장 삭제 선택 시 -> storeDeleteView 로 이동
            else if (input.equals("3")) {break;}                        // 뒤로 가기 선택 시 -> managementView로 이동
            else if (input.isEmpty()) {System.out.println("[오류]아무것도 입력하지 않았습니다.다시 입력해주세요.");}   // 입력하지 않았을 경우 반복
            else{System.out.println("[오류]잘못된 입력입니다.");}     // 잘못된 입력일 경우 반복
        }
    }
    // [3] 게시물 수정
    public void storeUpdateView(StoreDto selectedStore){
        System.out.printf(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                    매장 정보 수정                  ║\n" +
                "╚══════════════════════════════════════════════════╝\n\n" +
                "매장: %s\n\n" +
                "※ 매장명 변경은 고객센터 문의 필요\n" +
                "========================================\n\n" +
                "\uD83D\uDCDE 매장 연락처\n" +
                "현재: %s\n" +
                "변경: (변경 안하려면 Enter) >> \n",selectedStore.getName(),selectedStore.getContact());
        String contact = scan.nextLine();
        if (contact.isEmpty()) contact = selectedStore.getContact();
        System.out.printf("\n\uD83D\uDCE7 이메일\n" +
                "현재: %s\n" +
                "변경: (변경 안하려면 Enter) >> ",selectedStore.getEmail()); String email=scan.nextLine();
        if (email.isEmpty()) email = selectedStore.getEmail();
        System.out.printf("\n\uD83D\uDCCD 주소\n" +
                "현재: %s\n" +
                "변경: (변경 안하려면 Enter) >> ",selectedStore.getAddress()); String adress=scan.nextLine();
        if (adress.isEmpty()) adress = selectedStore.getAddress();
        System.out.printf(
                "\n\uD83C\uDFF7\uFE0F 매장 카테고리\n" +
                "현재: %s\n" +
                "변경: (변경 안하려면 Enter) >> ",selectedStore.getCategory());String category=scan.nextLine();
        if (category.isEmpty()) category = selectedStore.getCategory();
        System.out.printf("\n⏰ 평일 영업시간\n현재: %s\n변경: >> ", selectedStore.getBh_weekdays());
        String bh_weekdays = scan.nextLine();
        if (bh_weekdays.isEmpty()) bh_weekdays = selectedStore.getBh_weekdays();

        System.out.printf("⏰ 토요일 영업시간\n현재: %s\n변경: >> ", selectedStore.getBh_saturday());
        String bh_saturday = scan.nextLine();
        if (bh_saturday.isEmpty()) bh_saturday = selectedStore.getBh_saturday();

        System.out.printf("⏰ 일요일/공휴일 영업시간\n현재: %s\n변경: >> ", selectedStore.getBh_sunday());
        String bh_sunday = scan.nextLine();
        if (bh_sunday.isEmpty()) bh_sunday = selectedStore.getBh_sunday();
        String status = (selectedStore.getStatus() == 1) ? "정상 영업중" :
                (selectedStore.getStatus() == 2) ? "예약 일시중단" :
                        (selectedStore.getStatus() == 3) ? "영업 중단" : "오류";
        System.out.printf("\uD83D\uDFE2 운영 상태\n" +
                "현재: %s\n" +
                "변경:\n" +
                "  1. \uD83D\uDFE2 정상 영업중\n" +
                "  2. \uD83D\uDFE1 예약 일시중단\n" +
                "  3. \uD83D\uDD34 영업 중단\n" +
                "선택: (변경 안하려면 Enter) >> ",status);String chInput = scan.nextLine();
        int newStatus= chInput.isEmpty() ? selectedStore.getStatus() : Integer.parseInt(chInput);
        for(;;) {
            System.out.println("\n\n========================================\n\n" +
                    "정말 저장하시겠습니까? (Y/N) >> ");
            String confirm = scan.nextLine();
            if (confirm.equals("Y")) {
                boolean result=StoreController.getInstance().updateStore(
                        selectedStore.getNo(),
                        selectedStore.getName(),
                        category,
                        adress,
                        contact,
                        email,
                        bh_weekdays,
                        bh_saturday,
                        bh_sunday,
                        newStatus
                );
                System.out.println("\n✓ 매장 정보가 업데이트되었습니다!\n\n" +
                        "1. 계속 수정하기\n" +
                        "2. 뒤로가기\n\n" +
                        "선택 >>");int ch=scan.nextInt();
                if( ch == 1 ){
                    continue;
                }
                else if( ch == 2){
                    break;
                }
            } else if (confirm.equals("N")) {
                System.out.println("\n✕ 수정을 취소합니다. 이전 화면으로 돌아갑니다.\n\n");
                break;
            } else{
                System.out.println("[안내]저장을 실패하였습니다 Y/N로 입력해주세요");
                continue;
            }
            break;
        }
    }
    public void storeDeleteView(StoreDto selectedStore){
        System.out.printf(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                      매장 삭제                    ║\n" +
                "╚══════════════════════════════════════════════════╝\n\n" +
                "⚠\uFE0F  경고: 매장 삭제는 복구할 수 없습니다!\n\n"+
                "========================================\n" +
                "           삭제할 매장 정보\n" +
                "========================================\n" +
                "\n" +
                "매장명: %s\n" +
                "카테고리: %s\n" +
                "주소: %s\n" +
                "연락처: %s\n" +
                "이메일: %s\n\n" +
                "영업시간:\n" +
                "  평일: %s\n" +
                "  토요일: %s\n" +
                "  일요일/공휴일: %s\n\n",selectedStore.getName(),selectedStore.getCategory(),selectedStore.getAddress(),
                                selectedStore.getContact(),selectedStore.getEmail(),selectedStore.getBh_weekdays(),
                                selectedStore.getBh_saturday(),selectedStore.getBh_sunday());
        System.out.println("운영 상태: \uD83D\uDFE2 정상 영업중\n\n" +
                "========================================\n\n" +
                "⚠\uFE0F  매장 삭제 시 주의사항:\n" +
                "- 모든 좌석 배치 정보가 삭제됩니다\n" +
                "- 진행중인 예약이 있다면 자동 취소됩니다\n" +
                "- 과거 예약 내역도 모두 삭제됩니다\n" +
                "- 삭제 후 복구할 수 없습니다\n\n" +
                "========================================\n");
        for(;;) {
            System.out.print("정말 매장 정보를 삭제하시겠습니까? (Y/N) >> ");
            String confirm = scan.nextLine();
            if (confirm.equals("Y")) {
                // 매장삭제 안됨 DB에는 저장되지만 실제로 테스트할땐 살아있고 매장을 추가할때 제품번호는 늘어나지만 삭제한 뒤 제품번호가 최신화 안됨
                // 즉] 추가할 시 인덱스는 늘어나고 삭제하면 그만큼 인덱스가 최신화되야함 매장을 삭제하면 삭제화면/관리화면이 아닌 관리할매장 선택 화면으로 가져야함
                StoreController.getInstance().deleteStore(selectedStore.getNo());
                System.out.println("\n✓ 매장 정보가 삭제되었습니다!\n");
                break;
            } else if (confirm.equals("N")) {
                System.out.println("\n✕ 매장 정보 삭제를 취소합니다. 이전 화면으로 돌아갑니다.\n\n");
                break;
            }else{System.out.println("[안내]저장을 실패하였습니다 Y/N로 입력해주세요");}
        }
    }
    public void adminUpdate(){
        UserDto admin=Session.getLoginUser();
        System.out.printf(
                "╔══════════════════════════════════════════════════╗\n" +
                "║                  관리자 정보 수정                  ║\n" +
                "╚══════════════════════════════════════════════════╝\n" +
                "\n" +
                "현재 로그인 계정: %s\n" +
                "성함: %s\n" +
                "\n" +
                "========================================\n" +
                "           본인 확인을 진행합니다\n" +
                "========================================\n" +
                "\n",admin.getId(),admin.getName());
        for(;;) {
            System.out.println("\uD83D\uDD11 현재 비밀번호 입력 >> ");
            String password = scan.nextLine();
            if (admin.getPassword().equals(password)) {
                System.out.println("[정보]로그인 확인 되었습니다.");
                System.out.printf(
                        "\n" +
                                "========================================\n" +
                                "           수정할 정보를 입력하세요\n" +
                                "========================================\n" +
                                "\n" +
                                "\uD83D\uDC64 성함 변경 (현재: %s)\n" +
                                "   >> (변경 안 하려면 Enter) ", admin.getName());
                String name = scan.nextLine();
                if (name.isEmpty()) {
                    name = admin.getName();
                }
                System.out.println("\n" +
                        "\uD83D\uDD10 새 비밀번호 입력\n" +
                        "   >> (변경 안 하려면 Enter) ");
                String newPassword = scan.nextLine();
                if (newPassword.isEmpty()) {
                    newPassword = admin.getPassword();
                }
                System.out.printf("\n" +
                        "\uD83D\uDCF1 아이디 변경 (현재: %s)\n" +
                        "   >> (변경 안 하려면 Enter) ", admin.getId());
                String newid = scan.nextLine();
                if (newid.isEmpty()) {
                    newid = admin.getId();
                }
                System.out.println(
                        "\n========================================\n\n");
                for (; ; ) {
                    System.out.println("정말 수정한 정보로 저장하시겠습니까? (Y/N) >> ");
                    String confrim = scan.nextLine();
                    if (confrim.equals("Y")) {
                        admin.setId(newid);
                        admin.setName(name);
                        admin.setPassword(newPassword);
                        boolean result = UserController.getInstance().update(admin);
                        if (result) {
                            System.out.println("\n✓ 관리자 정보가 성공적으로 업데이트되었습니다!\n\n");
                            break;
                        } else {
                            System.out.println("[오류]관리자 정보 수정을 실패하였습니다.백엔드문제");
                        }
                    } else if (confrim.equals("N")) {
                        System.out.println("\n✕ 수정을 취소합니다. 이전 화면으로 돌아갑니다.\n\n");
                        break;
                    } else {
                        System.out.println("[안내]저장을 실패하였습니다 Y/N로 입력해주세요");
                    }
                }
            } else {
                System.out.println("다시 입력해주세요.");
                continue;
            }
            break;
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

    public void adminReservationView(int store_no, String storeName) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║                    전체 예약 내역                   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println("매장: " + storeName);
            System.out.println("조회일: " + LocalDate.now());
            System.out.println();

            // [C 담당자 영역] 좌석 배치도 출력
            ArrayList<SeatDto> seats = seatC.getSeats(store_no);
            SeatChart.showSeatingChartForReservationManagement(seats);

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
