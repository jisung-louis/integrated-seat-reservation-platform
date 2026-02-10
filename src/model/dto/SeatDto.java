package model.dto;

public class SeatDto {
    private String code;
    private int store_no;
    private String rowCode;
    private String colNum;
    private int status;

    public SeatDto() {}
    public SeatDto(String code, int store_no, String rowCode, String colNum, int status) {
        this.code = code;
        this.store_no = store_no;
        this.rowCode = rowCode;
        this.colNum = colNum;
        this.status = status;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getStore_no() { return store_no; }
    public void setStore_no(int store_no) { this.store_no = store_no; }
    public String getRowCode() { return rowCode; }
    public void setRowCode(String rowCode) { this.rowCode = rowCode; }
    public String getColNum() { return colNum; }
    public void setColNum(String colNum) { this.colNum = colNum; }
    public int getStatus() { return status;}
    public void setStatus(int status) { this.status = status; }

    @Override
    public String toString() {
        return "SeatDto{" +
                "code='" + code + '\'' +
                ", store_no=" + store_no +
                ", rowCode='" + rowCode + '\'' +
                ", colNum='" + colNum + '\'' +
                ", status=" + status +
                '}';
    }
}
