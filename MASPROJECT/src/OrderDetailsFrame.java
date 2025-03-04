import Model.Customer.Customer;
import Model.MenuItem;
import Model.Order.Order;
import Utilities.ObjectPlus;

import javax.swing.*;
import java.io.IOException;
import java.io.NotSerializableException;
import java.util.List;

public class OrderDetailsFrame extends JFrame {
    private JPanel rootPanel;
    private JTextField orderedByField;
    private JList<String> menuItemsList;
    private JTextField orderCommentField;
    private JTextField orderAddressField;
    private JTextField totalPriceField;
    private JTextField orderStatusField;
    private JButton backButton;
    private JButton saveButton;

    private JFrame previousFrame;
    private Order order;

    public OrderDetailsFrame(Order order, Customer customer, JFrame previousFrame) {
        this.order = ObjectPlus.getExtent(Order.class).stream()
                .filter(o -> o.getId() == order.getId())
                .findFirst()
                .orElse(order);

        setTitle("Model.Order.Order Details");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);

        orderedByField.setText(customer.getName());
        orderedByField.setEditable(false);
        totalPriceField.setText(String.format("%.2f", order.getTotalCost()));
        totalPriceField.setEditable(false);
        orderStatusField.setText(order.getStatus());
        orderCommentField.setText(order.getComment());
        orderAddressField.setText(order.getAddress());

        populateMenuItemsList(order.getMenuItems());

        saveButton.addActionListener(e -> saveChanges());

        backButton.addActionListener(e -> {
            if (previousFrame != null) {
                previousFrame.setVisible(true);
            }
            dispose();
        });
    }

    private void populateMenuItemsList(List<MenuItem> menuItems) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (MenuItem item : menuItems) {
            listModel.addElement(item.toString());
        }
        menuItemsList.setModel(listModel);
    }

    private void saveChanges() {
        try {
            order.setComment(orderCommentField.getText());
            order.setAddress(orderAddressField.getText());
            order.setStatus(orderStatusField.getText());

            // Log updated fields
            System.out.println("Updated Model.Order.Order Comment: " + order.getComment());
            System.out.println("Updated Model.Order.Order Address: " + order.getAddress());
            System.out.println("Updated Model.Order.Order Status: " + order.getStatus());

            ObjectPlus.saveExtents("Extents.txt");

            JOptionPane.showMessageDialog(this, "Model.Order.Order details saved successfully!");
        } catch (NotSerializableException e) {
            JOptionPane.showMessageDialog(this, "Failed to save changes: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save changes due to an IO error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
