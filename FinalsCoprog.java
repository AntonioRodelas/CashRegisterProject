    import java.util.*;
    import java.util.regex.*;
    import java.io.*;
    import java.util.Date;
    import java.text.SimpleDateFormat;

    public class FinalsCoprog {

        static Scanner scan = new Scanner(System.in);
        static ArrayList<String> products = new ArrayList<>();
        static ArrayList<Double> prices = new ArrayList<>();
        static ArrayList<Integer> quantities = new ArrayList<>();
        static ArrayList<String> passwords = new ArrayList<>();
        static ArrayList<String> usernames = new ArrayList<>();
        static String loggedInUser  = "";

        public static void main(String[] args) {
            boolean isLoggedIn = false;

            while (true) {
                System.out.println("====== MAIN MENU ======");
                System.out.println("1. Sign Up");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = scan.nextLine();

                if (choice.equals("1")) {
                    signUp();
                } else if (choice.equals("2")) {
                    isLoggedIn = login();
                    if (isLoggedIn) {
                        runCashRegister();
                    }
                } else if (choice.equals("3")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.\n");
                }
            }
        }

        static void signUp() {
            Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
            Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,20}$");

            System.out.println("\n----- SIGN UP -----");

            String username;
            while (true) {
                System.out.print("Enter a username (or type 'cancel' to go back): ");
                username = scan.nextLine();
                if (username.equalsIgnoreCase("cancel")) {
                    return; 
                }

                if (!usernamePattern.matcher(username).matches()) {
                    System.out.println("Invalid username format. It must be alphanumeric and 5–15 characters long.");
                    continue;
                }

                if (isUsernameTaken(username)) {
                    System.out.println("Username already taken. Please choose another.");
                    continue;
                }

                break;
            }

            while (true) {
                System.out.print("Enter a password (or type 'cancel' to go back): ");
                String password = scan.nextLine();
                if (password.equalsIgnoreCase("cancel")) {
                    return; 
                }

                if (!passwordPattern.matcher(password).matches()) {
                    System.out.println("Invalid password format. It must:");
                    System.out.println("- Be 8–20 characters");
                    System.out.println("- Contain at least 1 uppercase letter");
                    System.out.println("- Contain at least 1 number");
                    continue;
                }

                usernames.add(username);
                passwords.add(password);
                System.out.println("Signup successful!\n");
                break;
            }
        }

        static boolean isUsernameTaken(String username) {
            return usernames.contains(username);
        }

        static boolean login() {
            System.out.println("\n----- LOGIN -----");
            while (true) {
                System.out.print("Username (or type 'cancel' to go back): ");
                String enteredUsername = scan.nextLine();
                if (enteredUsername.equalsIgnoreCase("cancel")) {
                    return false;
                }
                System.out.print("Password: ");
                String enteredPassword = scan.nextLine();

                if (usernames.contains(enteredUsername) && passwords.get(usernames.indexOf(enteredUsername)).equals(enteredPassword)) {
                    loggedInUser  = enteredUsername;
                    System.out.println("Login successful!\n");
                    return true;
                } else {
                    System.out.println("Incorrect credentials. Try again.\n");
                }
            }
        }

        static void runCashRegister() {
            ArrayList<Integer> quantity = new ArrayList<>();
            ArrayList<String> product = new ArrayList<>();
            ArrayList<Double> price = new ArrayList<>();
            boolean isRunning = true;

            String[] mcdoProducts = {"McChicken", "Fries", "Softdrink"};
            double[] mcdoPrices = {99.0, 54.0, 39.0};

            for (int i = 0; i < mcdoProducts.length; i++) {
                product.add(mcdoProducts[i]);
                price.add(mcdoPrices[i]);
                quantity.add(0);
            }

            while (isRunning) {
                System.out.println("-------------------------------------");
                System.out.println("-                                   -");
                System.out.println("-       Welcome to McDonalds!       -");
                System.out.println("-                                   -");
                System.out.println("-------------------------------------");
                System.out.println("1. Add or Buy items");
                System.out.println("2. Display items");
                System.out.println("3. Remove items");
                System.out.println("4. Edit Order");
                System.out.println("5. Proceed to payment");
                System.out.println("6. Exit");
                System.out.println("7. Logout");
                System.out.print("Enter choice: ");
                String input = scan.nextLine();

                switch (input) {
                    case "1":
                        addOrBuyItem(product, quantity, price);
                        break;

                    case "2":
                        displayItems(product, quantity, price);
                        break;

                    case "3":
                        removeItem(product, quantity, price);
                        break;

                    case "4":
                        updateItemQuantity(product, quantity);
                        break;

                    case "5":
                        processPayment(product, quantity, price);
                        break;

                    case "6":
                        System.out.println("Thank you for using the cash register!");
                        isRunning = false;
                        break;

                    case "7":
                        System.out.println("Logging out...\n");
                        return;

                    default:
                        System.out.println("Not in the given choices.\n");
                }
            }
        }

        static void addOrBuyItem(ArrayList<String> product, ArrayList<Integer> quantity, ArrayList<Double> price) {
            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Buy existing item");
                System.out.println("2. Add new item");
                System.out.println("3. Return to menu");
                System.out.print("Enter your choice (or type 'cancel' to go back): ");
                String choice = scan.nextLine();

                if (choice.equalsIgnoreCase("cancel")) {
                    return; 
                }

                if (choice.equals("1")) {
                    if (product.isEmpty()) {
                        System.out.println("No products available to buy.\n");
                        continue;
                    }
                    System.out.println("\nAvailable products:");
                    for (int i = 0; i < product.size(); i++) {
                        System.out.println((i + 1) + ". " + product.get(i) + " - Php " + price.get(i));
                    }

                    int selectedIndex = -1;
                    while (true) {
                        System.out.print("Select product number to buy (or type 'cancel' to go back): ");
                        String input = scan.nextLine();
                        if (input.equalsIgnoreCase("cancel")) {
                            return; 
                        }
                        try {
                            selectedIndex = Integer.parseInt(input) - 1;
                            if (selectedIndex >= 0 && selectedIndex < product.size()) {
                                break;
                            } else {
                                System.out.println("Invalid product number.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    int buyQuantity = 0;
                    while (true) {
                        System.out.print("Enter quantity to buy (or type 'cancel' to go back): ");
                        String qtyStr = scan.nextLine();
                        if (qtyStr.equalsIgnoreCase("cancel")) {
                            return; 
                        }
                        try {
                            buyQuantity = Integer.parseInt(qtyStr);
                            if (buyQuantity > 0) {
                                break;
                            } else {
                                System.out.println("Quantity must be greater than zero.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    quantity.set(selectedIndex, quantity.get(selectedIndex) + buyQuantity);
                    System.out.println("Added " + buyQuantity + " " + product.get(selectedIndex) + "(s) to your purchase.\n");

                } else if (choice.equals("2")) {
                    System.out.print("Enter new product name (or type 'cancel' to go back): ");
                    String newProduct = scan.nextLine();
                    if (newProduct.equalsIgnoreCase("cancel")) {
                        return; 
                    }
                    double newPrice = 0.0;

                    while (true) {
                        System.out.print("Enter price for " + newProduct + " (or type 'cancel' to go back): ");
                        String priceStr = scan.nextLine();
                        if (priceStr.equalsIgnoreCase("cancel")) {
                            return; 
                        }
                        try {
                            newPrice = Double.parseDouble(priceStr);
                            if (newPrice < 0) {
                                System.out.println("Price cannot be negative.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    int newQuantity = 0;
                    while (true) {
                        System.out.print("Enter quantity for " + newProduct + " (or type 'cancel' to go back): ");
                        String qtyStr = scan.nextLine();
                        if (qtyStr.equalsIgnoreCase("cancel")) {
                            return;
                        }
                        try {
                            newQuantity = Integer.parseInt(qtyStr);
                            if (newQuantity <= 0) {
                                System.out.println("Quantity must be greater than zero.");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }

                    product.add(newProduct);
                    price.add(newPrice);
                    quantity.add(newQuantity);

                    System.out.println("Added new product: " + newProduct + " with quantity " + newQuantity + " and price Php " + newPrice + "\n");

                } else if (choice.equals("3")) {
                    break;
                } else {
                    System.out.println("Invalid choice.\n");
                }
            }
        }

        static void updateItemQuantity(ArrayList<String> product, ArrayList<Integer> quantity) {
            if (product.isEmpty()) {
                System.out.println("No items available to update.\n");
                return;
            }

            System.out.println("\nAvailable products:");
            for (int i = 0; i < product.size(); i++) {
                System.out.println((i + 1) + ". " + product.get(i) + " - Current Quantity: " + quantity.get(i));
            }

            int selectedIndex = -1;
            while (true) {
                System.out.print("Select product number to update quantity (or type 'cancel' to go back): ");
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    return; 
                }
                try {
                    selectedIndex = Integer.parseInt(input) - 1;
                    if (selectedIndex >= 0 && selectedIndex < product.size()) {
                        break;
                    } else {
                        System.out.println("Invalid product number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }

            int newQuantity = 0;
            while (true) {
                System.out.print("Enter new quantity for " + product.get(selectedIndex) + " (or type 'cancel' to go back): ");
                String qtyStr = scan.nextLine();
                if (qtyStr.equalsIgnoreCase("cancel")) {
                    return; 
                }
                try {
                    newQuantity = Integer.parseInt(qtyStr);
                    if (newQuantity >= 0) {
                        break;
                    } else {
                        System.out.println("Quantity cannot be negative.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }

            quantity.set(selectedIndex, newQuantity);
            System.out.println("Updated quantity for " + product.get(selectedIndex) + " to " + newQuantity + ".\n");
        }

        static void displayItems(ArrayList<String> product, ArrayList<Integer> quantity, ArrayList<Double> price) {
            if (product.isEmpty()) {
                System.out.println("No items added yet.\n");
                return;
            }

            System.out.println("\nItems: ");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "Qty", "Product", "Price", "Total");
            double grandTotal = 0;
            for (int i = 0; i < product.size(); i++) {
                double totalPrice = quantity.get(i) * price.get(i);
                System.out.printf("%-10d %-20s %-10.2f %-10.2f%n", quantity.get(i), product.get(i), price.get(i), totalPrice);
                grandTotal += totalPrice;
            }
            System.out.printf("Grand Total: Php %.2f%n", grandTotal);
        }

        static void removeItem(ArrayList<String> product, ArrayList<Integer> quantity, ArrayList<Double> price) {
            if (product.isEmpty()) {
                System.out.println("No items to remove.\n");
                return;
            }

            System.out.println("Select item to remove:");
            for (int i = 0; i < product.size(); i++) {
                System.out.println((i + 1) + ". " + product.get(i));
            }

            int removeItem = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter item number (or type 'cancel' to go back): ");
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    return;
                }
                try {
                    removeItem = Integer.parseInt(input);
                    if (removeItem > 0 && removeItem <= product.size()) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid item number.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.\n");
                }
            }

            product.remove(removeItem - 1);
            quantity.remove(removeItem - 1);
            price.remove(removeItem - 1);
            System.out.println("Item removed.\n");
        }

        static void processPayment(ArrayList<String> product, ArrayList<Integer> quantity, ArrayList<Double> price) {
            if (product.isEmpty()) {
                System.out.println("No items to pay for.\n");
                return;
            }

            double grandTotalPayment = 0;
            for (int i = 0; i < product.size(); i++) {
                grandTotalPayment += quantity.get(i) * price.get(i);
            }
            System.out.println("Total Amount: Php " + grandTotalPayment);

            double payment = 0;
            boolean validPayment = false;
            while (!validPayment) {
                System.out.print("Enter payment (or type 'cancel' to go back): ");
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    return;
                }
                try {
                    payment = Double.parseDouble(input);
                    if (payment >= grandTotalPayment) {
                        validPayment = true;
                    } else {
                        System.out.println("Insufficient payment. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            double change = payment - grandTotalPayment;
            System.out.println("Change: Php " + change + "\n");

            logTransaction(product, quantity, price, grandTotalPayment);

            for (int i = 0; i < quantity.size(); i++) {
                quantity.set(i, 0);
            }
        }

        static void logTransaction(ArrayList<String> product, ArrayList<Integer> quantity, ArrayList<Double> price, double totalAmount) {
            StringBuilder transactionDetails = new StringBuilder();
            transactionDetails.append("Date and Time: ")
                            .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                            .append("\nUsername: ").append(loggedInUser )
                            .append("\nItems Purchased:\n");

            for (int i = 0; i < product.size(); i++) {
                if (quantity.get(i) > 0) {
                    transactionDetails.append(product.get(i))
                                    .append(" - Qty: ").append(quantity.get(i))
                                    .append(", Price: Php ").append(String.format("%.2f", price.get(i)))
                                    .append("\n");
                }
            }
            transactionDetails.append("Total Amount: Php ").append(String.format("%.2f", totalAmount)).append("\n\n");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
                writer.write(transactionDetails.toString());
                writer.flush();
                System.out.println("Transaction logged successfully.\n");
            } catch (IOException e) {
                System.out.println("Error logging transaction: " + e.getMessage());
            }
        }
    }
