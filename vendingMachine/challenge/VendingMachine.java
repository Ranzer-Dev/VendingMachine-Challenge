package vendingMachine.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class VendingMachine{
    private Checkout checkout;
    private List<Product> productsOfVendingMachine;
    private ServicePerson servicePerson;
    private boolean underMaintenance = false;

    VendingMachine(List<Product> productsList){
        this.checkout = new Checkout();
        this.servicePerson = new ServicePerson();
        this.productsOfVendingMachine = new ArrayList<>(productsList);
    }

    public void replenishStock(){

        Scanner readLine = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Replenishing machine");

        while (true) {

            System.out.println("Type 0 to cancel or 1 to continue Replenish");
            int exitOrContinue = getValidIntInput(readLine);
            while (exitOrContinue != 0 && exitOrContinue != 1) {
                System.out.println("invalid enter");
                exitOrContinue = readLine.nextInt();
            }
            if (exitOrContinue == 0) {
                break;
            } else {

                System.out.println("Please type the Acess Key");
                int accesKey = getValidIntInput(readLine);;
                if (servicePerson.verifyAccess(accesKey)) {
                    displayProducts();
                    System.out.println("type the number of product and quantity, in this format: 1 10");
                    int product = getValidIntInput(readLine);
                    while (product < 0 || product >= productsOfVendingMachine.size()) {
                        System.out.println("Invalid Product, re-type");
                        if (readLine.hasNextInt()) {
                            product = readLine.nextInt();
                        } else {
                            readLine.next();
                        }
                    }
                    int quantity = getValidIntInput(readLine);
                    productsOfVendingMachine.get(product).setQuantity(productsOfVendingMachine.get(product).getQuantity()+quantity);
                    displayProducts();
                } else {
                    System.out.println("access denied!");
                }
            }
        }
    }

    public void replenishChange() {
        Scanner readLine = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Replenishing machine");

        while (true) {

            System.out.println("Type 0 to cancel or 1 to continue Replenish");

            int exitOrContinue = getValidIntInput(readLine);
            while (exitOrContinue != 0 && exitOrContinue != 1) {
                System.out.println("invalid enter");
                exitOrContinue = readLine.nextInt();
            }
            if (exitOrContinue == 0) {
                break;
            } else {
                System.out.println("Please type the Acess Key");
                int accesKey = getValidIntInput(readLine);
                if (servicePerson.verifyAccess(accesKey)) {
                    displayChange();
                    System.out.println("type the value of coin and quantity, in this format: 0.25 10");
                    double coinValue = getValidDoubleInput(readLine);
                    while (!checkout.isValidCoin(coinValue)) {
                        System.out.println("Invalid coin value, re-type");
                        if (readLine.hasNextDouble()) {
                            coinValue = readLine.nextDouble();
                        } else {
                            readLine.next();
                        }
                    }
                    int amount = getValidIntInput(readLine);;
                    System.out.println("replenishing change");
                    checkout.insertCoinQuantity(coinValue, amount);
                    System.out.println("the actual change is:");
                    displayChange();
                } else {
                    System.out.println("access denied!");
                }
            }
        }
    }

    private int getValidIntInput(Scanner readLine) {
        while (!readLine.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number.");
            readLine.next();
        }
        return readLine.nextInt();
    }

    private double getValidDoubleInput(Scanner readLine) {
        while (!readLine.hasNextDouble()) {
            System.out.println("Invalid input! Please enter a valid number.");
            readLine.next();
        }
        return readLine.nextDouble();
    }

    private void displayProducts() {
        for (int i = 0; i < productsOfVendingMachine.size(); i++) {
            System.out.println(i + ": " + productsOfVendingMachine.get(i).getName() + "___" + productsOfVendingMachine.get(i).getPrice() + "$ ___" + productsOfVendingMachine.get(i).getQuantity());
        }
    }

    private void  displayChange(){
        for (int i = 0; i < checkout.getCoinList().size(); i++) {
            System.out.println(i + ": " + checkout.getCoinList().get(i).getValue() + " " + checkout.getCoinList().get(i).getQuantity());
        }
    }

    public void insertCoin(double value, int quantity) {

        if (checkout.isValidCoin(value)) {
            checkout.insertCoinInPreOrder(value, quantity);
        } else {
            System.out.println("Invalid coin, coins accepted:");
            checkout.acceptedCoins();
        }

    }


    private void removeCoinsPreOrder(){
        checkout.returnCoinInPreOrder();
        System.out.println("Coins returned. Cancelling purchase."+checkout.getCoinInPreOrder());
    }

    public List<Product> getProductsOfVendingMachine() {
        return productsOfVendingMachine;
    }

    public void start() {
        Scanner readLine = new Scanner(System.in).useLocale(Locale.US);

        while (true) {
            System.out.println("""
                    
                    type: 0 for mainitence/replenish
                    type: 1 for see Change
                    type: 2 for start vending
                    type: 3 to turn vending machine off""");
            while (!readLine.hasNextInt()) {
                System.out.println("invalid, please select one: 0 mainitence, 1 for see Change or 2 for select products");
                readLine.next();
            }

            int interfaceChoose = getValidIntInput(readLine);;

            if (interfaceChoose == 0) {
                System.out.println("""
                        
                        type: 1 for replenish change
                        type: 2 for replenish products""");

                int maintenanceChoice = getValidIntInput(readLine);
                while (maintenanceChoice != 1 && maintenanceChoice != 2) {
                    System.out.println("Invalid input! Type 1 or 2.");
                    if (readLine.hasNextInt()){
                        maintenanceChoice = readLine.nextInt();
                    }else{
                        readLine.next();
                    }
                }
                if (maintenanceChoice == 1) {
                    replenishChange();
                } else {
                    replenishStock();
                }
            } else if (interfaceChoose == 1) {
                displayChange();;
            } else if (interfaceChoose == 2) {
                underMaintenance = false;

                while (!underMaintenance) {

                    int itensOutOfStock = 0;
                    int enterMaintenance = productsOfVendingMachine.size();

                    System.out.println("""
                            
                            Itens in this machine:
                            \nnumber/name/price/quantity""");

                    for (int i = 0; i < productsOfVendingMachine.size(); i++) {
                        if (productsOfVendingMachine.get(i).getQuantity() > 0) {
                            System.out.println(i + ": " + productsOfVendingMachine.get(i).getName() + "___" + productsOfVendingMachine.get(i).getPrice() + "$ ___" + productsOfVendingMachine.get(i).getQuantity());
                        }
                    }
                    for (Product product : productsOfVendingMachine) {
                        if (product.getQuantity() == 0) {
                            itensOutOfStock++;
                        }
                    }

                    if (itensOutOfStock >= productsOfVendingMachine.size()) {
                        System.out.println("Under maintenance");
                        replenishStock();
                    }

                    System.out.println("\ntype the number for chose your item or type: " + enterMaintenance + " for maintenance");
                    int chose = getValidIntInput(readLine);;
                    if (chose == enterMaintenance) {
                        underMaintenance = true;
                    } else {
                        while (chose < 0 || chose >= productsOfVendingMachine.size() || productsOfVendingMachine.get(chose).getQuantity() <= 0) {
                            if (chose < 0 || chose >= productsOfVendingMachine.size()) {
                                System.out.println("Invalid number! Please enter a valid item number.");
                            } else if (productsOfVendingMachine.get(chose).getQuantity() <= 0) {
                                if (itensOutOfStock >= productsOfVendingMachine.size()) {
                                    System.out.println("Under maintenance");
                                    replenishStock();
                                } else {
                                    System.out.println("product is out of stock, chose another one");
                                }

                            }

                            chose = getValidIntInput(readLine);;
                        }
                        Product selectedProduct = productsOfVendingMachine.get(chose);

                        System.out.println("\nYou've selected:" + selectedProduct);

                        System.out.println("this machine only accept, ");
                        checkout.acceptedCoins();
                        System.out.println("please type one coin per time or 0 for return all inserted");

                        while (checkout.getTotalInPreOrder() < selectedProduct.getPrice()) {
                            if (readLine.hasNextDouble()) {
                                double coin = readLine.nextDouble();
                                if (coin == 0) {
                                    removeCoinsPreOrder();
                                    return;
                                }
                                int quantity = 1;
                                insertCoin(coin, quantity);
                                System.out.println("Total amount: $" + checkout.getTotalInPreOrder());

                            } else {
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
                            checkout.returnCoinInPreOrder();
                        } else if (checkout.getTotalInPreOrder() > selectedProduct.getPrice()) {
                            double change = checkout.getTotalInPreOrder() - selectedProduct.getPrice();
                            productsOfVendingMachine.get(chose).setQuantity(productsOfVendingMachine.get(chose).getQuantity() - 1);
                            checkout.returnCoinInPreOrder();
                            System.out.println("\nyour change is: "+change+"\n");
                            while (change > 0.05) {
                                for (int i = checkout.getCoinList().size() - 1; i >= 0; i--) {
                                    if (checkout.getCoinList().get(i).getValue() <= change && checkout.getCoinList().get(i).getQuantity() > 0) {
                                        System.out.println("returning coin: "+checkout.getCoinList().get(i).getValue());
                                        checkout.getCoinList().get(i).setQuantity(checkout.getCoinList().get(i).getQuantity() - 1);
                                        change = change - checkout.getCoinList().get(i).getValue();
                                    } else if (i == 0 && checkout.getCoinList().get(i).getQuantity() == 0) {
                                        change = 0;
                                        System.out.println("this machine don't have money, calling the Service Person");
                                        replenishChange();
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (interfaceChoose == 3) {
                System.out.println("Turning off the vending machine");
                break;
            }
        }
    }
}