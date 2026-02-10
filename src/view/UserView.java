package view;

import controller.ReservationController;

public class UserView {
    // [1] 싱글톤
    private UserView(){}
    private static UserView instance = new UserView();
    public static UserView getInstance() {
        return instance;
    }
}
