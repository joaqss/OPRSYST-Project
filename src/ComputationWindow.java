import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComputationWindow {
    JPanel panel;
    DefaultTableModel model;
    Timer timer;
    int currentRow;
    String[] column = {"Process No.", "Arrival Time", "Burst Time", "Status", "Waiting Time", "Finish Time", "Turnaround Time"}; // column names
    int currentTime = 0;
    int minBurstTime;
    int shortestBTRow;
    int burstTime;



    JTextField awtField;
    JTextField tatField;


    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    public ComputationWindow(Window window, Process process) {

        // panel
        panel = new JPanel();
        panel.setSize((int) screenWidth, (int) screenHeight);
        panel.setLayout(null);

        // initialization of elements
        JLabel averageWaitingTime = new JLabel("Average Waiting Time");
        averageWaitingTime.setFont(new Font("Arial", Font.BOLD, 20));
        awtField = new JTextField(); awtField.setEditable(false);
        JLabel averageTurnaroundTime = new JLabel("Average Turnaround Time");
        averageTurnaroundTime.setFont(new Font("Arial", Font.BOLD, 20));
        tatField = new JTextField(); tatField.setEditable(false);
        JButton goBackButton = new JButton("Go Back");
        // table elements

        model = new DefaultTableModel(process.data, column);
        JTable processTable = new JTable(model);
        processTable.setRowHeight(30);
        processTable.setFont(new Font("Arial", Font.PLAIN, 15));
        JLabel tableName = new JLabel("Queue Table");
        tableName.setFont(new Font("Arial", Font.BOLD,20));
        JScrollPane scrollPane = new JScrollPane(processTable); // used to display headers correctly

        // set bounds
        averageWaitingTime.setBounds(50,50,250,30);
        awtField.setBounds(50,100,200,40);
        averageTurnaroundTime.setBounds(50,150,250,30);
        tatField.setBounds(50,200,200,40);
        goBackButton.setBounds(50, 250, 100, 50);
        tableName.setBounds(500, 50, 250, 30);
        scrollPane.setBounds(500,80,800,500);


        panel.add(averageWaitingTime);
        panel.add(awtField);
        panel.add(averageTurnaroundTime);
        panel.add(tatField);
        panel.add(goBackButton);
        panel.add(tableName);
        panel.add(scrollPane);

        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                panel.setVisible(false);
                process.resetValues();
                resetValues(process);
                window.mainPanel.setVisible(true);

            }
        });


        // timer
        timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBurstTime(window, process);
            }
        });
        timerStart();
    }

    public void timerStart() {
        timer.start();
    }


    private void updateBurstTime(Window window, Process process) {
        // fcfs
        if (window.selection == 0) {
            if (currentRow < model.getRowCount()) {
                Object burstTimeObj = model.getValueAt(currentRow, 2);
                int burstTime;
                if (burstTimeObj instanceof String) {
                    burstTime = Integer.parseInt((String) burstTimeObj);
                } else if (burstTimeObj instanceof Integer) {
                    burstTime = (Integer) burstTimeObj;
                } else {
                    throw new IllegalArgumentException("Unexpected value type: " + burstTimeObj.getClass().getName());
                }

                if (burstTime > 0) {
                    currentTime++;
                    model.setValueAt(burstTime - 1, currentRow, 2); // burst time subtraction
                    model.setValueAt("RUNNING", currentRow, 3); // status
                    model.setValueAt(process.waitingTimeArr[currentRow], currentRow, 4); // waiting time
                } else {
                    model.setValueAt("FINISHED", currentRow, 3);
                    model.setValueAt(process.finishTimeArr[currentRow], currentRow, 5); // finish time
                    model.setValueAt(process.turnaroundTimeArr[currentRow], currentRow, 6); // turnaround time
                    currentRow++;
                }
            } else {
                timer.stop();
                tatField.setText(process.averageWT);
                awtField.setText(process.averageTAT);
            }
        }
        // srtf
        else if (window.selection == 1) {

            boolean allBurstTimesComplete = true;

            // check if all burst time is 0
            for (int i=0; i<model.getRowCount(); i++) {
                Object burstTimeObj = model.getValueAt(i, 2); // iteration of each i-th process
                int burstTime;

                if (burstTimeObj instanceof String) {
                    burstTime = Integer.parseInt((String) burstTimeObj);
                } else if (burstTimeObj instanceof Integer) {
                    burstTime = (Integer) burstTimeObj;
                } else {
                    throw new IllegalArgumentException("Unexpected value type: " + burstTimeObj.getClass().getName());
                }

                if (burstTime != 0) {
                    allBurstTimesComplete = false;
                    break;
                }

            }

            System.out.println("currentTime: " + currentTime);
            if (!allBurstTimesComplete) {

                minBurstTime = Integer.MAX_VALUE;
                shortestBTRow = -1;

                // find shortest BT under current time
                for (int i=0; i<model.getRowCount(); i++) {
                    int arrivalTime = process.intData[i][1]; // get arrival time of i-th process
                    Object burstTimeObj = model.getValueAt(i, 2); // get burst time on the table

                    if (burstTimeObj instanceof String) {
                        burstTime = Integer.parseInt((String) burstTimeObj);
                    } else if (burstTimeObj instanceof Integer) {
                        burstTime = (Integer) burstTimeObj;
                    } else {
                        throw new IllegalArgumentException("Unexpected value type: " + burstTimeObj.getClass().getName());
                    }

                    // replaces minBurstTime and shortestBTRow if the current process has a shorter burst time
                    if (arrivalTime <= currentTime && burstTime < minBurstTime && burstTime > 0) {
                        model.setValueAt("WAITING", i, 3);
                        minBurstTime = burstTime;
                        shortestBTRow = i;
                    }
                }


                if (shortestBTRow == -1) { // increment current time if there is no process selected
                    currentTime++;
                }  else { // if a process is selected
                    Object burstTimeObj = model.getValueAt(shortestBTRow, 2);
                    int burstTime;

                    if (burstTimeObj instanceof String) {
                        burstTime = Integer.parseInt((String) burstTimeObj);
                    } else if (burstTimeObj instanceof Integer) {
                        burstTime = (Integer) burstTimeObj;
                    } else {
                        throw new IllegalArgumentException("Unexpected value type: " + burstTimeObj.getClass().getName());
                    }

                    if (burstTime > 0) {
                        burstTime--;
                        model.setValueAt(burstTime, shortestBTRow, 2); // burst time subtraction
                        model.setValueAt("RUNNING", shortestBTRow, 3); // status
                    }

                    if (burstTime == 0) {
                        model.setValueAt("FINISHED", shortestBTRow, 3);
                        model.setValueAt(process.waitingTimeArr[shortestBTRow], shortestBTRow, 4); // waiting time
                        model.setValueAt(process.finishTimeArr[shortestBTRow], shortestBTRow, 5); // finish time
                        model.setValueAt(process.turnaroundTimeArr[shortestBTRow], shortestBTRow, 6); // turnaround time
                    }

                }




                currentTime++;

            } else {
                timer.stop();
                tatField.setText(process.averageWT);
                awtField.setText(process.averageTAT);
            }

        }
    }

    private void resetValues(Process process) {
        timer.stop();
        currentRow = 0;
        awtField.setText("");
        tatField.setText("");

        // clear columns 3, 4, 5, 6
        for (int i=0; i<model.getRowCount(); i++) {
            model.setValueAt("", i, 3);
            model.setValueAt("", i, 4);
            model.setValueAt("", i, 5);
            model.setValueAt("", i, 6);
        }

        // retrieve burst time
        for (int i=0; i<model.getRowCount(); i++) {
            model.setValueAt(process.data[i][2], i, 2);
        }
    }

    public void refreshTable(String[][] data) {
        model.setDataVector(data, column);
    }

    // end of ComputationWindow Class
}
