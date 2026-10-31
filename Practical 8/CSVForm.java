
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CSVForm extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField courseField;

    // CSV file will be created inside the Practical 8 folder
    private static final Path CSV_FILE =
            Path.of("Practical 8", "user_details.csv");

    public CSVForm() {

        setTitle("Student Details Form");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // Name field
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Email field
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        // Course field
        panel.add(new JLabel("Course:"));
        courseField = new JTextField();
        panel.add(courseField);

        // Buttons
        JButton saveButton = new JButton("Save");
        JButton clearButton = new JButton("Clear");

        panel.add(saveButton);
        panel.add(clearButton);

        add(panel);

        saveButton.addActionListener(e -> saveDetails());
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void saveDetails() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return;
        }

        try {
            // Create the Practical 8 folder if it does not exist
            Files.createDirectories(CSV_FILE.getParent());

            // Check whether the CSV file is new or empty
            boolean writeHeader = !Files.exists(CSV_FILE)
                    || Files.size(CSV_FILE) == 0;

            try (BufferedWriter writer = Files.newBufferedWriter(
                    CSV_FILE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {

                // Write column headings only once
                if (writeHeader) {
                    writer.write("Name,Email,Course");
                    writer.newLine();
                }

                // Write the entered details
                writer.write(toCSV(name) + ","
                        + toCSV(email) + ","
                        + toCSV(course));

                writer.newLine();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Details saved successfully!"
            );

            clearFields();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "File Error: " + ex.getMessage()
            );
            ex.printStackTrace();
        }
    }

    // Handles commas and quotation marks in CSV values
    private String toCSV(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CSVForm::new);
    }
}

