import javax.swing.*;
import java.awt.*;
public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinRB, regularRB, deepDishRB;
    private ButtonGroup crustGroup;
    private JComboBox<String> sizeCB;
    private final String[] SIZES = {"-- Choose a size --", "Small", "Medium", "Large", "Super"};
    private final double[] PRICES = {0.00, 8,00, 12.00, 16.00, 20.00};
    private final String[] TOPPINGS = {"Pepperoni", "Mushrooms", "Onions", "Extra Cheese", "Pineapple", "Dragon Peppers"};
    private JCheckBox[] toppingCB = new JCheckBox[TOPPINGS.length];
    private final double TOPPING_PRICE = 1.00;
    private JTextArea receiptTA;
    private JButton orderBtn, clearBtn, quitBtn;
    private final double TAX_RATE = 0.07;

    public PizzaGUIFrame() {
        setTitle("Pizza Order");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel optionPanel = new JPanel(new GridLayout(1,3,10,10));
        JPanel bottomPanel = new JPanel(new  BorderLayout());

        add(optionPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        optionPanel.add(createCrustPanel());
        optionPanel.add(createSizePanel());
        optionPanel.add(createToppingPanel());

        bottomPanel.add(createReceiptPanel(), BorderLayout.CENTER);
        bottomPanel.add(createButtonPanel(), BorderLayout.SOUTH);


        setVisible(true);
    }
    private JPanel createCrustPanel() {
        JPanel panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createTitledBorder("Crust"));

        thinRB = new JRadioButton("Thin");
        regularRB = new JRadioButton("Regular");
        deepDishRB = new JRadioButton("Deep Dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinRB);
        crustGroup.add(regularRB);
        crustGroup.add(deepDishRB);

        panel.add(thinRB);
        panel.add(regularRB);
        panel.add(deepDishRB);
        return panel;
    }
    private JPanel createSizePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Size"));
        sizeCB = new JComboBox<>(SIZES);
        panel.add(sizeCB);

        return panel;
    }
    private JPanel createToppingPanel() {
        JPanel panel = new JPanel(new GridLayout(TOPPINGS.length, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Topping ($1.00 each)"));

        for (int i = 0; i < TOPPINGS.length; i++) {
            toppingCB[i] = new JCheckBox(TOPPINGS[i]);
            panel.add(toppingCB[i]);
        }
        return panel;
    }
    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Receipt"));

        receiptTA = new JTextArea(12, 40);
        receiptTA.setEditable(false);
        receiptTA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        panel.add(new JScrollPane(receiptTA), BorderLayout.CENTER);
        return panel;
    }
    private JPanel createButtonPanel(){
        JPanel panel = new JPanel(new GridLayout(1,3,10,10));
        orderBtn = new JButton("Order");
        clearBtn = new JButton("Clear");
        quitBtn = new  JButton("Quit");
        panel.add(orderBtn);
        panel.add(clearBtn);
        panel.add(quitBtn);
       quitBtn.addActionListener(e -> quit());
       clearBtn.addActionListener(e -> clearForm());
       orderBtn.addActionListener(e -> placeOrder());
       return panel;
    }
    private void quit(){
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit", "Quit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    private void clearForm(){
        crustGroup.clearSelection();
        // brings it to choose the size
        sizeCB.setSelectedIndex(0);
        for (JCheckBox cb: toppingCB) {
            cb.setSelected(false);
        }
        receiptTA.setText("");
    }
    private void placeOrder(){
        String crust;
        if (thinRB.isSelected()) {
            crust = "Thin";
        }
        else if (regularRB.isSelected()) {
            crust = "Regular";
        }
        else if (deepDishRB.isSelected()) {
            crust = "Deep Dish";
        }
        else {
            JOptionPane.showMessageDialog(this, "Pleade Choose a crust");
            return;
        }
        int sizeIndex = sizeCB.getSelectedIndex();
        if (sizeIndex == 0){
            JOptionPane.showMessageDialog(this, "Please select a size");
            return;

        }
        int toppingCount = 0;
        for (JCheckBox cb: toppingCB) {
            if (cb.isSelected()) {
                toppingCount++;
            }
        }
            if (toppingCount == 0){
                JOptionPane.showMessageDialog(this, "Please select atleast one Topping");
                return;
            }

            // Calculations of the order
            double basePrice = PRICES[sizeIndex];
            double subTotal  = basePrice + toppingCount * TOPPING_PRICE;
            double tax       = subTotal * TAX_RATE;
            double total     = subTotal + tax;

// The receipt layout
            String line = "=========================================\n";
            String dash = "-----------------------------------------\n";
            StringBuilder r = new StringBuilder();
            r.append(line);
            r.append(String.format("%-30s %9.2f%n", crust + " & " + SIZES[sizeIndex], basePrice));
            for (JCheckBox cbs : toppingCB) {
                if (cbs.isSelected()) {
                    r.append(String.format("%-30s %9.2f%n", cbs.getText(), TOPPING_PRICE));
                }
            }
            r.append("\n");
            r.append(String.format("%-30s %9.2f%n", "Sub-total:", subTotal));
            r.append(String.format("%-30s %9.2f%n", "Tax:", tax));
            r.append(dash);
            r.append(String.format("%-30s %9.2f%n", "Total:", total));
            r.append(line);
            receiptTA.setText(r.toString());

        }


}