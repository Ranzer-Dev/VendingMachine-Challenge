# Vending Machine Project

## Overview

This project simulates a vending machine with the following features:

- Users can insert coins and select products.
- The machine has a temporary coin storage that stores coins before finalizing the purchase.
- If the user inserts more coins than necessary, the machine will return change.
- In case the machine runs out of stock, a service person can replenish the items or coins.
- The vending machine can be turned off by the user.

## Features

- **Product Selection**: Choose from available products and view their prices and quantities.
- **Coin Management**: Insert coins, receive change, or cancel the transaction.
- **Maintenance**: Replenish products and coins with an access key for the service person.
- **Shutdown**: The vending machine can be turned off when no longer needed.

## How It Works

The machine accepts coins, allows users to select products, and processes payments. It has two separate "storages":

- **Temporary Coin Storage**: Holds coins inserted by the user until the purchase is completed or canceled.
- **Main Coin Storage**: Holds available coins for change, which can be replenished by the service person.

If the machine runs out of stock or change, it will prompt the service person to refill.

## How to Run

1. Clone the repository:  
   `git clone https://github.com/yourusername/vending-machine.git`
   
2. Navigate to the project directory:  
   `cd vending-machine`

3. Open the project using your preferred IDE

4. Find and run the main class that contains the main(String[] args) method.

5. Enjoy simulating your vending machine experience!

## Technologies Used

- Java
- InteliJ IDEA Community Edition (for development)

## License

This project is licensed under the MIT License
