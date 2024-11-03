import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ComputationWindow {
    //unchanged
    ProcessExecution processExecution;
    JPanel panel;
    DefaultTableModel model;
    Timer timer;
    int currentRow;
    String[] column = {"Process No.", "Arrival Time", "Burst Time", "Status", "Waiting Time", "Finish Time", "Turnaround Time"};
    int currentTime = 0;
    int minBurstTime;
    int shortestBTRow;
    int burstTime;
    List<ProcessExecution> executions = new ArrayList<>();
    int previousShortestBTRow = -1;

    JTextField awtField;
    JTextField tatField;

    // screen size of user
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();


    public ComputationWindow(Window window, Process process) {

        //Ronin added to maximize
        panel = new JPanel();
        panel.setPreferredSize(new Dimension((int)screenWidth, (int)screenHeight));
        window.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.revalidate();
        panel.repaint();

        final int previousWindowState = window.mainFrame.getExtendedState();

        // Header Section with Back Button and Title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(50, 30, (int)screenWidth - 100, 50);
        headerPanel.setBackground(Color.WHITE);

        // Back Button modified
        JButton goBackButton = createHoverImageButton(
                "./Images//back o.png",
                "./Images//back.png");
        goBackButton.setFont(new Font("Arial", Font.BOLD, 20));
        goBackButton.setBounds(0, 0, 50, 40);
        goBackButton.setForeground(new Color(102, 0, 102));
        goBackButton.setBorderPainted(false);
        goBackButton.setContentAreaFilled(false);
        goBackButton.setFocusPainted(false);

        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                panel.setVisible(false);
                process.resetValues();
                resetValues(process);
                System.out.println("Reset values.");
                // Restore the previous window state instead of setting to NORMAL
                window.mainFrame.setExtendedState(previousWindowState);
                window.mainPanel.setVisible(true);
            }
        });

        // Title Label
        JLabel titleLabel = new JLabel("OUTPUT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(102, 0, 102));
        titleLabel.setBounds(60, 0, 200, 40);

        headerPanel.add(goBackButton);
        headerPanel.add(titleLabel);

        // Gantt Chart
        executions = new ArrayList<>();
        JPanel ganttChartPanel = new JPanel();
        ganttChartPanel.setLayout(null);
        ganttChartPanel.setBounds(50, 100, (int)screenWidth - 100, 200);
        ganttChartPanel.setBackground(Color.WHITE);

        JLabel ganttChartLabel = new JLabel("GANTT CHART");
        ganttChartLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ganttChartLabel.setForeground(new Color(102, 0, 102));
        ganttChartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ganttChartLabel.setBounds(0, 0, ganttChartPanel.getWidth(), 30);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 5));
        chartPanel.setBounds(100, 40, ganttChartPanel.getWidth() - 200, 100);

        ganttChartPanel.add(ganttChartLabel);
        ganttChartPanel.add(chartPanel);

        // Main Content Section - Adjusted for maximized window
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(50, 320, (int)screenWidth - 100, (int)screenHeight - 400);
        contentPanel.setBackground(Color.WHITE);

        // Table Section
        model = new DefaultTableModel(process.data, column);
        JTable processTable = new JTable(model);
        processTable.setRowHeight(30);
        processTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Customize table header
        JTableHeader header = processTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(new Color(102, 0, 102));
        header.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(processTable);
        scrollPane.setBounds(0, 0, (int)(contentPanel.getWidth() * 0.75), contentPanel.getHeight());

        // Metrics Panel - Adjusted position for maximized window
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(null);
        metricsPanel.setBounds((int)(contentPanel.getWidth() * 0.78), 0,
                (int)(contentPanel.getWidth() * 0.22), 200);
        metricsPanel.setBackground(Color.WHITE);

        // Average Turnaround Time
        JLabel tatLabel = new JLabel("Average Turnaround Time");
        tatLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tatLabel.setForeground(new Color(102, 0, 102));
        tatLabel.setBounds(0, 0, 250, 30);

        tatField = new JTextField();
        tatField.setEditable(false);
        tatField.setBounds(0, 35, 200, 35);
        tatField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Average Waiting Time
        JLabel awtLabel = new JLabel("Average Waiting Time");
        awtLabel.setFont(new Font("Arial", Font.BOLD, 16));
        awtLabel.setForeground(new Color(102, 0, 102));
        awtLabel.setBounds(0, 90, 250, 30);

        awtField = new JTextField();
        awtField.setEditable(false);
        awtField.setBounds(0, 125, 200, 35);
        awtField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        metricsPanel.add(tatLabel);
        metricsPanel.add(tatField);
        metricsPanel.add(awtLabel);
        metricsPanel.add(awtField);

        contentPanel.add(scrollPane);
        contentPanel.add(metricsPanel);

        // Add all panels to main panel
        panel.add(headerPanel);
        panel.add(ganttChartPanel);
        panel.add(contentPanel);

        // Event Listeners
        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                panel.setVisible(false);
                process.resetValues();
                resetValues(process);
                window.mainFrame.setExtendedState(JFrame.NORMAL);
                window.mainPanel.setVisible(true);
            }
        });

        // Timer initialization remains the same
        timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBurstTime(window, process, chartPanel);
            }
        });
        timerStart();
    }

    // all existing methods unchanged
    public void timerStart() {
        timer.start();
    }


    private void updateBurstTime(Window window, Process process, JPanel chartPanel) {
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

                    model.setValueAt(burstTime - 1, currentRow, 2); // burst time subtraction
                    model.setValueAt("RUNNING", currentRow, 3); // status
                    model.setValueAt(process.waitingTimeArr[currentRow], currentRow, 4); // waiting time

                    if (executions.isEmpty() || !executions.get(executions.size() - 1).getProcessNo().equals("P" + (currentRow + 1))) {
                        executions.add(new ProcessExecution("P" + (currentRow + 1), Color.WHITE, Integer.toString(currentTime), process.finishTimeArr[currentRow]));
                        updateGanttChart(chartPanel, executions);
                    }

                    currentTime++;

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
                JOptionPane.showMessageDialog(null, "All processes are finished.", "Finished", JOptionPane.INFORMATION_MESSAGE);
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
                        // add to gantt chart

                        model.setValueAt("WAITING", i, 3);
                        minBurstTime = burstTime;
                        shortestBTRow = i;

                      if (previousShortestBTRow == shortestBTRow) {
                            executions.get(executions.size() - 1).setStopTime(Integer.toString(currentTime));
                            updateGanttChart(chartPanel, executions);
                        }


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

                        // Add to Gantt chart if not already added
                        if (executions.isEmpty() || !executions.get(executions.size() - 1).getProcessNo().equals("P" + (shortestBTRow + 1))) {
                            executions.add(new ProcessExecution("P" + (shortestBTRow + 1), Color.WHITE, Integer.toString(currentTime), Integer.toString(currentTime+1)));
                            updateGanttChart(chartPanel, executions);
                        } else if (previousShortestBTRow == shortestBTRow) {
                            executions.get(executions.size() - 1).setStopTime(Integer.toString(currentTime+1));
                            updateGanttChart(chartPanel, executions);
                        }

                        burstTime--;
                        model.setValueAt(burstTime, shortestBTRow, 2); // burst time subtraction
                        model.setValueAt("RUNNING", shortestBTRow, 3); // status

                        previousShortestBTRow = shortestBTRow;

                    }

                    if (burstTime == 0) {
                        model.setValueAt("FINISHED", shortestBTRow, 3);
                        model.setValueAt(process.waitingTimeArr[shortestBTRow], shortestBTRow, 4); // waiting time
                        model.setValueAt(process.finishTimeArr[shortestBTRow], shortestBTRow, 5); // finish time
                        model.setValueAt(process.turnaroundTimeArr[shortestBTRow], shortestBTRow, 6); // turnaround time

                    }

                    currentTime++;
                }

            } else {
                timer.stop();
                tatField.setText(process.averageWT);
                awtField.setText(process.averageTAT);
                JOptionPane.showMessageDialog(null, "All processes are finished.", "Finished", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private void resetValues(Process process) {
        timer.stop();
        currentRow = 0;
        awtField.setText("");
        tatField.setText("");
        currentTime = 0;

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

        // clear gantt chart
        executions.clear();
    }

    public void refreshTable(String[][] data) {
        model.setDataVector(data, column);
    }

    //added for back button
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


    private void updateGanttChart(JPanel chartPanel, List<ProcessExecution> executions) {
        chartPanel.removeAll(); // Clear existing labels
        for (ProcessExecution execution : executions) {
            JLabel label = new JLabel(execution.getProcessNo(), SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(execution.getColor());
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial", Font.BOLD, 14));

            Border thickBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

            // Create a TitledBorder with start and finish times
            String title = "Start: " + execution.getStartTime() + " Stop: " + execution.getStopTime();
            TitledBorder leftBorder = BorderFactory.createTitledBorder(thickBorder, title);
            leftBorder.setTitlePosition(TitledBorder.ABOVE_TOP);

            // Set the TitledBorder directly to the JLabel
            label.setBorder(leftBorder);

            chartPanel.add(label);
        }

        chartPanel.revalidate();
        chartPanel.repaint();
    }


}
