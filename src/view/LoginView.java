package view;

import session.Session;
import controller.UserController;
import model.dto.UserDto;

import java.util.Scanner;

public class LoginView {
    // [1] 싱글톤
    private LoginView(){}
    private static LoginView instance = new LoginView();
    public static LoginView getInstance() {
        return instance;
    }
    // [2] Controller 인스턴스 가져오기
    private UserController uc = UserController.getInstance();
    // [3] 스캐너 인스턴스 생성
    Scanner scan = new Scanner(System.in);

    public void index(){
        for(;;) {
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                   좌석 예약 시스템                   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 종료");
            System.out.println();
            System.out.print("선택 > ");
            int ch = scan.nextInt();
            if (ch == 1) {
                loginView();
            } else if (ch == 2) {
                signupTypeSelectionView();
            } else if (ch == 3) {
                break;
            }
        }
    }

    public void loginView(){
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                       로그인                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.print("아이디 > "); String id = scan.next();
        System.out.print("비밀번호 > "); String password = scan.next();
        System.out.println();
        UserDto user = uc.login(id,password); // 로그인 (로그인 실패시 null 저장됨)
        Session.setLoginUser(user); // 세션에 로그인한 유저 정보 저장
        UserDto currentUser = Session.getLoginUser();
        if(currentUser == null) {
            System.out.println("✕ 로그인 실패!");
        }
        else {
            System.out.println("✓ 로그인 성공!");
            if(currentUser.isAdmin()){
                System.out.println("관리자 메인화면");
                AdminView admin = AdminView.getInstance();
                admin.index(currentUser);
            }
            else {
                // TODO : 사용자 메인화면으로
                System.out.println("사용자 메인화면");
            }
        }
    }

    public void signupTypeSelectionView(){
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                      회원가입                      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("회원 유형을 선택해주세요 : ");
        System.out.println();
        System.out.println("1. 일반 사용자(고객)");
        System.out.println("2. 매장 관리자(사업자)");
        System.out.println("3. 홈으로");
        System.out.println();
        System.out.print("선택 > ");
        int ch = scan.nextInt();
        if (ch == 1) {
            signupView(false);
        } else if (ch == 2) {
            signupView(true);
        } else if (ch == 3) {
            return;
        }
    }

    public void signupView(boolean isAdmin){
        String type = isAdmin ? "관리자" : "사용자";
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.printf("║                    %s 회원가입                   ║\n",type);
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.print("아이디 > "); String id = scan.next();
        System.out.print("비밀번호 > "); String password = scan.next();
        System.out.print("이름 > "); String name = scan.next();
        System.out.println();
        boolean result = uc.signup(id, password, name, isAdmin);
        System.out.println(result ? "✓ 회원가입 성공!" : "✕ 회원가입 실패!");
    }
}
