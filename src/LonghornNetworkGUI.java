import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the front-end or the graphical user interface for
 *
 * @author Abdon Morales, am226923,
 * <a href="mailto:abdonmorales@my.utexas.edu">abdonmorales@my.utexas.edu</a>
 *
 * @version 1.0 Beta
 */
public class LonghornNetworkGUI {
    private JFrame frame;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGTH = 600;
    private List<UniversityStudent> studentData;
    private StudentGraph graph;
    private java.io.File file;
    final String placeholder = "Enter a name here and click Search";

    /// The private and public methods fo LonghornNetworkGUI begin here.
    /**
     *
     */
    public LonghornNetworkGUI() {
        setLookAndFeel();

        // Build the interface.
        frame = new JFrame("Longhorn Network");
        JPanel panel = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildMenuBar(menuBar, frame);

        // Build the top panel to display message and logo
        JLabel label = new JLabel("Welcome to Longhorn Network",
                new ImageIcon("Longhorn.png"), SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(20f));
        panel.add(label, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        // Build center panel to display search bar.
        JPanel searchPanel = new JPanel();
        // Use a vertical BoxLayout so the label appears above the text field
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JLabel searchLabel = new JLabel("NOTE! Load the file first, before searching or " +
                "displaying the graph!");
        searchLabel.setFont(searchLabel.getFont().deriveFont(14f));
        searchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField searchField = getSearchField();
        searchPanel.add(searchLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 5))); // small vertical gap
        searchPanel.add(searchField);

        // Build the bottom panel to display the buttons for search and display student graph
        JPanel buttonPanel = new JPanel();
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

        // Now add panels to the frame/window.
        frame.add(searchPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Center the window upon execution and make it visible to the user
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     *
     * @return
     */
    private JTextField getSearchField() {
        JTextField searchField = new JTextField();
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchField.setText(placeholder);
        Dimension fixedSize = searchField.getPreferredSize();
        searchField.setMaximumSize(fixedSize);
        searchField.setPreferredSize(fixedSize);
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        searchField.setFont(searchField.getFont().deriveFont(14f));
        return searchField;
    }

    /**
     *
     * @param menuBar
     * @param frame
     */
    private void buildMenuBar(JMenuBar menuBar, JFrame frame) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Student Data File");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Opening Student Data File...");
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    try {
                        studentData = DataParser.parseStudents(path);
                        graph = new StudentGraph(studentData);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error reading file:\n" +
                                        ex.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error with the file:\n" +
                                ex.getMessage(), "Internal Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = getHelpMenu(frame);
        menuBar.add(helpMenu);

        menuBar.setBackground(new Color(191, 87, 0));
        frame.setJMenuBar(menuBar);
    }

