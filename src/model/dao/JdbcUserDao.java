package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao{
    // [0] 연동 인터페이스 선언
    private Connection conn;
    // [1] 싱글톤
    private JdbcUserDao(){ conn = DBConnection.connect(); }
    private static JdbcUserDao instance = new JdbcUserDao();
    public static JdbcUserDao getInstance() {
        return instance;
    }

    @Override
    public boolean login(String id, String password){
        try {
            String sql = "select * from user where id = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // 결과가 있으면 true, 없으면 false
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }

    @Override
    public boolean signup(String id, String password, String name, boolean isAdmin){
        try {
            String sql = "insert into user values(null, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setBoolean(4,isAdmin);
            return ps.executeUpdate() == 1; // executeUpdate를 호출하고 그 값이 1이면 true, 아니면 false 반환
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return false;
    }
}
