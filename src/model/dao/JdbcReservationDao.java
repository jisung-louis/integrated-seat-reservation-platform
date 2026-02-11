package model.dao;

public class JdbcReservationDao implements ReservationDao {
    // [1] 싱글톤
    private JdbcReservationDao(){}
    private static JdbcReservationDao instance = new JdbcReservationDao();
    public static JdbcReservationDao getInstance() {
        return instance;
    }
}
