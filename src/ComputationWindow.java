import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ComputationWindow {
    JPanel panel;
    DefaultTableModel model;

    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    public ComputationWindow(Window window, Process process) {
        String[][] computedData ;

        // panel
        panel = new JPanel();
        panel.setSize((int) screenWidth, (int) screenHeight);
        panel.setLayout(null);

        // initialization of elements
        JLabel averageWaitingTime = new JLabel("Average Waiting Time");
        averageWaitingTime.setFont(new Font("Arial", Font.BOLD, 20));
        JTextField awtField = new JTextField();
        JLabel averageTurnaroundTime = new JLabel("Average Turnaround Time");
        averageTurnaroundTime.setFont(new Font("Arial", Font.BOLD, 20));
        JTextField tatField = new JTextField();
        JButton goBackButton = new JButton("Go Back");
        String[] column = {"Process No.", "Arrival Time", "Burst Time", "Status", "Waiting Time"}; // column names
        model = new DefaultTableModel(process.data, column);
        JTable processTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(processTable); // used to display headers correctly

        // set bounds
        scrollPane.setBounds(700,50,500,500);
        averageWaitingTime.setBounds(50,50,250,30);
        awtField.setBounds(50,100,200,40);
        averageTurnaroundTime.setBounds(50,150,250,30);
        tatField.setBounds(50,200,200,40);
        goBackButton.setBounds(50, 250, 100, 50);

        panel.add(averageWaitingTime);
        panel.add(awtField);
        panel.add(averageTurnaroundTime);
        panel.add(tatField);
        panel.add(goBackButton);
        panel.add(scrollPane);

        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                panel.setVisible(false);
                window.mainPanel.setVisible(true);

            }
        });
    }

    // end of ComputationWindow Class
}
