import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {

    ComputationWindow computationWindow;
    Process process = new Process();

    JFrame mainFrame;
    JPanel mainPanel;
    JButton addProcessButton;
    JButton startButton;
    Object[][] data = getData();
    int selection;

    // Ronin Added
    Home home;

    public Window() {
        // Ronin Added: Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ronin Modified frame: Changed title and size
        mainFrame = new JFrame("Process Configuration");
        mainFrame.setSize(800, 600);
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setLocationRelativeTo(null);

        // panel changed: Using BorderLayout instead of null layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Ronin Added panels for buttons and title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("PROCESS CONFIGURATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(102, 0, 102));

        // Ronin Added: Back button with hover images
        JButton backButton = createHoverImageButton(
                "./Images/back o.png",
                "./Images/back.png");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> launchApplication());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        // Ronin Modified: Changed button labels
        addProcessButton = new JButton("+ ADD PROCESS");
        styleButton(addProcessButton);

        startButton = new JButton("START");
        styleButton(startButton);

        buttonPanel.add(addProcessButton);
        buttonPanel.add(startButton);

        // table setup
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Ronin Modified: Changed column names
        String[] columns = {"PROCESS", "ARRIVAL TIME(S)", "BURST TIME(S)", "DEL"};
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing of cells
            }
        };

        JTable processTable = new JTable(model);

        // Table styling
        processTable.setRowHeight(40);
        processTable.setFont(new Font("Arial", Font.PLAIN, 14));
        processTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        processTable.setShowGrid(true);
        processTable.setGridColor(Color.LIGHT_GRAY);

        // Add delete column
        processTable.getColumnModel().getColumn(3).setMaxWidth(50);
        processTable.getColumnModel().getColumn(3).setMinWidth(50);

        JScrollPane scrollPane = new JScrollPane(processTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add everything to main panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Ronin Modified: Using action listener for adding process
        addProcessButton.addActionListener(e -> inputDialog(model));

        startButton.addActionListener(e -> {
            if (process.data.length == 0) {
                JOptionPane.showMessageDialog(mainFrame, "No process added", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                process.resetValues();
                selection = StartWindow.showDialog(mainFrame);

                if (selection != -1) {
                    switch (selection) {
                        case 0:
                            process.fcfs();
                            break;
                        case 1:
                            process.srtf();
                            break;
                    }
                    // Store the current state before creating computation window
                    int currentState = mainFrame.getExtendedState();
                    createComputationWindow();
                    mainPanel.setVisible(false);
                    computationWindow.panel.setVisible(true);
                    mainFrame.add(computationWindow.panel);
                    // Ensure the window is maximized
                    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }
        });

        // Delete row functionality
        processTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = processTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / processTable.getRowHeight();

                if (row < processTable.getRowCount() && row >= 0 &&
                        column < processTable.getColumnCount() && column >= 0) {
                    if (column == 3) {  // Delete column
                        model.removeRow(row);
                    }
                }
            }
        });

        // Frame setup
        mainFrame.add(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    // Ronin Modified: Added style to button method
    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 80, 90));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(170, 40));
    }

    private String[][] getData() {
        return process.data;
    }

    // Ronin Modified: Enhanced input dialog with restrictions
    private void inputDialog(DefaultTableModel model) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField processNoField = new JTextField(process.processNo + "");
        processNoField.setEditable(false);
        JTextField arrivalTimeField = new JTextField();
        JTextField burstTimeField = new JTextField();

        // Ronin Added: Input restrictions to allow only numbers
        ((AbstractDocument) arrivalTimeField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) burstTimeField.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        panel.add(new JLabel("Process No:"));
        panel.add(processNoField);
        panel.add(new JLabel("Arrival Time (s):"));
        panel.add(arrivalTimeField);
        panel.add(new JLabel("Burst Time (s):"));
        panel.add(burstTimeField);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Add Process",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (!arrivalTimeField.getText().trim().isEmpty() &&
                    !burstTimeField.getText().trim().isEmpty()) {
                String[] row = {String.valueOf(process.processNo),
                        arrivalTimeField.getText(),
                        burstTimeField.getText(),
                        "  X"};  // Using an X symbol for delete
                model.addRow(row);
                System.out.println("PROCESS NUMBER: " + process.processNo);

                // Update process data without the delete symbol
                process.addRow(new String[]{row[0], row[1], row[2]});
                process.processNo++;

            } else {
                JOptionPane.showMessageDialog(mainFrame, "All fields must be filled",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void createComputationWindow() {
        if (computationWindow == null) {
            computationWindow = new ComputationWindow(this, process);
        } else {
            computationWindow.refreshTable(process.data);
            computationWindow.timerStart();
        }
    }

    private void launchApplication() {
        mainFrame.revalidate(); // Refresh the main frame
        mainFrame.repaint(); // Repaint to show changes
        mainFrame.remove(mainPanel);

        if (home == null) {
            home = new Home();
            mainFrame.setVisible(false); // Show the window after launching the application
            mainPanel.setVisible(false);
        }
    }

    // Ronin Added: Create hover image button with functionality
    private JButton createHoverImageButton(String normalImagePath, String hoverImagePath) {
        ImageIcon buttonIcon = new ImageIcon(normalImagePath);
        ImageIcon hoverIcon = new ImageIcon(hoverImagePath);

        JButton button = new JButton(buttonIcon);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(50, 30));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(buttonIcon);
            }
        });

        return button;
    }

    // Document filter to restrict input to numbers only
    class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumeric(String str) {
            return str.matches("\\d*"); // Allow only digits
        }
    }
}