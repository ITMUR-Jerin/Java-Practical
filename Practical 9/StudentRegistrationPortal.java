
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class StudentRegistrationPortal extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField courseField;

    private DefaultTableModel tableModel;

    private static final Path CSV_FILE =
            Path.of("Practical 9", "students.csv");

    public StudentRegistrationPortal() {

        setTitle("Student Registration Portal");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Registration form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(
                BorderFactory.createTitledBorder("Student Registration")
        );

        formPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        formPanel.add(courseField);

        JButton registerButton = new JButton("Register Student");
        JButton clearButton = new JButton("Clear Form");

        formPanel.add(registerButton);
        formPanel.add(clearButton);

        // Table for displaying registered students
        String[] columns = {
                "Student ID", "Name", "Email", "Phone", "Course"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Registered Students")
        );

        JButton refreshButton = new JButton("Refresh Records");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        registerButton.addActionListener(e -> registerStudent());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> loadStudents());

        // Load existing records when the application starts
        loadStudents();

        setVisible(true);
    }

    private void registerStudent() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String course = courseField.getText().trim();

        if (name.isEmpty() || email.isEmpty()
                || phone.isEmpty() || course.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address."
            );
            return;
        }

        if (!phone.matches("[0-9]{10}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid 10-digit phone number."
            );
            return;
        }

        try {
            Files.createDirectories(CSV_FILE.getParent());

            List<String[]> students = readStudents();
            int nextId = 1;

            for (String[] student : students) {
                nextId = Math.max(nextId,
                        Integer.parseInt(student[0]) + 1);
            }

            boolean writeHeader = !Files.exists(CSV_FILE)
                    || Files.size(CSV_FILE) == 0;

            try (BufferedWriter writer = Files.newBufferedWriter(
                    CSV_FILE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {

                if (writeHeader) {
                    writer.write("Student ID,Name,Email,Phone,Course");
                    writer.newLine();
                }

                writer.write(toCSV(String.valueOf(nextId)) + ","
                        + toCSV(name) + ","
                        + toCSV(email) + ","
                        + toCSV(phone) + ","
                        + toCSV(course));

                writer.newLine();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Student registered successfully!\nStudent ID: " + nextId
            );

            clearForm();
            loadStudents();

        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error saving student details: " + ex.getMessage()
            );
            ex.printStackTrace();
        }
    }

    private void loadStudents() {

        tableModel.setRowCount(0);

        try {
            List<String[]> students = readStudents();

            for (String[] student : students) {
                tableModel.addRow(student);
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error reading student records: " + ex.getMessage()
            );
            ex.printStackTrace();
        }
    }

    private List<String[]> readStudents() throws IOException {

        List<String[]> students = new ArrayList<>();

        if (!Files.exists(CSV_FILE)) {
            return students;
        }

        try (BufferedReader reader = Files.newBufferedReader(CSV_FILE)) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (!line.isBlank()) {
                    students.add(parseCSVLine(line));
                }
            }
        }

        return students;
    }

    // Convert a value into CSV format
    private String toCSV(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Read CSV values, including quoted values
    private String[] parseCSVLine(String line) {

        List<String> values = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char ch = line.charAt(i);

            if (ch == '"') {

                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    value.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (ch == ',' && !insideQuotes) {

                values.add(value.toString());
                value.setLength(0);

            } else {
                value.append(ch);
            }
        }

        values.add(value.toString());

        return values.toArray(new String[0]);
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRegistrationPortal::new);
    }
}

