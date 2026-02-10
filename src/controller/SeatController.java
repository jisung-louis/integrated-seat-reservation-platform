package controller;

public class SeatController {
    // [1] 싱글톤
    private SeatController(){}
    private static SeatController instance = new SeatController();
    public static SeatController getInstance() {
        return instance;
    }
}
