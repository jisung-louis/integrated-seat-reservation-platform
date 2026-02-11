package model.dto;

public class StoreDto {
    private int no;
    private int owner_no;
    private String name;
    private String category;
    private String address;
    private String contact;
    private String email;
    private String bh_weekdays;
    private String bh_saturday;
    private String bh_sunday;
    private int status;

    public StoreDto() {
    }

    public StoreDto(int no, int owner_no, String name, String category, String address, String contact, String email, String bh_weekdays, String bh_saturday, String bh_sunday, int status) {
        this.no = no;
        this.owner_no = owner_no;
        this.name = name;
        this.category = category;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.bh_weekdays = bh_weekdays;
        this.bh_saturday = bh_saturday;
        this.bh_sunday = bh_sunday;
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getOwner_no() {
        return owner_no;
    }

    public void setOwner_no(int owner_no) {
        this.owner_no = owner_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBh_weekdays() {
        return bh_weekdays;
    }

    public void setBh_weekdays(String bh_weekdays) {
        this.bh_weekdays = bh_weekdays;
    }

    public String getBh_saturday() {
        return bh_saturday;
    }

    public void setBh_saturday(String bh_saturday) {
        this.bh_saturday = bh_saturday;
    }

    public String getBh_sunday() {
        return bh_sunday;
    }

    public void setBh_sunday(String bh_sunday) {
        this.bh_sunday = bh_sunday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
                ", status=" + status +
                '}';
    }
}

