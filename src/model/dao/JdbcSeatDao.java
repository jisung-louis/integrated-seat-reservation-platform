package model.dao;

public class JdbcSeatDao implements SeatDao {
    // [1] 싱글톤
    private JdbcSeatDao(){}
    private static JdbcSeatDao instance = new JdbcSeatDao();
    public static JdbcSeatDao getInstance() {
        return instance;
    }
}
