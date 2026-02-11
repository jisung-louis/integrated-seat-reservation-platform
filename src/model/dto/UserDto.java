package model.dto;

public class UserDto {

    private int no;
    private String id;
    private String password;
    private String name;
    private boolean isAdmin;

    public UserDto() {};
    public UserDto(int no, String id, String password, String name, boolean isAdmin) {
        this.no = no;
        this.id = id;
        this.password = password;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public int getNo() {
        return no;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "StoreDto{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
