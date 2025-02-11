import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Customer class to hold customer data
class Customer implements Serializable {
    String name;
    String customerId;
    String address;
    String serviceType;
    String monthlyPlan;
    String billStatus;

    public Customer(String name, String customerId, String address, String serviceType, String monthlyPlan, String billStatus) {
        this.name = name;
        this.customerId = customerId;
        this.address = address;
        this.serviceType = serviceType;
        this.monthlyPlan = monthlyPlan;
        this.billStatus = billStatus;
    }

    @Override
    public String toString() {
        return name + " | " + customerId + " | " + address + " | " + serviceType + " | " + monthlyPlan + " | " + billStatus;
    }
}

public class ISPAutomationSystemGUI {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static List<Customer> customers = new ArrayList<>(); // List to store customer data
    private static List<String> billHistory = new ArrayList<>();  // List to store bill payment history
    private static final String CUSTOMER_FILE = "customers.dat";
    private static final String HISTORY_FILE = "billHistory.dat";
    private static JFrame frame;

    public static void main(String[] args) {
        // Load data from files before showing the login page
        loadData();
        SwingUtilities.invokeLater(() -> showLoginPage());
    }

    // Show Login Page
    private static void showLoginPage() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);

        // Create a panel with background color and better styling
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBackground(new Color(60, 63, 65));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        // Style the buttons
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 69, 0));
        cancelButton.setForeground(Color.WHITE);

        // Align components
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(cancelButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (USERNAME.equals(username) && PASSWORD.equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        frame.add(panel);
        frame.setVisible(true);
    }

    // Show Main Menu
    private static void showMainMenu() {
        frame = new JFrame("ISP Automation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Create panel with attractive background color
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(30, 30, 30));

        JButton addCustomerButton = new JButton("Add Customer");
        JButton viewCustomersButton = new JButton("View Customers");
        JButton generateBillButton = new JButton("Generate Bill");
        JButton viewHistoryButton = new JButton("View History");
        JButton exitButton = new JButton("Exit");

        // Style the buttons
        addCustomerButton.setBackground(new Color(34, 139, 34));
        addCustomerButton.setForeground(Color.WHITE);
        viewCustomersButton.setBackground(new Color(34, 139, 34));
        viewCustomersButton.setForeground(Color.WHITE);
        generateBillButton.setBackground(new Color(34, 139, 34));
        generateBillButton.setForeground(Color.WHITE);
        viewHistoryButton.setBackground(new Color(34, 139, 34));
        viewHistoryButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(255, 69, 0));
        exitButton.setForeground(Color.WHITE);

        addCustomerButton.addActionListener(e -> showAddCustomerPage());
        viewCustomersButton.addActionListener(e -> showCustomerTable());
        generateBillButton.addActionListener(e -> generateBill());
        viewHistoryButton.addActionListener(e -> viewBillHistory());
        exitButton.addActionListener(e -> {
            saveData();  // Save data before exiting
            System.exit(0);
        });

        panel.add(addCustomerButton);
        panel.add(viewCustomersButton);
        panel.add(generateBillButton);
        panel.add(viewHistoryButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Show Add Customer Page
    private static void showAddCustomerPage() {
        JFrame addCustomerFrame = new JFrame("Add Customer");
        addCustomerFrame.setSize(400, 300);
        addCustomerFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel serviceTypeLabel = new JLabel("Service Type:");
        JTextField serviceTypeField = new JTextField();
        JLabel monthlyPlanLabel = new JLabel("Monthly Plan:");
        JTextField monthlyPlanField = new JTextField();
        JLabel billStatusLabel = new JLabel("Bill Status:");
        JTextField billStatusField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Style the buttons
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(255, 69, 0));
        cancelButton.setForeground(Color.WHITE);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String customerId = customerIdField.getText();
            String address = addressField.getText();
            String serviceType = serviceTypeField.getText();
            String monthlyPlan = monthlyPlanField.getText();
            String billStatus = billStatusField.getText();

            if (name.isEmpty() || customerId.isEmpty() || address.isEmpty() || serviceType.isEmpty() || monthlyPlan.isEmpty() || billStatus.isEmpty()) {
                JOptionPane.showMessageDialog(addCustomerFrame, "All fields must be filled!");
            } else {
                customers.add(new Customer(name, customerId, address, serviceType, monthlyPlan, billStatus));
                JOptionPane.showMessageDialog(addCustomerFrame, "Customer added successfully!");
                addCustomerFrame.dispose();
            }
        });

        cancelButton.addActionListener(e -> addCustomerFrame.dispose());

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(customerIdLabel);
        panel.add(customerIdField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(serviceTypeLabel);
        panel.add(serviceTypeField);
        panel.add(monthlyPlanLabel);
        panel.add(monthlyPlanField);
        panel.add(billStatusLabel);
        panel.add(billStatusField);
        panel.add(saveButton);
        panel.add(cancelButton);

        addCustomerFrame.add(panel);
        addCustomerFrame.setVisible(true);
    }

    // Show Customer Table
    private static void showCustomerTable() {
        JFrame customerTableFrame = new JFrame("Customer List");
        customerTableFrame.setSize(600, 400);
        customerTableFrame.setLocationRelativeTo(null);

        String[] columnNames = {"Name", "Customer ID", "Address", "Service Type", "Monthly Plan", "Bill Status"};
        String[][] data = new String[customers.size()][6];

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.name;
            data[i][1] = customer.customerId;
            data[i][2] = customer.address;
            data[i][3] = customer.serviceType;
            data[i][4] = customer.monthlyPlan;
            data[i][5] = customer.billStatus;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        customerTableFrame.add(scrollPane);
        customerTableFrame.setVisible(true);
    }

    // Generate Bill (Simple Implementation)
    private static void generateBill() {
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No customers available to generate bill.");
        } else {
            StringBuilder billDetails = new StringBuilder();
            for (Customer customer : customers) {
                billDetails.append("Customer Name: ").append(customer.name)
                        .append(" | Service Type: ").append(customer.serviceType)
                        .append("\n");
            }
            JOptionPane.showMessageDialog(frame, billDetails.toString(), "Bill Generated", JOptionPane.INFORMATION_MESSAGE);
            billHistory.add(billDetails.toString());
        }
    }

    // View Bill History
    private static void viewBillHistory() {
        if (billHistory.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No bill history available.");
        } else {
            StringBuilder history = new StringBuilder("Bill History:\n");
            for (String bill : billHistory) {
                history.append(bill).append("\n");
            }
            JOptionPane.showMessageDialog(frame, history.toString(), "Bill History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Save Customer Data and Bill History to Files
    private static void saveData() {
        try (ObjectOutputStream customerStream = new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILE));
             ObjectOutputStream historyStream = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            customerStream.writeObject(customers);
            historyStream.writeObject(billHistory);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data.");
        }
    }

    // Load Customer Data and Bill History from Files
    private static void loadData() {
        try (ObjectInputStream customerStream = new ObjectInputStream(new FileInputStream(CUSTOMER_FILE));
             ObjectInputStream historyStream = new ObjectInputStream(new FileInputStream(HISTORY_FILE))) {
            customers = (List<Customer>) customerStream.readObject();
            billHistory = (List<String>) historyStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            customers = new ArrayList<>();
            billHistory = new ArrayList<>();
        }
    }
}
