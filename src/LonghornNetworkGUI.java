import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the front-end or the graphical user interface for 
 * the Longhorn Network application [LN Lab for ECE 422C].
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
     * This constructor creates the main GUI for the Longhorn Network application.
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

        // Build the button to search for a student
        JButton searchButton = new JButton("Search");
        searchButton.setContentAreaFilled(true);
        searchButton.setBackground(new Color(191, 87, 0));
        searchButton.addActionListener(search(searchField));
        buttonPanel.add(searchButton);

        // Build the button to display the student graph
        JButton dispStudentGraphButton = new JButton("Display Student Graph");
        dispStudentGraphButton.setContentAreaFilled(true);
        dispStudentGraphButton.setBackground(new Color(191, 87, 0));
        dispStudentGraphButton.addActionListener(studentGraph());
        buttonPanel.add(dispStudentGraphButton);

        // Run Test Cases button, in other words, run Ayush's test cases.
        JButton runTestCasesButton = new JButton("Run Test Cases");
        runTestCasesButton.setContentAreaFilled(true);
        runTestCasesButton.setBackground(new Color(191, 87, 0));
        runTestCasesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                NetworkLabUI.main(null);
            }
        });
        buttonPanel.add(runTestCasesButton);

        // Now add panels to the frame/window.
        frame.add(searchPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Center the window upon execution and make it visible to the user
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * This method creates a JTextField for the search bar. It sets the placeholder text for 
     * the search bar so users know what to enter. It also sets the font and color of the 
     * text field.
     *
     * @return searchField, the JTextField for the search bar
     * @see JTextField
     * @see FocusAdapter
     * @see FocusEvent
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
     * This helper method builds the menu bar for the GUI. It contains a File menu with options 
     * to open a student data file and exit the application. It also includes a Help menu 
     * with an "About" item. The menu bar is added to the main frame of the application.
     * @see JMenu
     *
     * @param menuBar, the menu bar to be added to the frame
     * @see JMenuBar
     * @param frame, the main frame of the application
     * @see JFrame
     */
    private void buildMenuBar(JMenuBar menuBar, JFrame frame) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Student Data File");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser to select the student data file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Opening Student Data File...");
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showOpenDialog(frame);
                
                // Check if the user selected a file
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
        // Add the menu items to the file menu
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Add the Help menu to the menu bar
        JMenu helpMenu = getHelpMenu(frame);
        menuBar.add(helpMenu);

        // Set the background color of the menu bar
        menuBar.setBackground(new Color(191, 87, 0));
        frame.setJMenuBar(menuBar);
    }

    /**
     * This method creates the Help menu for the GUI. It contains an "About" item that displays 
     * the application version, purpose, and copyright information.
     *
     * @param frame, the main frame of the application
     * @return helpMenu, the Help menu with the "About" item
     * @see JMenu
     * @see JMenuItem
     * @see ActionListener
     * @see JOptionPane
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
     * This method implements the ActionListener for the display student graph button.
     * @see ActionListener
     * @return studentGraph, which is an ActionListener that implements the functionality to display the student graph.
     * @see GraphPanel
     */
    private ActionListener studentGraph() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                // Create the window
                JFrame window = new JFrame("Student Graph");
                window.getContentPane().setLayout(new BorderLayout());
                window.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
                window.setLocationRelativeTo(null);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create the menu bar
                JMenuBar menuBar = new JMenuBar();
                menuBar.setBackground(new Color(191, 87, 0));

                // Create the menu bar with a return button
                JButton returnButton = new JButton("Return to Main Menu");
                returnButton.addActionListener(goHome(window));
                menuBar.add(returnButton);
                window.setJMenuBar(menuBar);

                // Create the graph panel and add it to the window
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
     * This method implements the ActionListener for the return button in the UserInfoWindow and elsewhere.
     *
     * @param window, the window to be closed
     * @see JFrame
     * @see ActionListener
     * @return goHome, which is an ActionListener that implements the functionality to return to the main menu.
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
     * whichever component adds it to its ActionListener.
     * @param searchField, This passes through the text-field to grab the string that the user typed.
     * @return search, which is an ActionListener that implements the search functionality.
     * @see ActionListener
     * @see JTextField
     */
    private ActionListener search(JTextField searchField) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    boolean found = false;

                    // Iterate through the student data to find the student
                    for (UniversityStudent student : studentData) {

                        // Check if the student name matches the search field
                        if (student.name.equalsIgnoreCase(searchField.getText())) {
                            found = true;
                            new UserInfoWindow(student);
                        }
                    }
                    // If the student is not found, show a warning message
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
     * This class implements the GraphPanel that 
     * displays the graph of students in a circular layout using 
     * a @see {@link java.util.HashMap} and ArrayLists to provide the maximum amount of 
     * efficency and quick access times.
     * 
     * @author Abdon Morales, am226923, 
     * <a href="mailto:abdonmorales@my.utexas.edu">abdonmorales@my.utexas.edu</a>
     *
     */
    private class GraphPanel extends JPanel {
        private final java.util.Map<UniversityStudent, Point> coord = new HashMap<>();

        private final int RADIUS = 200;
        private final int NODE_SIZE = 40;

        /**
         * This constructor creates a new GraphPanel object. It sets the preferred
         * size of the panel to accommodate the graph. The size is based on the
         * radius of the circle and the size of the nodes. The panel is used to
         * display the graph in a circular layout.
         */
        public GraphPanel() {
            setPreferredSize(new Dimension(2*RADIUS + NODE_SIZE + 20,
                    2*RADIUS + NODE_SIZE + 20));
        }

        /**
         * This method computes the layout of the graph in a circular manner.
         * It calculates the coordinates of each node in the graph and stores
         * them in a map. The nodes are arranged in a circle with a specified
         * radius. The center of the circle is at the center of the panel.
         */
        private void computerCircLayout() {
            java.util.List<UniversityStudent> nodes = new ArrayList<>(graph.getAllNodes());
            int n = nodes.size();

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            // Calculate the coordinates of each node in a circular layout
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
         * This method is called to paint the component. It draws the graph
         * using the coordinates calculated in the computerCircLayout method.
         * It uses the Graphics2D object to draw the edges and nodes of the graph.
         * 
         * @param g the Graphics object to paint on
         * @see Graphics
         * @see Graphics2D
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            computerCircLayout();

            g2d.setColor(Color.GRAY);
            // Get the coordinates of the nodes
            for (UniversityStudent node : graph.getAllNodes()) {
                Point p1 = coord.get(node);

                // Draw the edges of the graph
                for (StudentGraph.Edge edge : graph.getNeighbors(node)) {
                    UniversityStudent vertex = edge.neighbor;
                    Point p2 = coord.get(vertex);
                    g2d.drawLine(p1.x + NODE_SIZE/2, p1.y + NODE_SIZE/2,
                            p2.x + NODE_SIZE/2, p2.y + NODE_SIZE/2);
                    int midpointX = ((p1.x + NODE_SIZE/2) + (p2.x + NODE_SIZE/2))/2;
                    int midpointY = ((p1.y + NODE_SIZE/2) + (p2.y + NODE_SIZE/2))/2;
                    String weight = String.valueOf(edge.weight);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(weight, midpointX, midpointY);
                }
            }

            // Draw the nodes of the graph
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
     * This class implements the user information window that displays the relevant information of a student. As mentioned in its respective constructor comment. 
     * @see LonghornNetworkGUI.UserInfoWindow
     * @author Abdon Morales, am226923, 
     * <a href="mailto:abdonmorales@my.utexas.edu">abdonmorales@my.utexas.edu</a>
     */
    private class UserInfoWindow {
        Color panelColor = Color.WHITE;

        /**
         * This constructor creates a new window to display the relevant information of 
         * the student. Such as, core data, friends, referrals, and chats.
         * 
         * @param student
         */
        public UserInfoWindow(UniversityStudent student) {
            // Create the window
            JFrame window = new JFrame();
            window.setTitle("Longhorn Network -- " + student.name.trim());
            window.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the tabbed pane to hold the different pages (panels)
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

            // Create the student roommate pairs page
            JPanel roommatePairsPanel = new JPanel();
            roommatePairsPanel.setBackground(panelColor);

            tabbedPane.addTab("Roommate Pairs", roommatePairsPanel);

            // Create the student's referral page
            JPanel ReferralPanel = new JPanel();
            ReferralPanel.setBackground(panelColor);
            
            tabbedPane.addTab("Referrals", ReferralPanel);

            // Create the student's chat page
            JPanel ChatPanel = new JPanel();
            ChatPanel.setBackground(panelColor);
            tabbedPane.addTab("Chats", ChatPanel);

            // Create the student's friends page
            JPanel FriendsPanel = new JPanel(new BorderLayout());
            FriendsPanel.setBackground(panelColor);

            // Build the available friends list, and make sure that people from my Friends
            // are not already in the available friends.
            DefaultListModel<String> availableFriends = new DefaultListModel<>();
            for (UniversityStudent s : studentData) {
                if (s.name.equalsIgnoreCase(student.name)) continue;
                availableFriends.addElement(s.name);
            }
            for (String name : student.getFriends()) {
                availableFriends.removeElement(name);
            }
            JList<String> availableFriendsList = new JList<>(availableFriends);
            // Create the list for the user's my friends.
            DefaultListModel<String> myFriendsListModel = new DefaultListModel<>();
            student.getFriends().forEach(myFriendsListModel::addElement);
            JList<String> myFriendsList = new JList<>(myFriendsListModel);

            // Create the UI for the friends tab with split panes for my Friends and
            // Available Friends.
            JScrollPane myFriendsScrollPane = new JScrollPane(myFriendsList);
            myFriendsScrollPane.setBorder(BorderFactory.createTitledBorder("My Friends"));
            JScrollPane availableFriendsScrollPane = new JScrollPane(availableFriendsList);
            availableFriendsScrollPane.setBorder(BorderFactory.createTitledBorder("Available Friends"));
            JSplitPane friendsPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myFriendsScrollPane
            , availableFriendsScrollPane);
            friendsPane.setResizeWeight(0.5);
            friendsPane.setContinuousLayout(true);
            FriendsPanel.add(friendsPane, BorderLayout.CENTER);
            availableFriendsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int index = availableFriendsList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String selectedName = availableFriends.getElementAt(index);
                        int frendReq = JOptionPane.showConfirmDialog(FriendsPanel, "Send " +
                                "friend request to " + selectedName + "?", "Friend Request",
                                JOptionPane.YES_NO_OPTION);
                        // If the user does for a fact want to send a friend request, then do it.
                        if (frendReq == JOptionPane.YES_OPTION) {
                            UniversityStudent receiver = null;
                            for (UniversityStudent s : studentData) {
                                // If the name/friend is found, then break.
                                if (s.name.equalsIgnoreCase(selectedName)) {
                                    receiver = s;
                                    break;
                                }
                            }
                            // If the student is found then run the fr. req thread and updated
                            // the list.
                            if (receiver != null) {
                                new Thread(new FriendRequestThread(student, receiver)).start();
                                myFriendsListModel.addElement(selectedName);
                                availableFriends.removeElementAt(index);
                            }
                        }
                    }
                }
            });
            tabbedPane.addTab("Friends", FriendsPanel);
            tabbedPane.setBackground(new Color(191, 87, 0));
            window.add(tabbedPane);

            // Create the bottom panel with the exit button
            JPanel buttonPanel = new JPanel();
            JButton exitButton = new JButton("Return Home");
            exitButton.addActionListener(goHome(window));

            // Set the button properties
            buttonPanel.setBackground(new Color(191, 87, 0));
            buttonPanel.add(exitButton);
            window.add(buttonPanel, BorderLayout.SOUTH);

            window.setVisible(true);
        }

        /**
         * This method displays the information of the student in the text area.
         *
         * @param studentInfoTextArea, the text area to display the information
         * @param student, the student object to that contains the information
         * @see UniversityStudent
         * @see JTextArea
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
     * This is the main method that creates an instance of the LonghornNetworkGUI class.
     * @param args, the command line arguments
     * @see LonghornNetworkGUI
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LonghornNetworkGUI::new);
    }
}