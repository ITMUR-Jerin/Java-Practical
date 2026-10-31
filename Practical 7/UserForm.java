
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserForm extends JFrame implements ActionListener {

    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton clearButton;

    public UserForm() {

        setTitle("User Registration Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Create labels and input fields
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Create buttons
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");

        // Add action listeners
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Add components to panel
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(submitButton);
        panel.add(clearButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {

            String name = nameField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter both name and password."
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Form Submitted Successfully!\nWelcome, " + name
                );
            }

        } else if (e.getSource() == clearButton) {

            nameField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserForm::new);
    }
}

