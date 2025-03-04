import Model.Customer.Customer;
import Model.Order.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerDetailFrame extends JFrame {
    private JPanel rootPanel;
    private JTextField customerNameField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JTable orderTable;
    private JButton viewOrderButton;
    private JButton backButton;

    private Customer customer;

    public CustomerDetailFrame(Customer customer) {
        this.customer = customer;

        setTitle("Model.Customer.Customer Details");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);

        populateCustomerDetails();
        populateOrderTable();

        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected order's ID
                    String orderId = (String) orderTable.getValueAt(selectedRow, 0);
                    List<Order> orders = customer.getOrders();
                    Order selectedOrder = orders.stream()
                            .filter(order -> String.valueOf(order.getId()).equals(orderId))
                            .findFirst()
                            .orElse(null);

                    if (selectedOrder != null) {
                        new OrderDetailsFrame(selectedOrder, customer, CustomerDetailFrame.this).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(CustomerDetailFrame.this, "Model.Order.Order not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerDetailFrame.this, "Please select an order.");
                }
            }
        });

        backButton.addActionListener(e -> {
            new CustomerFrame().setVisible(true);
            dispose();
        });
    }

    private void populateCustomerDetails() {
        customerNameField.setText(customer.getName());
        phoneNumberField.setText(customer.getPhoneNumber());
        emailField.setText(customer.getEmail());
    }

    private void populateOrderTable() {
        String[] columnNames = {"Model.Order.Order ID", "Total Cost", "Status"};

        List<Order> orders = customer.getOrders();
        String[][] data = new String[orders.size()][3];

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            data[i][0] = String.valueOf(order.getId());
            data[i][1] = String.format("%.2f", order.getTotalCost());
            data[i][2] = order.getStatus();
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        orderTable.setModel(tableModel);
    }
}
