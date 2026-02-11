package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Dao들의 DB 연결 부분을 분리한 클래스
    public static final String url = "jdbc:mysql://localhost:3306/project";
    public static final String user = "root";
    public static final String password = "1234";

    /**
     * 사용법 : Connection conn을 선언해두고, DAO의 싱글톤 객체 생성자에서 conn 변수에 DBConnection.connect()를 호출/대입하면 됩니다.
     */
    public static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            System.out.printf("[경고] DB랑 통신하다가 문제가 생겼어요.\n%s\n",e);
        } catch (ClassNotFoundException e) {
            System.out.printf("[경고] MySQL 드라이버를 못 찾았어요.\n%s\n",e);
        }
        return null;
    }
}
