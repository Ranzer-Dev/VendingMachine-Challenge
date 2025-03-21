package vendingMachine.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class VendingMachine {
    private Checkout checkout;
    private List<Product> productsOfVendingMachine = new ArrayList<>();
    private ServicePerson servicePerson;

    VendingMachine(){
        this.checkout = new Checkout();
        this.servicePerson = new ServicePerson();

        productsOfVendingMachine.add(new Product("Water", 0.65, 0));
        productsOfVendingMachine.add(new Product("Juice", 1, 0));
        productsOfVendingMachine.add(new Product("Soda", 1.50, 0));
    }

    public void replenishStock(int product, int quantity){

        Scanner askAccessKey = new Scanner(System.in);
        System.out.println("Please type the Acess Key");

        if(servicePerson.verifyAccess(askAccessKey.nextInt())) {
            System.out.println("replenishing stock");
            productsOfVendingMachine.get(product).setQuantity(quantity);
        } else {
            System.out.println("access denied!");
        }
    }

    public void replenishChange(double value, int amount){

        Scanner askAccessKey = new Scanner(System.in);
        System.out.println("Please type the Acess Key");

        if(servicePerson.verifyAccess(askAccessKey.nextInt())) {
            System.out.println("replenishing change");
            checkout.insertCoinQuantity(value,amount);
        } else {
            System.out.println("access denied!");
        }
    }

    public void insertCoin(double value, int quantity) {

        if (checkout.isValidCoin(value)) {
            checkout.insertCoinPreOrder(value, quantity);
        } else {
            System.out.println("Invalid coin, coins accepted:");
            checkout.acceptedCoins();
        }

    }


    private void removeCoinsPreOrder(){
        checkout.returnCoinPreOrder();
        System.out.println("Coins returned. Cancelling purchase."+checkout.getCoinInPreOrder());
    }

    public List<Product> getProductsOfVendingMachine() {
        return productsOfVendingMachine;
    }

    public void start() {
        Scanner readLine = new Scanner(System.in).useLocale(Locale.US);
        int itensOutOfStock = 0;

        System.out.println("""
                
                Itens in this machine:
                \nnumber/name/price/quantity""");

        for (int i = 0; i < productsOfVendingMachine.size(); i++) {
            if (productsOfVendingMachine.get(i).getQuantity() > 0) {
                System.out.println(i + ": " + productsOfVendingMachine.get(i).getName() + "___" + productsOfVendingMachine.get(i).getPrice() + "$ ___" + productsOfVendingMachine.get(i).getQuantity());
            }else{
                itensOutOfStock = itensOutOfStock + 1;
                if (itensOutOfStock >= productsOfVendingMachine.size()){
                    System.out.println("Under maintenance");
                    replenishStock(0,1);
                }
            }
        }
        System.out.println("\ntype the number for chose your item");
        while (!readLine.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number.");
            readLine.next();
        }
        int chose = readLine.nextInt();
        while (chose < 0 || chose >= productsOfVendingMachine.size() || productsOfVendingMachine.get(chose).getQuantity() <= 0) {
            if (chose < 0 || chose >= productsOfVendingMachine.size()) {
                System.out.println("Invalid number! Please enter a valid item number.");
            } else if (productsOfVendingMachine.get(chose).getQuantity() <= 0) {
                System.out.println("product is out of stock, chose another one");
            }

            while (!readLine.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number.");
                readLine.next();
            }

            chose = readLine.nextInt();
        }
        Product selectedProduct = productsOfVendingMachine.get(chose);

        System.out.println("\nYou've selected:"+selectedProduct);

        System.out.println("this machine only accept, ");
        checkout.acceptedCoins();
        System.out.println("please type one coin per time or 0 for return all inserted");

        while(checkout.getTotalInPreOrder() < selectedProduct.getPrice()) {
            if (readLine.hasNextDouble()) {
                double coin = readLine.nextDouble();
                if (coin == 0) {
                    removeCoinsPreOrder();
                    return;
                }
                int quantity = 1;
                insertCoin(coin, quantity);
                System.out.println("Total amount: $" + checkout.getTotalInPreOrder());
                System.out.println(checkout.getCoinInPreOrder());

            } else{
                System.out.println("Invalid input, try again");
                readLine.next();
            }
        }
        if (checkout.getTotalInPreOrder() == selectedProduct.getPrice()) {
            System.out.println("payment done, releasing you item");
            for (int i = 0; i < checkout.getCoinInPreOrder().size(); i++) {
                checkout.insertCoinQuantity(checkout.getCoinInPreOrder().get(i).getValue(), checkout.getCoinInPreOrder().get(i).getQuantity());
            }
            productsOfVendingMachine.get(chose).setQuantity(productsOfVendingMachine.get(chose).getQuantity() - 1);
        } else if (checkout.getTotalInPreOrder() > selectedProduct.getPrice()) {
            double change = checkout.getTotalInPreOrder() - selectedProduct.getPrice();
            productsOfVendingMachine.get(chose).setQuantity(productsOfVendingMachine.get(chose).getQuantity() - 1);
            while(change > 0.00) {
                for (int i = checkout.getCoinList().size()-1; i >= 0 ; i--) {
                    if (checkout.getCoinList().get(i).getValue() <= change && checkout.getCoinList().get(i).getQuantity() > 0){
                        checkout.getCoinList().get(i).setQuantity(checkout.getCoinList().get(i).getQuantity()-1);
                        change = change - checkout.getCoinList().get(i).getValue();
                        System.out.println(change);
                            for (int j = 0; j < checkout.getCoinList().size(); j++) {
                                System.out.println(checkout.getCoinList().get(j).getValue() + " " +checkout.getCoinList().get(j).getQuantity());
                        }
                    }else if (i == 0 && checkout.getCoinList().get(i).getQuantity() == 0){
                        change = 0;
                        System.out.println("this machine don't have money, calling the Service Person");
                        replenishChange(0.25,1);
                    }
                }
            }
        }
    }
}