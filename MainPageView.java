import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPageView {
    private JFrame jf;
    private JPanel jp;
    private JTextField tName, tQuantity, tPrice, tCuisine, tExpiry;
    private JTextArea cartTextArea;
    private JButton addButton, orderButton, historyButton;

    
    private FoodItem[] cartItems = new FoodItem[100];
    private int itemCount = 0;
    private double totalCost = 0.0;

    public MainPageView() {
        jf = new JFrame("Food Management System");
        jf.setSize(800, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp = new JPanel();
        jp.setLayout(null);

        JLabel label;

        
        label = new JLabel("Food name:");
        label.setBounds(50, 50, 150, 20);
        jp.add(label);

        tName = new JTextField();
        tName.setBounds(140, 50, 150, 30);
        jp.add(tName);

        
        label = new JLabel("Food quantity:");
        label.setBounds(50, 100, 150, 20);
        jp.add(label);

        tQuantity = new JTextField();
        tQuantity.setBounds(140, 100, 150, 30);
        jp.add(tQuantity);

        
        label = new JLabel("Price per item:");
        label.setBounds(50, 150, 150, 20);
        jp.add(label);

        tPrice = new JTextField();
        tPrice.setBounds(140, 150, 150, 30);
        jp.add(tPrice);

        
        label = new JLabel("Cuisine:");
        label.setBounds(50, 200, 150, 20);
        jp.add(label);

        tCuisine = new JTextField();
        tCuisine.setBounds(140, 200, 150, 30);
        jp.add(tCuisine);

        
        label = new JLabel("Expiry date:");
        label.setBounds(50, 250, 150, 20);
        jp.add(label);

        tExpiry = new JTextField();
        tExpiry.setBounds(140, 250, 150, 30);
        jp.add(tExpiry);

        
        label = new JLabel("Cart:");
        label.setBounds(590, 20, 40, 20);
        jp.add(label);

        cartTextArea = new JTextArea();
        cartTextArea.setBounds(450, 50, 300, 350);
        cartTextArea.setEditable(false);
        jp.add(cartTextArea);

        
        addButton = new JButton("Add to cart");
        addButton.setBounds(50, 350, 110, 30);
        jp.add(addButton);

        orderButton = new JButton("Order now");
        orderButton.setBounds(170, 350, 110, 30);
        jp.add(orderButton);

        historyButton = new JButton("Order history");
        historyButton.setBounds(290, 350, 130, 30);
        jp.add(historyButton);

        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = tName.getText().trim();
                String quantityStr = tQuantity.getText().trim();
                String priceStr = tPrice.getText().trim();
                String cuisine = tCuisine.getText().trim();
                String expiry = tExpiry.getText().trim();

                if (name.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(jf,
                            "Please enter name, quantity and price.",
                            "Blank",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityStr);
                    double price = Double.parseDouble(priceStr);

                    
                    if (itemCount >= cartItems.length) {
                        JOptionPane.showMessageDialog(jf,
                                "Cart is full",
                                "Cart Full",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    FoodItem item = new FoodItem(name, quantity, price, cuisine, expiry);
                    cartItems[itemCount] = item;
                    itemCount++;
                    totalCost += item.getCost();

                    
                    String cartText = "";
                    for (int i = 0; i < itemCount; i++) {
                        cartText = cartText + cartItems[i].toString() + "\n";
                    }
                    cartText = cartText + "\nTotal: " + totalCost + " tk";
                    cartTextArea.setText(cartText);

                    
                    tName.setText("");
                    tQuantity.setText("");
                    tPrice.setText("");
                    tCuisine.setText("");
                    tExpiry.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(jf,
                            "Invalid input",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (itemCount == 0) {
                    JOptionPane.showMessageDialog(jf,
                            "Cart is empty, please add items",
                            "Empty Cart",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    OrderRepository.saveOrder(cartItems, itemCount, totalCost);
                    JOptionPane.showMessageDialog(jf,
                            "Order saved!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    
                    for (int i = 0; i < itemCount; i++) {
                        cartItems[i] = null;
                    }
                    itemCount = 0;
                    totalCost = 0.0;
                    cartTextArea.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jf,
                            "Failed to save order.",
                            "I/O Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                new OrderHistoryView(jf);
            }
        });

        jf.add(jp);
    }

    
    public JFrame getFrame() {
        return jf;
    }
}