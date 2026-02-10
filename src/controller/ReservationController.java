package controller;

public class ReservationController {
    // [1] 싱글톤
    private ReservationController(){}
    private static ReservationController instance = new ReservationController();
    public static ReservationController getInstance() {
        return instance;
    }
}
