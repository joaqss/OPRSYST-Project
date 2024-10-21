import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
    Process process = new Process(this);

    JFrame mainFrame;
    JPanel mainPanel;
    JButton addProcessButton;
    JButton removeProcessButton;
    JButton startButton;
    Object[][] data = getData();

    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    public Window() {
        // frame
        mainFrame = new JFrame("CPU Scheduler");
        mainFrame.setSize((int)screenWidth, (int)screenHeight);

        // panel
        mainPanel = new JPanel();
        mainPanel.setSize((int)screenWidth, (int)screenHeight);
        mainPanel.setLayout(null);

        // initialization of elements
        addProcessButton = new JButton("Add Process");
        removeProcessButton = new JButton("Remove Process");
        startButton = new JButton("Start");
        String[] column = {"Process No.", "Arrival Time", "Burst Time"}; // column names
        DefaultTableModel model = new DefaultTableModel(data, column);
        JTable processTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(processTable); // used to display headers correctly

        // set bounds
        addProcessButton.setBounds(50, 50, 150, 50);
        removeProcessButton.setBounds(50, 150, 150, 50);
        startButton.setBounds(50, 250, 150, 50);
        scrollPane.setBounds(250, 50, 500, 500);

        // elements of panel
        mainPanel.add(addProcessButton);
        mainPanel.add(removeProcessButton);
        mainPanel.add(startButton);
        mainPanel.add(scrollPane);

        // action listeners
        addProcessButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputDialog(model);
            }
        });

        // default
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private Object[][] getData() {
        return process.data;
    }

    private void inputDialog(DefaultTableModel model) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField processNoField = new JTextField(process.processNo++ + "");
        processNoField.setEditable(false);
        JTextField arrivalTimeField = new JTextField();
        JTextField burstTimeField = new JTextField();
        JTextField priorityField = new JTextField(); // Additional input field

        panel.add(new JLabel("Process No:"));
        panel.add(processNoField);
        panel.add(new JLabel("Arrival Time:"));
        panel.add(arrivalTimeField);
        panel.add(new JLabel("Burst Time:"));
        panel.add(burstTimeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Process Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String processNo = processNoField.getText();
            String arrivalTime = arrivalTimeField.getText();
            String burstTime = burstTimeField.getText();
            String priority = priorityField.getText(); // Retrieve additional input
            model.addRow(new Object[]{processNo, arrivalTime, burstTime, priority}); // Add new row to table
        } else {
            System.out.println("Input canceled");
        }
    }





}
