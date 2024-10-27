import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
    ComputationWindow computationWindow;
    Process process = new Process();

    JFrame mainFrame;
    JPanel mainPanel;
    JButton addProcessButton;
    JButton removeProcessButton;
    JButton startButton;
    Object[][] data = getData();
    int selection;

    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    public Window() {
        // frame
        mainFrame = new JFrame("CPU Scheduler Simulator");
        mainFrame.setSize((int)screenWidth, (int)screenHeight);

        // panel
        mainPanel = new JPanel();
        mainPanel.setSize((int)screenWidth, (int)screenHeight);
        mainPanel.setLayout(null);

        // initialization of elements
        addProcessButton = new JButton("Add Process");
        removeProcessButton = new JButton("Remove Process");
        startButton = new JButton("Start");
        String[] column = {"Process No.", "Arrival Time (s)", "Burst Time (s)"}; // column names
        DefaultTableModel model = new DefaultTableModel(data, column);
        JTable processTable = new JTable(model);
        processTable.setRowHeight(35);
        processTable.setFont(new Font("Arial", Font.PLAIN, 14));


        JScrollPane scrollPane = new JScrollPane(processTable); // used to display headers correctly

        // set bounds
        addProcessButton.setBounds(50, 50, 150, 50);
        removeProcessButton.setBounds(50, 150, 150, 50);
        startButton.setBounds(50, 250, 150, 50);
        scrollPane.setBounds(250, 50, 500, 500);

        // elements of main panel
        mainPanel.add(addProcessButton);
        mainPanel.add(removeProcessButton);
        mainPanel.add(startButton);
        mainPanel.add(scrollPane);

        // action listeners
        addProcessButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                inputDialog(model);
            }
        });
        removeProcessButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int selectedRow = processTable.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "No row selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                super.mousePressed(e);
                // check if process table is empty
                if (process.data.length == 0) {
                    JOptionPane.showMessageDialog(null, "No process added", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] options = {"FCFS", "SRTF"};
                    selection = JOptionPane.showOptionDialog(null, "Choose Algorithm", "Start", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, null);

                    switch (selection) {
                        case 0:
                            process.fcfs(); // with data sort, displays the sortedArr in computationWin
                            createComputationWindow();
                            mainPanel.setVisible(false);
                            computationWindow.panel.setVisible(true);
                            mainFrame.add(computationWindow.panel);
                            break;
                        case 1:
                            process.srtf(); // with data sort, displays the sortedArr in computationWin
                            createComputationWindow();
                            mainPanel.setVisible(false);
                            computationWindow.panel.setVisible(true);
                            mainFrame.add(computationWindow.panel);
                            break;
                        default:
                            break;
                    }


                }
            }
        });

        // default
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }


    private String[][] getData() {
        return process.data;
    }


    private void inputDialog(DefaultTableModel model) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField processNoField = new JTextField(process.processNo + "");
        processNoField.setEditable(false);
        JTextField arrivalTimeField = new JTextField();
        JTextField burstTimeField = new JTextField();

        panel.add(new JLabel("Process No:"));
        panel.add(processNoField);
        panel.add(new JLabel("Arrival Time (s):"));
        panel.add(arrivalTimeField);
        panel.add(new JLabel("Burst Time (s):"));
        panel.add(burstTimeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Process Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !arrivalTimeField.getText().isEmpty() && !burstTimeField.getText().isEmpty()) {

            String[] row = {processNoField.getText(), arrivalTimeField.getText(), burstTimeField.getText()};
            process.processNo++;
            model.addRow(row); // Add new row to table
            process.addRow(row); // Add new row to data

        } else if (result == JOptionPane.CANCEL_OPTION) {
            System.out.println("Cancelled.");
        } else {
            JOptionPane.showMessageDialog(null, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Field is empty.");
        }
    }


    public void createComputationWindow() {
        // ensures that there is only one instance habang nag rrun
        if (computationWindow == null) {
            computationWindow = new ComputationWindow(Window.this, process);
            System.out.println("ComputationWindow is null. Creating new instance");
        }

        else {
            computationWindow.refreshTable(process.data);
            computationWindow.timerStart();
        }
    }

    // end of Window class
}
