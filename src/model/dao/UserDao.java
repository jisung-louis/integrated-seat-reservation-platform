package model.dao;

public interface UserDao {
    boolean login(String id, String password);
    boolean signup(String id, String password, String name, boolean isAdmin);
}
