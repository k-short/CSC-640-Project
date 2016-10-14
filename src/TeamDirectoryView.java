import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View showing the team directory.
 */
public class TeamDirectoryView extends JPanel {
    private final String ADD_BUTTON_LBL = "Add member";
    private JButton editButton;
    private JPanel directoryPane;
    private ActionListener editButtonListener;
    private JButton addNewButton;

    private JPanel directoryPanel;
    private JPanel editMemberPanel;
    private JPanel addMemberPanel;

    /**
     * Constructor.
     * Set up the panel.
     */
    public TeamDirectoryView(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up listener for edit buttons
        editButtonListener = new EditButtonListener();

        //Create and add the directory pane to the panel
        directoryPane = createDirectoryPanel();

        add(directoryPane);
    }

    private JPanel createDirectoryPanel() {
        //Panel to hold the directory scroll pane and an add member button
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Create a text pane to hold documents and edit buttons
        JTextPane directoryTextPane = new JTextPane();
        directoryTextPane.setEditable(false);

        //Create a document to hold text for directory information
        StyledDocument doc = directoryTextPane.getStyledDocument();

        for(int i = 0; i <=12; i++) {
            try {
                doc.insertString(doc.getLength(), dummyText, null);
                directoryTextPane.setCaretPosition(doc.getLength());
                directoryTextPane.insertComponent(new EditButton());
                doc.insertString(doc.getLength(), "\n\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        //Create a scroll pane displaying all of the current members and their information.
        JScrollPane scrollPane = new JScrollPane(directoryTextPane);

        //Create an add member button
        addNewButton = new JButton(ADD_BUTTON_LBL);

        panel.add(scrollPane);
        panel.add(addNewButton);

        return panel;
    }

    /**
     * Pane to show member details, allowing user to edit the details.
     */
    private JPanel createEditPanel(int id){
        //Lable for the form
        String[] labels = {"Name: ", "Job Title: ", "Home Address: ","Home Phone: " ,"Cell Phone: ", "Work Phone: "};
        int numPairs = labels.length;

        //Use the id to access the database and pull the known fields for associated member.
        //Dummy data for now
        String[] fields = {"Kenneth Short", "Lead Car Cleaner", "500 Kale Court, Greensboro, NC 27403",
                           "", "543-345-2222", ""};

        //Create the panel and populate it with the labels and editable fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for(int i = 0; i < numPairs; i++){
            JLabel label = new JLabel(labels[i], JLabel.TRAILING);
            panel.add(label);
            JTextField textField = new JTextField(fields[i], 50);
            textField.setMaximumSize(textField.getPreferredSize());
            label.setLabelFor(textField);
            panel.add(textField);
        }

        return panel;
    }

    /**
     * Class to create edit buttons.
     * Adds the same listener to each button.
     */
    private class EditButton extends JButton{
        public EditButton(){
            super("Edit");
            addActionListener(editButtonListener);
        }
    }

    /**
     * Listener class for the edit button.
     * Removes directory from panel, add editable member information.
     */
    private class EditButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Remove the directory panel
            removeAll();
            revalidate();
            repaint();

            //ID of member to be edited
            //This will be used in createEditPanel() to auto-fill fields
            int id = 1111;

            //Add the edit member panel
            add(createEditPanel(id));
            revalidate();
            repaint();
        }
    }

    String dummyText= "Kenneth Short\n" + "Lead Car Cleaner\n" +"500 Kale Court, Greensboro, NC 27403\n" +
            "543-345-2222\n";
}
