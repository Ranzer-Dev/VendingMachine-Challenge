package vendingMachine.challenge;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Product> drinks = new ArrayList<>();
        drinks.add(new Product("Water",0.65,0));
        drinks.add(new Product("Juice",1.00,5));
        drinks.add(new Product("Soda",1.50,0));

        VendingMachine drinkMachine = new VendingMachine(drinks);

        drinkMachine.start();

    }
}
