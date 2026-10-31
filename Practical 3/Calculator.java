
import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame implements ActionListener {

    private TextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean newInput = true;

    public Calculator() {
        setTitle("AWT Calculator");
        setSize(350, 450);
        setLayout(new BorderLayout(5, 5));

        display = new TextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        add(display, BorderLayout.NORTH);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String label : buttons) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            if (newInput || display.getText().equals("0")) {
                display.setText(command);
                newInput = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            firstNumber = 0;
            operator = "";
            newInput = true;
        } else if (command.equals("=")) {
            calculate();
        } else {
            try {
                firstNumber = Double.parseDouble(display.getText());
                operator = command;
                newInput = true;
            } catch (NumberFormatException ex) {
                display.setText("Error");
                newInput = true;
            }
        }
    }

    private void calculate() {
        if (operator.isEmpty()) {
            return;
        }

        try {
            double secondNumber =
                    Double.parseDouble(display.getText());

            double result;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        display.setText("Cannot divide by zero");
                        operator = "";
                        newInput = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    return;
            }

            display.setText(String.valueOf(result));
            firstNumber = result;
            operator = "";
            newInput = true;

        } catch (NumberFormatException ex) {
            display.setText("Error");
            operator = "";
            newInput = true;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
