
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

public class StudentCRUD extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField courseField;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private static final Path CSV_FILE =
            Path.of("Practical 11", "students.csv");

    public StudentCRUD() {

        setTitle("Student Management - CRUD Operations");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(
                BorderFactory.createTitledBorder("Student Details")
        );

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        formPanel.add(courseField);

        // Buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        JButton refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        String[] columns = {"ID", "Name", "Email", "Course"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Student Records")
        );

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        refreshButton.addActionListener(e -> loadStudents());

        // Select a row to display its details in the form
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = studentTable.getSelectedRow();

                if (row != -1) {
                    idField.setText(
                            tableModel.getValueAt(row, 0).toString()
                    );
                    nameField.setText(
                            tableModel.getValueAt(row, 1).toString()
                    );
                    emailField.setText(
                            tableModel.getValueAt(row, 2).toString()
                    );
                    courseField.setText(
                            tableModel.getValueAt(row, 3).toString()
                    );
                }
            }
        });

        loadStudents();

        setVisible(true);
    }

    // CREATE: Add a new student
    private void addStudent() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        if (!validateFields(name, email, course)) {
            return;
        }

        try {
            List<String[]> students = readStudents();

            int nextId = 1;

            for (String[] student : students) {
                nextId = Math.max(
                        nextId,
                        Integer.parseInt(student[0]) + 1
                );
            }

            students.add(new String[]{
                    String.valueOf(nextId), name, email, course
            });

            writeStudents(students);
            loadStudents();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!"
            );

        } catch (IOException | NumberFormatException ex) {
            showError(ex);
        }
    }

    // READ: Display all student records
    private void loadStudents() {

        tableModel.setRowCount(0);

        try {
            List<String[]> students = readStudents();

            for (String[] student : students) {
                tableModel.addRow(student);
            }

        } catch (IOException | NumberFormatException ex) {
            showError(ex);
        }
    }

    // UPDATE: Modify the selected student
    private void updateStudent() {

        String id = idField.getText();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String course = courseField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student from the table."
            );
            return;
        }

        if (!validateFields(name, email, course)) {
            return;
        }

        try {
            List<String[]> students = readStudents();
            boolean found = false;

            for (String[] student : students) {
                if (student[0].equals(id)) {
                    student[1] = name;
                    student[2] = email;
                    student[3] = course;
                    found = true;
                    break;
                }
            }

            if (found) {
                writeStudents(students);
                loadStudents();
                clearFields();

                JOptionPane.showMessageDialog(
                        this,
                        "Student updated successfully!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Student record not found."
                );
            }

        } catch (IOException | NumberFormatException ex) {
            showError(ex);
        }
    }

    // DELETE: Remove the selected student
    private void deleteStudent() {

        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student from the table."
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            List<String[]> students = readStudents();

            students.removeIf(student -> student[0].equals(id));

            writeStudents(students);
            loadStudents();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully!"
            );

        } catch (IOException | NumberFormatException ex) {
            showError(ex);
        }
    }

    // Read all records from the CSV file
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
                    String[] student = parseCSVLine(line);

                    if (student.length == 4) {
                        students.add(student);
                    }
                }
            }
        }

        return students;
    }

    // Write all records back to the CSV file
    private void writeStudents(List<String[]> students)
            throws IOException {

        Files.createDirectories(CSV_FILE.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(
                CSV_FILE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write("ID,Name,Email,Course");
            writer.newLine();

            for (String[] student : students) {

                writer.write(
                        toCSV(student[0]) + ","
                                + toCSV(student[1]) + ","
                                + toCSV(student[2]) + ","
                                + toCSV(student[3])
                );

                writer.newLine();
            }
        }
    }

    // Convert a value into CSV format
    private String toCSV(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Parse CSV values, including commas and quotation marks
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

    // Validate form fields
    private boolean validateFields(
            String name, String email, String course) {

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return false;
        }

        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address."
            );
            return false;
        }

        return true;
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        courseField.setText("");

        studentTable.clearSelection();
    }

    private void showError(Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                "Error: " + ex.getMessage()
        );

        ex.printStackTrace();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentCRUD::new);
    }
}


