package controller;

public class UserController {
    // [1] 싱글톤
    private UserController(){}
    private static UserController instance = new UserController();
    public static UserController getInstance() {
        return instance;
    }
}
