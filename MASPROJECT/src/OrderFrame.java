import Model.Customer.Customer;
import Model.Order.Order;
import Utilities.ObjectPlus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderFrame extends JFrame {
    private JPanel rootPanel;
    private JTable orderTable;
    private JButton viewDetailsButton;
    private JButton backButton;

    public OrderFrame() {
        setTitle("Model.Order.Order List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);

        populateOrderTable();

        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    String orderId = (String) orderTable.getValueAt(selectedRow, 0);
                    Order selectedOrder = findOrderById(orderId);

                    if (selectedOrder != null) {
                        Customer customer = findCustomerByOrder(selectedOrder);
                        if (customer != null) {
                            new OrderDetailsFrame(selectedOrder, customer, OrderFrame.this).setVisible(true);
                            dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(OrderFrame.this, "Please select an order.");
                }
            }
        });

        backButton.addActionListener(e -> {
            new MainMenuFrame().setVisible(true);
            dispose();
        });
    }

    private void populateOrderTable() {
        String[] columnNames = {"Model.Order.Order ID", "Total Cost", "Status"};

        Set<Order> orders = ObjectPlus.getExtent(Order.class);

        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort((o1, o2) -> Integer.compare(o2.getId(), o1.getId())); // Descending order

        // Create data array for the table
        String[][] data = new String[sortedOrders.size()][3];

        int rowIndex = 0;
        for (Order order : sortedOrders) {
            data[rowIndex][0] = String.valueOf(order.getId());
            data[rowIndex][1] = String.format("%.2f", order.getTotalCost());
            data[rowIndex][2] = order.getStatus();
            rowIndex++;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        orderTable.setModel(tableModel);
    }

    private Order findOrderById(String orderId) {
        Set<Order> orders = ObjectPlus.getExtent(Order.class);
        for (Order order : orders) {
            if (String.valueOf(order.getId()).equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    private Customer findCustomerByOrder(Order order) {
        Set<Customer> customers = ObjectPlus.getExtent(Customer.class);
        for (Customer customer : customers) {
            if (customer.getOrders().contains(order)) {
                return customer;
            }
        }
        return null;
    }
}
