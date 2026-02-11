package view;

import controller.ReservationController;
import controller.StoreController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminView {
    // [1] 싱글톤
    private AdminView(){}
    private static AdminView instance = new AdminView();
    public static AdminView getInstance() {
        return instance;
    }
    Scanner scan=new Scanner(System.in);
    StoreController sc=StoreController.getInstance();
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
}
