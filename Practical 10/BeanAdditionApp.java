

import javax.swing.*;
import java.awt.*;

public class BeanAdditionApp extends JFrame {

    private JTextField number1Field;
    private JTextField number2Field;
    private JLabel resultLabel;

    // Create the JavaBean object
    private final AdditionBean additionBean = new AdditionBean();

    public BeanAdditionApp() {

        setTitle("JavaBean - Addition of Two Numbers");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // First number
        panel.add(new JLabel("Enter First Number:"));
        number1Field = new JTextField();
        panel.add(number1Field);

        // Second number
        panel.add(new JLabel("Enter Second Number:"));
        number2Field = new JTextField();
        panel.add(number2Field);

        // Add button
        JButton addButton = new JButton("Calculate Sum");
        panel.add(addButton);

        // Clear button
        JButton clearButton = new JButton("Clear");
        panel.add(clearButton);

        // Result label
        panel.add(new JLabel("Result:"));
        resultLabel = new JLabel("0.0");
        panel.add(resultLabel);

        add(panel);

        addButton.addActionListener(e -> calculateAddition());
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void calculateAddition() {

        try {
            double number1 = Double.parseDouble(
                    number1Field.getText().trim()
            );

            double number2 = Double.parseDouble(
                    number2Field.getText().trim()
            );

            // Set values using JavaBean setter methods
            additionBean.setNumber1(number1);
            additionBean.setNumber2(number2);

            // Get the calculated result from the JavaBean
            double sum = additionBean.getResult();

            resultLabel.setText(String.valueOf(sum));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );
        }
    }

    private void clearFields() {

        number1Field.setText("");
        number2Field.setText("");
        resultLabel.setText("0.0");

        additionBean.setNumber1(0);
        additionBean.setNumber2(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BeanAdditionApp::new);
    }
}
