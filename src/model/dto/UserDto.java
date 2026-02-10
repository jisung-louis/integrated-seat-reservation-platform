package model.dto;

public class UserDto {
    private int no;
    private int owner_no;
    private String name;
    private String category;
    private String address;
    private String email;
    private String bh_weekdays;
    private String bh_saturday;
    private String bh_sunday;
    private int staus;

    public UserDto() {
    }

    public UserDto(int no, int owner_no, String name, String category, String address, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int staus) {
        this.no = no;
        this.owner_no = owner_no;
        this.name = name;
        this.category = category;
        this.address = address;
        this.email = email;
        this.bh_weekdays = bh_weekdays;
        this.bh_saturday = bh_saturday;
        this.bh_sunday = bh_sunday;
        this.staus = staus;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setOwner_no(int owner_no) {
        this.owner_no = owner_no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBh_weekdays(String bh_weekdays) {
        this.bh_weekdays = bh_weekdays;
    }

    public void setBh_saturday(String bh_saturday) {
        this.bh_saturday = bh_saturday;
    }

    public void setBh_sunday(String bh_sunday) {
        this.bh_sunday = bh_sunday;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public int getNo() {
        return no;
    }

    public int getOwner_no() {
        return owner_no;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getBh_weekdays() {
        return bh_weekdays;
    }

    public String getBh_saturday() {
        return bh_saturday;
    }

    public String getBh_sunday() {
        return bh_sunday;
    }

    public int getStaus() {
        return staus;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "no=" + no +
                ", owner_no=" + owner_no +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", bh_weekdays='" + bh_weekdays + '\'' +
                ", bh_saturday='" + bh_saturday + '\'' +
                ", bh_sunday='" + bh_sunday + '\'' +
                ", staus=" + staus +
                '}';
    }
}