    /**
     *
     * @param frame
     * @return
     */
    private static JMenu getHelpMenu(JFrame frame) {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About Longhorn Network");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String about = """
                        Longhorn Network 1.0 Beta
                        Built by Abdon Morales for ECE 422C
                        UI: Sun Microsystem Motif v2.5.2, 2009""";
                JOptionPane.showMessageDialog(frame, about,
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutItem);
        return helpMenu;
    }

    /**
     *
     * @return
     */
    private ActionListener studentGraph() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame window = new JFrame("Student Graph");
                window.getContentPane().setLayout(new BorderLayout());
                window.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
                window.setLocationRelativeTo(null);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JMenuBar menuBar = new JMenuBar();
                menuBar.setBackground(new Color(191, 87, 0));
                JButton returnButton = new JButton("Return to Main Menu");
                returnButton.addActionListener(goHome(window));
                menuBar.add(returnButton);

                window.setJMenuBar(menuBar);
                if (studentData != null) {
                     GraphPanel graphPanel = new GraphPanel();
                     window.getContentPane().add(graphPanel, BorderLayout.CENTER);
                }
                window.setVisible(true);
            }
        };
    }

    /**
     * This method sets the GUI Look and Feel; such as the buttons, color, theme, and other
     * miscellaneous items.
     * <p>
     * For this project, and for compatibility, we will be using Sun's Motif Look and Feel interface
     * as seen in Solaris
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
     *
     * @param window
     * @return
     */
    private ActionListener goHome(JFrame window) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                frame.setVisible(true);
            }
        };
    }

    /**
     * This method implements the search functionality for the search button, or
     * whichever component adds it to its ActionListener
     * @param searchField, This passes through the text-field to grab the string that the user typed.
     * @return search
     */
    private ActionListener search(JTextField searchField) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    boolean found = false;
                    for (UniversityStudent student : studentData) {
                        if (student.name.equalsIgnoreCase(searchField.getText())) {
                            found = true;
                            new UserInfoWindow(student);
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(frame,
                                "Student is not found, please try again.",
                                "Warning!", JOptionPane.WARNING_MESSAGE);
                        frame.setVisible(true);
                        frame.toFront();
                        frame.requestFocus();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Internal Error!\n" +
                            ex.getMessage() + "\nERR 000", "Internal Error", JOptionPane.ERROR_MESSAGE);
                    frame.setVisible(true);
                    frame.toFront();
                    frame.requestFocus();
                }
            }
        };
    }

    /// Private and public methods of LonghornNetworkGUI end here. ///

    /**
     *
     */
    private class GraphPanel extends JPanel {
        private final java.util.Map<UniversityStudent, Point> coord = new HashMap<>();

        private final int RADIUS = 200;
        private final int NODE_SIZE = 40;

        /**
         *
         */
        public GraphPanel() {
            setPreferredSize(new Dimension(2*RADIUS + NODE_SIZE + 20,
                    2*RADIUS + NODE_SIZE + 20));
        }

        /**
         *
         */
        private void computerCircLayout() {
            java.util.List<UniversityStudent> nodes = new ArrayList<>(graph.getAllNodes());
            int n = nodes.size();

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            for (int i = 0; i < n; i++) {
                UniversityStudent node = nodes.get(i);

                // Map from Polar to Cartesian
                double theta = 2 * Math.PI * i / n;
                int x = centerX + (int)(RADIUS * Math.cos(theta)) - NODE_SIZE/2;
                int y = centerY + (int)(RADIUS * Math.sin(theta)) - NODE_SIZE/2;
                coord.put(node, new Point(x, y));
            }
        }

        /**
         *
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            computerCircLayout();

            g2d.setColor(Color.GRAY);
            //
            for (UniversityStudent node : graph.getAllNodes()) {
                Point p1 = coord.get(node);

                //
                for (StudentGraph.Edge edge : graph.getNeighbors(node)) {
                    UniversityStudent vertex = edge.neighbor;
                    Point p2 = coord.get(vertex);
                    g2d.drawLine(p1.x + NODE_SIZE/2, p1.y + NODE_SIZE/2,
                            p2.x + NODE_SIZE/2, p2.y + NODE_SIZE/2);
                }
            }

            //
            for (UniversityStudent node : graph.getAllNodes()) {
                Point p1 = coord.get(node);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p1.x, p1.y, NODE_SIZE, NODE_SIZE);

                String name = node.name;
                FontMetrics fm = g2d.getFontMetrics();
                int tx = p1.x + (NODE_SIZE - fm.stringWidth(name)) / 2;
                int ty = p1.y + ((NODE_SIZE - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(name, tx, ty);
            }
        }
    }

    /**
     *
     */
    private class UserInfoWindow {
        Color panelColor = Color.WHITE;

        /**
         *
         * @param student
         */
        public UserInfoWindow(UniversityStudent student) {
            JFrame window = new JFrame();
            window.setTitle("Longhorn Network -- " + student.name.trim());
            window.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Create the student's information page
            JPanel StudentInformationPanel = new JPanel();
            StudentInformationPanel.setBackground(panelColor);
            JTextArea studentInfoTextArea = new JTextArea(BorderLayout.WEST);
            studentInfoTextArea.setEditable(false);
            studentInfoTextArea.setText("");
            displayStudentInfo(studentInfoTextArea,student);
            StudentInformationPanel.add(studentInfoTextArea);
            tabbedPane.addTab("Student Information", StudentInformationPanel);


            // Create the student's referral page
            JPanel ReferralPanel = new JPanel();
            ReferralPanel.setBackground(panelColor);
            tabbedPane.addTab("Referrals", ReferralPanel);

            // Create the student's chat page
            JPanel ChatPanel = new JPanel();
            ChatPanel.setBackground(panelColor);
            tabbedPane.addTab("Chats", ChatPanel);

            // Create the student's friends page
            JPanel FriendsPanel = new JPanel();
            FriendsPanel.setBackground(panelColor);
            tabbedPane.addTab("Friends", FriendsPanel);
            tabbedPane.setBackground(new Color(191, 87, 0));
            window.add(tabbedPane);

            //
            JPanel buttonPanel = new JPanel();
            JButton exitButton = new JButton("Return Home");
            exitButton.addActionListener(goHome(window));

            buttonPanel.setBackground(new Color(191, 87, 0));
            buttonPanel.add(exitButton);
            window.add(buttonPanel, BorderLayout.SOUTH);

            window.setVisible(true);
        }

        /**
         *
         * @param studentInfoTextArea
         * @param student
         */
        private static void displayStudentInfo (JTextArea studentInfoTextArea,
                                                UniversityStudent student) {
            studentInfoTextArea.append("Name: " + student.name + "\n");
            studentInfoTextArea.append("Age: " + student.age + "\n");
            studentInfoTextArea.append("Gender: " + student.gender + "\n");
            studentInfoTextArea.append("Year: " + student.year + "\n");
            studentInfoTextArea.append("GPA: " + student.gpa + "\n");
            studentInfoTextArea.append("Major: " + student.major + "\n");
            studentInfoTextArea.setFont(studentInfoTextArea.getFont().deriveFont(14f));
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new LonghornNetworkGUI();
    }
}