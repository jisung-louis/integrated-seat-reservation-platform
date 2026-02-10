package view;

import controller.ReservationController;

public class LoginView {
    // [1] 싱글톤
    private LoginView(){}
    private static LoginView instance = new LoginView();
    public static LoginView getInstance() {
        return instance;
    }
}
