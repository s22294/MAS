import Model.Customer.Customer;
import Utilities.ObjectPlus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class CustomerFrame extends JFrame {
    private JPanel rootPanel;
    private JTable customerTable;
    private JButton selectButton;
    private JButton backButton;

    public CustomerFrame() {
        setTitle("Model.Customer.Customer List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);

        populateCustomerTable();

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedName = (String) customerTable.getValueAt(selectedRow, 0);

                    // Find the corresponding Model.Customer.Customer object in the extent
                    Customer selectedCustomer = ObjectPlus.getExtent(Customer.class).stream()
                            .filter(customer -> customer.getName().equals(selectedName))
                            .findFirst()
                            .orElse(null);

                    if (selectedCustomer != null) {
                        new CustomerDetailFrame(selectedCustomer).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(CustomerFrame.this, "Model.Customer.Customer not found.");
                    }
                } else {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Please select a customer.");
                }
            }
        });

        backButton.addActionListener(e -> {
            new MainMenuFrame().setVisible(true);
            dispose();
        });
    }

    private void populateCustomerTable() {
        String[] columnNames = {"Model.Customer.Customer Name", "Contact Number"};

        Set<Customer> customers = ObjectPlus.getExtent(Customer.class);

        String[][] data = new String[customers.size()][2];
        int rowIndex = 0;
        for (Customer customer : customers) {
            data[rowIndex][0] = customer.getName();
            data[rowIndex][1] = customer.getPhoneNumber();
            rowIndex++;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        customerTable.setModel(tableModel);
    }
}
