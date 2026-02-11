package Session;

import model.dto.UserDto;

public class Session {
    public static UserDto loginUser;

    /**
     *
     * @return 현재 로그인한 유저 객체 반환
     */
    public static UserDto getLoginUser() {
        return loginUser;
    }

    /**
     *
     * @param loginUser 유저 객체
     * @apiNote 로그인했을 때 호출해서 세션에 저장
     */
    public static void setLoginUser(UserDto loginUser) {
        Session.loginUser = loginUser;
    }
    public static void logout(){
        loginUser = null;
    }
    public static boolean isLogin(){
        return loginUser != null;
    }
}
