package vendingMachine.challenge;

public class Coin {
    private double value;
    private int quantity;

    public Coin(double value, int quantity){
        this.value = value;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "coin: "+ value;
    }
}
