package model.dto;

public class SeatDto {
    private String code;
    private int store_no;
    private String colCode;
    private String rowNum;
    private int status;

    public SeatDto() {}
    public SeatDto(String code, int store_no, String colCode, String rowNum, int status) {
        this.code = code;
        this.store_no = store_no;
        this.colCode = colCode;
        this.rowNum = rowNum;
        this.status = status;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getStore_no() { return store_no; }
    public void setStore_no(int store_no) { this.store_no = store_no; }
    public String getColCode() { return colCode; }
    public void setColCode(String colCode) { this.colCode = colCode; }
    public String getRowNum() { return rowNum; }
    public void setRowNum(String rowNum) { this.rowNum = rowNum; }
    public int getStatus() { return status;}
    public void setStatus(int status) { this.status = status; }

    @Override
    public String toString() {
        return "SeatDto{" +
                "code='" + code + '\'' +
                ", store_no=" + store_no +
                ", colCode='" + colCode + '\'' +
                ", rowNum='" + rowNum + '\'' +
                ", status=" + status +
                '}';
    }
}
