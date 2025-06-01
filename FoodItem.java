public class FoodItem {
    private String name;
    private int quantity;
    private double price;
    private String cuisine;
    private String expiry;

    public FoodItem(String name, int quantity, double price, String cuisine, String expiry) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.cuisine = cuisine;
        this.expiry = expiry;
    }

    public double getCost() {
        return quantity * price;
    }

    
    public String toString() {
        return "Name: " + name +
               ", quantity: " + quantity +
               ", Price: " + price +
               ", Cuisine: " + cuisine +
               ", Expiry: " + expiry;
    }
}
