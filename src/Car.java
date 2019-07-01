public class Car {
    private String brand;
    private String type;
    private double price;
    private int owner_id;

    public Car(String brand, String type, double price, int owner_id) {
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.owner_id = owner_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String toString(){
        return String.format("(%s, %s, %s, %s)", brand, type, price, owner_id);
    }
}
