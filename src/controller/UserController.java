package controller;

import model.dao.JdbcUserDao;
import model.dao.UserDao;
import model.dto.UserDto;

public class UserController {
    // [1] 싱글톤
    private UserController(){}
    private static UserController instance = new UserController();
    public static UserController getInstance() {
        return instance;
    }

    private UserDao ud = JdbcUserDao.getInstance();

    public UserDto login(String id, String password){
        return ud.login(id,password);
    }
    public boolean signup(String id, String password, String name, boolean isAdmin){
        return ud.signup(id,password,name,isAdmin);
    }
}
