import Model.Customer.Customer;
import Model.MenuItem;
import Model.Order.Order;
import Utilities.ObjectPlus;

import javax.swing.*;
import java.util.Random;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load saved data or create sample data if not found
                ObjectPlus.loadExtents("Extents.txt");
            } catch (Exception e) {
                System.out.println("No saved data found. Creating sample data...");
                createSampleData();
            }

            // Show the Main Menu
            MainMenuFrame mainMenuFrame = new MainMenuFrame();
            mainMenuFrame.setVisible(true);

            // Save data on exit
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    ObjectPlus.saveExtents("Extents.txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }));
        });
    }

    private static void createSampleData() {
        Customer customer1 = new Customer(1, "Alice", "alice@example.com", "123 456 789");
        Customer customer2 = new Customer(2, "Bob", "bob@example.com", "987 654 321");
        Customer customer3 = new Customer(3, "Charlie", "charlie@example.com", "456 789 123");
        Customer customer4 = new Customer(4, "Diana", "diana@example.com", "654 321 987");
        Customer customer5 = new Customer(5, "Eve", "eve@example.com", "321 654 987");
        Customer customer6 = new Customer(6, "Frank", "frank@example.com", "789 123 456");
        Customer customer7 = new Customer(7, "Grace", "grace@example.com", "123 456 123");
        Customer customer8 = new Customer(8, "Hank", "hank@example.com", "456 789 789");
        Customer customer9 = new Customer(9, "Ivy", "ivy@example.com", "321 321 321");
        Customer customer10 = new Customer(10, "Jack", "jack@example.com", "999 888 777"); // No orders for Jack

        Set<Customer> customers = ObjectPlus.getExtent(Customer.class);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        customers.add(customer6);
        customers.add(customer7);
        customers.add(customer8);
        customers.add(customer9);
        customers.add(customer10);

        // Create a random object for generating unique orders
        Random random = new Random();
        int orderId = 1;

        for (Customer customer : customers) {
            if (customer.getId() == 10) {
                // Skip customer 10
                continue;
            }

            // Assign 6 orders per customer with random menu items
            for (int i = 0; i < 6; i++) {
                Order order = new Order(orderId++, generateRandomStatus(random));

                // Add random menu items to the order
                int menuItemsCount = random.nextInt(3) + 2; // Between 2 and 4 items per order
                for (int j = 0; j < menuItemsCount; j++) {
                    order.addMenuItem(generateRandomMenuItem(random));
                }

                customer.addOrder(order);
            }
        }

        System.out.println("Sample data created. 10 customers, 60 orders (1 customer has no orders).");
    }

    private static String generateRandomStatus(Random random) {
        String[] statuses = {"Created", "In Progress", "Delivered", "Cancelled"};
        return statuses[random.nextInt(statuses.length)];
    }

    private static MenuItem generateRandomMenuItem(Random random) {
        MenuItem[] menuItems = {
                new MenuItem("Margherita Model.Order.Pizza", 10.99, 10, MenuItem.Category.PIZZA),
                new MenuItem("Pepperoni Model.Order.Pizza", 12.49, 5, MenuItem.Category.PIZZA),
                new MenuItem("Veggie Pasta", 9.99, 0, MenuItem.Category.PASTA),
                new MenuItem("Chicken Alfredo Pasta", 14.99, 5, MenuItem.Category.PASTA),
                new MenuItem("Garlic Bread", 3.99, 0, MenuItem.Category.SIDE),
                new MenuItem("Fries", 2.99, 10, MenuItem.Category.SIDE),
                new MenuItem("Coke", 1.99, 0, MenuItem.Category.DRINK),
                new MenuItem("Lemonade", 2.49, 0, MenuItem.Category.DRINK)
        };

        return menuItems[random.nextInt(menuItems.length)];
    }

}
