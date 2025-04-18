import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LonghornNetworkGUI {
    private JFrame frame;

    public LonghornNetworkGUI() {
        setLookAndFeel();

        // Build the interface.
        frame = new JFrame("Longhorn Network");
        JPanel panel = new JPanel();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel = new JPanel();
        JLabel label = new JLabel("Welcome to Longhorn Network");
        label.setFont(label.getFont().deriveFont(20f));
        JComponent imageComponent = new JLabel(new ImageIcon("Longhorn.png"));
        // TODO: Fix the components layout issue with the image and label.
        panel.add(label, BorderLayout.CENTER);
        panel.add(imageComponent);
        frame.add(panel, BorderLayout.NORTH);

        JTextField searchField = new JTextField("Enter a name here and click Search");
        searchField.setFont(searchField.getFont().deriveFont(14f));
        centerPanel.add(searchField, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton searchButton = new JButton("Search");
        searchButton.setContentAreaFilled(true);
        searchButton.setBackground(new Color(191, 87, 0));
        searchButton.addActionListener(search(searchField));
        buttonPanel.add(searchButton);

        JButton dispStudentGraphButton = new JButton("Display Student Graph");
        dispStudentGraphButton.setContentAreaFilled(true);
        dispStudentGraphButton.setBackground(new Color(191, 87, 0));
        dispStudentGraphButton.addActionListener(studentGraph());
        buttonPanel.add(dispStudentGraphButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Center the window upon execution
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ActionListener studentGraph() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame window = new JFrame("Student Graph");
                window.setSize(800, 600);
                window.setLocationRelativeTo(null);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel = new JPanel();

                window.setVisible(true);
            }
        };
    }

    /**
     *
     */
    private static void setLookAndFeel() {
        // We are going Old-Skool here, back to the good ol' Sun days.
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            System.err.println("Failed to set look and feel: " +
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }
    }

    /**
     * @param searchField
     * @return
     */
    private ActionListener search(JTextField searchField) {

        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserInfoWindow(searchField.getText());
            }
        };
    }

    public class UserInfoWindow {

        public UserInfoWindow(String name) {
            JFrame window = new JFrame();
            window.setTitle("Longhorn Network -- " + name.trim());
            window.setSize(800, 600);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            //
            JPanel StudentInformationPanel = new JPanel();
            tabbedPane.addTab("Student Information", StudentInformationPanel);

            //
            JPanel ReferralPanel = new JPanel();
            tabbedPane.addTab("Referrals", ReferralPanel);

            //
            JPanel ChatPanel = new JPanel();
            tabbedPane.addTab("Chats", ChatPanel);

            //
            JPanel FriendsPanel = new JPanel();
            tabbedPane.addTab("Friends", FriendsPanel);

            JButton returnButton = new JButton("Return to Main Menu");
            returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    window.dispose();
                    frame.setVisible(true);
                }
            });

            window.add(tabbedPane);
            window.setVisible(true);
        }
    }


    public static void main(String[] args) {
        new LonghornNetworkGUI();
    }
}
