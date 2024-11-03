import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

// Ronin added For algorithm chooser design tweak

public class StartWindow extends JDialog {
    int selectedAlgorithm = -1;
    static final Color PURPLE_COLOR = new Color(102, 0, 102);
    static final Color DARK_GRAY = new Color(70, 80, 90);

    public StartWindow(JFrame parent) {
        super(parent, "CHOOSE ALGORITHM", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("CHOOSE ALGORITHM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(PURPLE_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 40));
        centerPanel.setBackground(Color.WHITE);

        // FCFS
        centerPanel.add(createAlgorithmPanel(
                "FCFS",
                "First Come First Serve",
                "Non-preemptive",
                "Simplest CPU scheduling algorithm that schedules according to arrival times of processes. " +
                        "The first come first serve scheduling algorithm states that the process that requests the CPU first is " +
                        "allocated the CPU first.",
                0
        ));

        // SRTF
        centerPanel.add(createAlgorithmPanel(
                "SRTF",
                "Shortest Remaining Time First",
                "Preemptive",
                "A scheduling method that is a preemptive version of shortest job next scheduling. In this " +
                        "scheduling algorithm, the process with the smallest amount of time remaining until completion is " +
                        "selected to execute.",
                1
        ));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    JPanel createAlgorithmPanel(String title, String subtitle, String type, String description, final int algorithmId) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel headerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PURPLE_COLOR);

        JLabel subtitleLabel = new JLabel("• " + subtitle + " •");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(PURPLE_COLOR);

        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        typeLabel.setForeground(PURPLE_COLOR);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        headerPanel.add(typeLabel);

        JLabel descLabel = new JLabel("<html><div style='width: 600px'>" + description + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedAlgorithm = algorithmId;
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(245, 245, 245));
                headerPanel.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE);
                headerPanel.setBackground(Color.WHITE);
            }
        });

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);

        return panel;
    }

    public int getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public static int showDialog(JFrame parent) {
        StartWindow dialog = new StartWindow(parent);
        dialog.setVisible(true);
        return dialog.getSelectedAlgorithm();
    }
}