package model.dao;

import model.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao extends DBConnection implements UserDao{
    // [0] 연동 인터페이스 선언
    private Connection conn;
    // [1] 싱글톤
    private JdbcUserDao(){ conn = connect(); }
    private static JdbcUserDao instance = new JdbcUserDao();
    public static JdbcUserDao getInstance() {
        return instance;
    }

    @Override
    public UserDto login(String id, String password){
        try {
            String sql = "select * from user where id = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int user_no = rs.getInt("no");
                String name = rs.getString("name");
                boolean isAdmin = rs.getBoolean("isAdmin");
                return new UserDto(user_no,id,password,name,isAdmin);
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제 발생 : " + e);
        }
        return null;
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

    @Override
    public boolean update(UserDto admin) {
        try {
            String sql = "UPDATE user SET id = ?, password = ?, name = ? WHERE no = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getId());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getName());
            ps.setInt(4, admin.getNo());
            return ps.executeUpdate()==1;
        } catch (Exception e) {
            System.out.println("[DB 오류] 관리자 정보 수정 실패: " + e.getMessage());
        }
        return false;
    }
}
