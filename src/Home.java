import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Ronin added for homescreen

public class Home {
    JPanel homePanel;
    JFrame mainFrame;
    Window windowInstance;
    JPanel sidePanel;
    JScrollPane scrollPane;

    public Home() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame = new JFrame("CPU Scheduler");
        mainFrame.setSize(800, 600);  // Initial size
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        homePanel = new JPanel();
        homePanel.setBackground(new Color(245, 245, 245));
        homePanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(245, 245, 245));

        ImageIcon titleIcon = new ImageIcon("./Images/Untitled71_20241030090511 (1).png");
        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 245, 245));

        ImageIcon infoIcon = new ImageIcon("./Images/Untitled71_20241030090549.png");
        JLabel infoLabel = new JLabel(infoIcon);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(infoLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(new Color(245, 245, 245));

        // Buttons paths
        JButton whatButton = createHoverImageButton(
                "./Images/what o.png",
                "./Images/what.png");


        JButton whoButton = createHoverImageButton(
                "./Images/who o.png",
                "./Images/who.png");

        whatButton.addActionListener(e -> showWhatSection());
        whoButton.addActionListener(e -> showWhoSection());

        JButton staButton = createHoverImageButton(
                "./Images/Launch.png",
                "./Images/Launch Hover.png");
        staButton.addActionListener(e -> launchApplication());

        JButton date = createHoverImageButton(
                "./Images/date.png",
                "./Images/date.png");


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        buttonsPanel.add(whatButton, gbc);
        buttonsPanel.add(whoButton, gbc);
        buttonsPanel.add(staButton, gbc);
        buttonsPanel.add(date, gbc);

        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setVisible(false);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(sidePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        centerPanel.add(titlePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer
        centerPanel.add(infoPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer
        centerPanel.add(buttonsPanel);

        homePanel.add(centerPanel, BorderLayout.CENTER);
        homePanel.add(scrollPane, BorderLayout.EAST);

        mainFrame.add(homePanel);
        mainFrame.setVisible(true);
    }

    private JButton createHoverImageButton(String normalImagePath, String hoverImagePath) {
        ImageIcon buttonIcon = new ImageIcon(normalImagePath);
        JButton button = new JButton(buttonIcon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon(hoverImagePath));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(buttonIcon);
            }
        });

        return button;
    }

    private void showWhatSection() {
        System.out.println("Showing What Section");
        sidePanel.removeAll();

        // Side panel Back
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> hideSidePanel());

        JLabel imageLabel1 = new JLabel(new ImageIcon("./Images/whayt.png"));
        JLabel imageLabel2 = new JLabel(new ImageIcon("./Images/Justincase.png"));

        sidePanel.add(backButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(imageLabel1);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(imageLabel2);

        sidePanel.setVisible(true);
        scrollPane.setVisible(true);
        sidePanel.revalidate();
        sidePanel.repaint();
        homePanel.revalidate();
        homePanel.repaint();
    }


    private void showWhoSection() {
        System.out.println("Showing Who Section");
        sidePanel.removeAll();


        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> hideSidePanel());

        JLabel imageLabel1 = new JLabel(new ImageIcon("./Images/who1.png"));

        sidePanel.add(backButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(imageLabel1);

        sidePanel.setVisible(true);
        scrollPane.setVisible(true);
        sidePanel.revalidate();
        sidePanel.repaint();
        homePanel.revalidate();
        homePanel.repaint();
    }

    private void hideSidePanel() {
        sidePanel.removeAll();
        sidePanel.setVisible(false);
        scrollPane.setVisible(false);

        homePanel.revalidate();
        homePanel.repaint();
    }

    // Launch Window.java
    private void launchApplication() {
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.remove(homePanel);

        if (windowInstance == null) {
            windowInstance = new Window();
            mainFrame.setVisible(false);
        }
    }
}
