package model;

public class DailyProduct extends Product{
    private String type;

    public DailyProduct() {
    }

    public DailyProduct(String type) {
        this.type = type;
    }

    public DailyProduct(String code, String name, String type) {
        super(code, name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString() + "DailyProduct{" + "type=" + type + '}';
    }
    
    
}
