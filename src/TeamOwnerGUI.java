import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.TabExpander;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;

/**
 * Main GUI class.
 */
public class TeamOwnerGUI {
    //Frame title
    private final String FRAME_TITLE = "Team Owner";

    //Frame to hold all panels
    private JFrame frame;

    //Menu button fonts
    private final Font PRESSED_FONT = new Font(Font.DIALOG,Font.BOLD, 16);
    private final Font UNPRESSED_FONT = new Font(Font.DIALOG, Font.PLAIN, 16);

    //Button labels
    private final String DIR_BUTTON_LBL = "Team Directory";
    private final String SCH_BUTTON_LBL = "Event Schedule";
    private final String FUNDS_BUTTON_LBL = "Team Funds";
    private final String EXP_BUTTON_LBL = "Expense Requests";

    //Menu buttons
    private JButton directoryButton;
    private JButton scheduleButton;
    private JButton fundsButton;
    private JButton expenseButton;

    //Spacing between menu items
    private final int MENU_ITEM_SPACE = 30;

    /**
     * Default constructor for the GUI.
     */
    public TeamOwnerGUI(){
        //Get the frame ready
        frame = new JFrame();
        frame.setTitle(FRAME_TITLE);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Build the button menu and add it to the frame
        JMenuBar navMenuBar = buildNavMenu();

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.add(navMenuBar, BorderLayout.LINE_START);
        contentPane.add(new TeamDirectoryView());

        //Display the window
        frame.pack();
        frame.setLocationRelativeTo(null); // Center frame on screen
        frame.setVisible(true);
    }

    /**
     * Build the button panel to hold buttons that the use can select
     * to navigate to other panels of the GUI.
     */
    private JMenuBar buildNavMenu(){
        //Create menu bar to hold the menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.PAGE_AXIS));

        //Build a menu to hold buttons
        JMenu  menu = new JMenu();

        //Create a button for each of the other panels
        directoryButton = createMenuButton(DIR_BUTTON_LBL);
        directoryButton.addActionListener(new TeamDirectoryButtonListener());

        scheduleButton = createMenuButton(SCH_BUTTON_LBL);
        scheduleButton.addActionListener(new EventScheduleButtonListener());

        fundsButton = createMenuButton(FUNDS_BUTTON_LBL);
        fundsButton.addActionListener(new TeamFundsButtonListener());

        expenseButton = createMenuButton(EXP_BUTTON_LBL);
        expenseButton.addActionListener(new ExpenseRequestButtonListener());

        //Add each of the buttons to the button panel
        menuBar.add(Box.createRigidArea(new Dimension(0, MENU_ITEM_SPACE)));
        menuBar.add(scheduleButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, MENU_ITEM_SPACE)));
        menuBar.add(directoryButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, MENU_ITEM_SPACE)));
        menuBar.add(fundsButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, MENU_ITEM_SPACE)));
        menuBar.add(expenseButton);

        //Set the directory button as default pressed font
        setPressedFont(scheduleButton);

        return menuBar;
    }

    /**
     * Private class for team directory button listener.
     * Display the team directory panel.
     */
    private class TeamDirectoryButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setPressedFont(directoryButton);
        }
    }

    /**
     * Private class for event schedule button listener.
     * Display the event schedule panel.
     */
    private class EventScheduleButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setPressedFont(scheduleButton);
        }
    }

    /**
     * Private class for team funds button listener.
     * Display the team funds panel.
     */
    private class TeamFundsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setPressedFont(fundsButton);
        }
    }

    /**
     * Private class for expense request button listener.
     * Display the expense request panel.
     */
    private class ExpenseRequestButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setPressedFont(expenseButton);
        }
    }

    /**
     * Creates a menu button.
     * The button will be displayed as just text.
     */
    private JButton createMenuButton(String label) {
        JButton button = new JButton(label);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(UNPRESSED_FONT);

        Dimension dim = new Dimension(button.getPreferredSize().width + 15, button.getPreferredSize().height);
        button.setPreferredSize(dim);

        return button;
    }

    /**
     * Set the given button to pressed.
     * This simply changes the fonts of the buttons.
     * Pressed button is bold, unpressed are plain.
     */
    private void setPressedFont(JButton button){
        //Set pressed button to pressed font and others to unpressed font.
        if(button.equals(directoryButton)){
            directoryButton.setFont(PRESSED_FONT);
            scheduleButton.setFont(UNPRESSED_FONT);
            fundsButton.setFont(UNPRESSED_FONT);
            expenseButton.setFont(UNPRESSED_FONT);
        }else if(button.equals(scheduleButton)){
            directoryButton.setFont(UNPRESSED_FONT);
            scheduleButton.setFont(PRESSED_FONT);
            fundsButton.setFont(UNPRESSED_FONT);
            expenseButton.setFont(UNPRESSED_FONT);
        }else if(button.equals(fundsButton)){
            directoryButton.setFont(UNPRESSED_FONT);
            scheduleButton.setFont(UNPRESSED_FONT);
            fundsButton.setFont(PRESSED_FONT);
            expenseButton.setFont(UNPRESSED_FONT);
        }else if(button.equals(expenseButton)){
            directoryButton.setFont(UNPRESSED_FONT);
            scheduleButton.setFont(UNPRESSED_FONT);
            fundsButton.setFont(UNPRESSED_FONT);
            expenseButton.setFont(PRESSED_FONT);
        }

    }
}
