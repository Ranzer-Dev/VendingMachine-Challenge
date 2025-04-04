package vendingMachine.challenge;

import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private List<Coin> coinList;
    private List<Coin> totalInPreOrder;

    public Checkout() {
        this.coinList = new ArrayList<>();
        this.totalInPreOrder = new ArrayList<>();

        coinList.add(new Coin(0.05, 10));
        coinList.add(new Coin(0.10, 10));
        coinList.add(new Coin(0.25, 10));
        coinList.add(new Coin(0.50, 10));
        coinList.add(new Coin(1.00, 10));
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public double getTotalInPreOrder() {
        double total = 0;
        for (Coin coin: totalInPreOrder){
            total = total + coin.getValue();
        }
        return total;
    }

    public List<Coin> getCoinInPreOrder() {
        return totalInPreOrder;
    }

    public boolean isValidCoin(double value) {
        for (Coin coin : coinList) {
            if (coin.getValue() == value) {
                return true;
            }
        }
        return false;
    }

    public void insertCoinInPreOrder(double value, int amount) {
        for (Coin coin : coinList) {
            if (coin.getValue() == value) {
                totalInPreOrder.add(new Coin(value,amount));
                return;
            }
        }
        System.out.println("Coin not found: " + value);
    }

    public void returnCoinInPreOrder() {
        totalInPreOrder.clear();
    }

    public void insertCoinQuantity(double value, int amount) {
        for (Coin coin : coinList) {
            if (coin.getValue() == value) {
                coin.setQuantity(coin.getQuantity() + amount);
                return;
            }
        }
        System.out.println("Coin not found: " + value);
    }

    public void returnCoinQuantity(double value, int amount) {
        for (Coin coin : coinList) {
            if (coin.getValue() == value) {
                coin.setQuantity(coin.getQuantity() - amount);
                return;
            }
        }
        System.out.println("Coin not found: " + value);
    }

    public void acceptedCoins(){
        System.out.println(coinList.toString());
    }

}
