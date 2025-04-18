import javax.swing.*;
import java.awt.*;

public class LonghornNetworkGUI {
    //private static JProgressBar progressBar;
    private JFrame frame;

    public LonghornNetworkGUI() {
        // Force the Look and Feel to use Cross Platform for compat.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Build the interface.
        frame = new JFrame("Longhorn Network");
        JPanel panel = new JPanel();
        //frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new BorderLayout(10, 10));
        //((JPanel) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel label = new JLabel("Welcome to Longhorn Network");
        label.setFont(label.getFont().deriveFont(20f));
        frame.add(label, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        //centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField searchField = new JTextField("Enter a name here and click Search");
        centerPanel.add(searchField);

        //centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton searchButton = new JButton("Search");
        searchButton.setContentAreaFilled(true);
        searchButton.setBackground(new Color(191, 87, 0));
        centerPanel.add(searchButton);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Center the window upon execution
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LonghornNetworkGUI();
    }
}
