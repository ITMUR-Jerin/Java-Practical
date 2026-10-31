
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea textArea;
    private File currentFile;

    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem saveAsItem;
    private JMenuItem exitItem;

    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem selectAllItem;

    public Notepad() {

        setTitle("Untitled - Notepad");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        createMenuBar();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        setVisible(true);
    }

    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");

        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        saveAsItem = new JMenuItem("Save As");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");

        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        selectAllItem = new JMenuItem("Select All");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(selectAllItem);

        JMenuItem[] items = {
                newItem, openItem, saveItem, saveAsItem, exitItem,
                cutItem, copyItem, pasteItem, selectAllItem
        };

        for (JMenuItem item : items) {
            item.addActionListener(this);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == newItem) {
            newFile();
        }
        else if (source == openItem) {
            openFile();
        }
        else if (source == saveItem) {
            saveFile();
        }
        else if (source == saveAsItem) {
            saveAsFile();
        }
        else if (source == exitItem) {
            exitApplication();
        }
        else if (source == cutItem) {
            textArea.cut();
        }
        else if (source == copyItem) {
            textArea.copy();
        }
        else if (source == pasteItem) {
            textArea.paste();
        }
        else if (source == selectAllItem) {
            textArea.selectAll();
        }
    }

    private void newFile() {

        textArea.setText("");
        currentFile = null;
        setTitle("Untitled - Notepad");
    }

    private void openFile() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            currentFile = chooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(currentFile))) {

                textArea.read(reader, null);

                setTitle(currentFile.getName() + " - Notepad");

            } catch (IOException ex) {
                showError(ex);
            }
        }
    }

    private void saveFile() {

        if (currentFile == null) {
            saveAsFile();
        } else {
            writeFile(currentFile);
        }
    }

    private void saveAsFile() {

        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            currentFile = chooser.getSelectedFile();

            writeFile(currentFile);
        }
    }

    private void writeFile(File file) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            textArea.write(writer);

            setTitle(file.getName() + " - Notepad");

        } catch (IOException ex) {
            showError(ex);
        }
    }

    private void exitApplication() {
        dispose();
    }

    private void showError(IOException ex) {

        JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Notepad::new);
    }
}
