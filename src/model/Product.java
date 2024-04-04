package model;

public class Product {

    private String code;
    private String name;
    private int availableQuantity=0;

    public Product() {
    }

    public Product(String code, String name) {
        this.code = code;
        this.name = name;
        this.availableQuantity = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", availableQuantity=" + availableQuantity + '}';
    }

  

    
}
