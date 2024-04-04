package model;

import java.sql.Date;

public class ItemReceipt {

    private String productCode;
    private int quantity;
    private Date manufacturingDate;
    private Date expirationDate;

    public ItemReceipt() {
    }

    public ItemReceipt(String productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.manufacturingDate = null;
        this.expirationDate = null;
    }

    public ItemReceipt(String productCode, int quantity, Date manufacturingDate, Date expirationDate) {
        this.productCode = productCode;
        this.quantity = quantity;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getManufacturingDate() {
        return manufacturingDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "ItemReceipt{" + "productCode=" + productCode + ", quantity=" + quantity + ", manufacturingDate=" + manufacturingDate + ", expirationDate=" + expirationDate + '}';
    }

    
    
}
