package vendingMachine.challenge;

public class Product {
    private final double price;
    private final String name;
    private int quantity;

    Product(String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getName(){
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "\n"+name +" price: "+ price +" quantity: "+ quantity+"\n";
    }
}
