package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private int code;
    private Date createDate;
    private String type;
    public List<ItemReceipt> items;
    
    public Receipt(int code, String type) {
        this.code = ++code;
        long millis = System.currentTimeMillis();
        java.sql.Date createDateSystem = new java.sql.Date(millis);
        this.createDate = createDateSystem;
        this.type = type;
        this.items = new ArrayList<>();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ItemReceipt> getItems() {
        return items;
    }

    public void setItems(List<ItemReceipt> items) {
        this.items = items;
    }

    public void addItem(ItemReceipt item) {
        try {
            items.add(item);
        } catch (Exception e) {

        }
    }

    @Override
    public String toString() {
        return "Receipt{" + "code=" + code + ", createDate=" + createDate + ", type=" + type + ", items=" + items + '}';
    }
    

}
