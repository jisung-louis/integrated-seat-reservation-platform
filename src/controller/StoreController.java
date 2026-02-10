package controller;

public class StoreController {
    // [1] 싱글톤
    private StoreController(){}
    private static StoreController instance = new StoreController();
    public static StoreController getInstance() {
        return instance;
    }
}
