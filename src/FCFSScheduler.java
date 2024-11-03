import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FCFSScheduler extends JFrame {
    private JButton whoButton;
    private JButton whatButton;
    private JButton launchButton;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel namesLabel;
    private JLabel yearLabel;


    public FCFSScheduler() {
        setTitle("FCFS RTF Scheduler");
        setLayout(null);  // Using absolute positioning for exact layout
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Layout components
        layoutComponents();

        // Add hover effects
        setupHoverEffects();
    }

    private void initializeComponents() {
        // Main title components
        titleLabel = new JLabel("FCFS RTF");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(102, 0, 102)); // Purple color

        subtitleLabel = new JLabel("CPU SCHEDULER");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // Project info
        namesLabel = new JLabel("PACETE • ABONITA • A.GARCIA • CPE231");
        namesLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        yearLabel = new JLabel("@2024");
        yearLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Buttons
        whoButton = new JButton("WHO");
        whatButton = new JButton("WHAT");
        launchButton = new JButton("LAUNCH PROJECT");

        styleButton(whoButton);
        styleButton(whatButton);
        styleButton(launchButton);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 80, 90));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    private void layoutComponents() {
        // Set bounds for each component
        titleLabel.setBounds(200, 100, 400, 60);
        subtitleLabel.setBounds(200, 50, 200, 20);
        namesLabel.setBounds(200, 400, 400, 20);
        yearLabel.setBounds(50, 500, 100, 20);

        whoButton.setBounds(650, 450, 100, 30);
        whatButton.setBounds(650, 380, 100, 30);
        launchButton.setBounds(200, 450, 200, 40);

        // Add components to frame
        add(titleLabel);
        add(subtitleLabel);
        add(namesLabel);
        add(yearLabel);
        add(whoButton);
        add(whatButton);
        add(launchButton);
    }

    private void setupHoverEffects() {
        setupButtonHover(whoButton, "default_who.png", "hover_who.png");
        setupButtonHover(whatButton, "default_what.png", "hover_what.png");
    }

    private void setupButtonHover(JButton button, String defaultImage, String hoverImage) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change to hover image
                ImageIcon icon = new ImageIcon(hoverImage);
                button.setIcon(icon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Change back to default image
                ImageIcon icon = new ImageIcon(defaultImage);
                button.setIcon(icon);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FCFSScheduler scheduler = new FCFSScheduler();
            scheduler.setVisible(true);
        });
    }
}