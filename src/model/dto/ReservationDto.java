package model.dto;

public class ReservationDto {
    private int no;
    private int user_no;
    private String seat_code;
    private String reservedAt;
    
    // 조인용 추가 필드
    private String userName; 
    private String userId;

    public ReservationDto() { }

    public ReservationDto(int no, int user_no, String seat_code, String reservedAt) {
        this.no = no;
        this.user_no = user_no;
        this.seat_code = seat_code;
        this.reservedAt = reservedAt;
    }

    public ReservationDto(int no, int user_no, String seat_code, String reservedAt, String userName, String userId) {
        this.no = no;
        this.user_no = user_no;
        this.seat_code = seat_code;
        this.reservedAt = reservedAt;
        this.userName = userName;
        this.userId = userId;
    }

    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }
    public int getUser_no() { return user_no; }
    public void setUser_no(int user_no) { this.user_no = user_no; }
    public String getSeat_code() { return seat_code; }
    public void setSeat_code(String seat_code) { this.seat_code = seat_code; }
    public String getReservedAt() { return reservedAt; }
    public void setReservedAt(String reservedAt) { this.reservedAt = reservedAt; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "no=" + no +
                ", user_no=" + user_no +
                ", seat_code='" + seat_code + '\'' +
                ", reservedAt='" + reservedAt + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
