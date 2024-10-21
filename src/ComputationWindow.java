import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class ComputationWindow {
    JPanel panel;
    int processNo = 1;
    String[][] data = {{"1", "2", "3"},{"2", "2", "3"}};
    DefaultTableModel model;

    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    public ComputationWindow(Window window, Process process) {
        Object[][] data = window.getData();
        String[][] computedData;

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
        String[] column = {"Process No.", "Arrival Time", "Burst Time", "Status", "Waiting Time"}; // column names
        model = new DefaultTableModel(data, column);
        JTable processTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(processTable); // used to display headers correctly

        // set bounds
        scrollPane.setBounds(700,50,500,500);
        averageWaitingTime.setBounds(50,50,250,30);
        awtField.setBounds(50,100,200,40);
        averageTurnaroundTime.setBounds(50,150,250,30);
        tatField.setBounds(50,200,200,40);

        panel.add(averageWaitingTime);
        panel.add(awtField);
        panel.add(averageTurnaroundTime);
        panel.add(tatField);
        panel.add(scrollPane);
    }

    public void updateCell(int row, int column, Object newValue, DefaultTableModel model) {
        model.setValueAt(newValue, row, column);
    }

    public void addRow(String[] row) {
        String[][] newData = new String[data.length + 1][row.length];
        for (int i = 0; i<data.length;i++) {
            newData[i] = data[i];
        }
        newData[data.length] = row;
        data = newData;

        for (String[] a : data) {
            System.out.print(Arrays.toString(a));
        }
        System.out.println();
    }

    public void fcfs(ComputationWindow computationWindow) {
        // need: waiting time for each process, average waiting time for all processes WT = TS - AT
        // need: turnaround time for each process, average turnaround time for all processes TAT = TC-AT
        int i = 0;
        System.out.println("DATA LENGTH: " + data.length);
        while (i < data.length) {
            System.out.println("RUNNING FCFS");
            String[] process = data[i];
            int arrivalTime = Integer.parseInt(process[1]);
            int burstTime = Integer.parseInt(process[2]);


            computationWindow.updateCell(i, 3, "Running", model);
            computationWindow.panel.repaint();
            computationWindow.panel.revalidate();
            i++;
        }
    }

    public void srtf(ComputationWindow computationWindow) {
    }

    // end of ComputationWindow Class
}
