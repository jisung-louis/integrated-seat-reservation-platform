package model.dao;

import model.dto.UserDto;

public interface UserDao {
    /**
     *
     * @param id 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 해당 유저의 유저번호(user_no) 반환
     */
    UserDto login(String id, String password);
    boolean signup(String id, String password, String name, boolean isAdmin);
}
