import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oracle.jrockit.jfr.StringConstantPool;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ken12_000 on 10/21/2016.
 */
public class TeamOwnerGUI extends Stage{
    private final Font MENU_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);
    private final Font MENU_OPTION_FONT_UNSEL = Font.font("Arial", FontWeight.NORMAL, 18);
    private final Font MENU_OPTION_FONT_SEL = Font.font("Arial", FontWeight.BOLD, 18);
    private final Font LABEL_FONT = Font.font("Arial", FontWeight.BOLD, 18);
    private final Font TEXT_FONT = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Insets CENTER_INSETS = new Insets(10, 5, 5, 45);
    private final int FORM_VGAP = 30;
    private final int FORM_HGAP = 10;
    private final int BUTTON_WIDTH = 160;
    private final int BUTTON_HEIGHT = 40;
    private final int TEXT_FIELD_WIDTH = 250;
    private BorderPane borderPane;

    String dummyExpense1 = "Item:\t\tTires\nCost:\t\t$2000\nTimeline:\t\tImmediate\nPriority:\t\tNormal\n";
    String dummyExpense2 = "Item:\t\tFront Springs\nCost:\t\t$1200\nTimeline:\t\t1 Week\nPriority:\t\tHigh\n";

    //Management objects and associated lists
    private EventScheduleManagement eventMgmt = new EventScheduleManagement();
    private TeamDirectoryManagement dirMgmt = new TeamDirectoryManagement();
    private TeamFundsManagement fundsMgmt = new TeamFundsManagement();
    private ArrayList<TeamEvent> eventList;
    private ArrayList<DirectoryMember> directory;
    private ArrayList<Transaction> transactions;
    private Double totalFunds;

    //Text fields to hold information to be added to serialized files
    private TextField eventTitleField;
    private TextField eventSpeedwayField;
    private TextField eventLocationField;
    private TextField eventDetailsField;

    private TextField dirNameField;
    private TextField dirJobTitleField;
    private TextField dirAddressField;
    private TextField dirPhoneNumField;

    private TextField addFundsTextField;
    private TextField removeFundsTextField;

    //Data holders for choice boxes in add/edit event methods.
    private int eventMonth;
    private int eventDay;
    private int eventHour;
    private int eventMinute;
    private String eventAMPM;

    //This is the index of the event/directory being edited
    private int editingIndex;

    public TeamOwnerGUI(){
        //Use a border pane as the root for the scene
        borderPane = new BorderPane();
        //borderPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        borderPane.setTop(topHBox());

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(addSideMenuVBox());

        //Create grid pane to display main info, for the center of the border pane.
        borderPane.setCenter(eventSchedulePane());

        //Blank HBox at bottom for border
        borderPane.setBottom(eventScheduleButtonPanel());

        //Right border
        VBox rightBorder = new VBox();
        rightBorder.setPrefWidth(30);
        rightBorder.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setRight(rightBorder);

        //Create and show scene
        Scene scene = new Scene(borderPane);
        setScene(scene);
        setTitle("Team Owner Interface");
        setMaximized(true);
    }

    /**
     * Create a VBox to hold the menu on left side of border pane
     */
    private VBox addSideMenuVBox(){
        VBox menu = new VBox();
        menu.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        //Create menu buttons
        Button schedule = new Button("Event Schedule");
        Button dir = new Button("Team Directory");
        Button funds = new Button("Team Funds");
        Button req = new Button("Expense Requests");

        //Array of menu option buttons
        Button[] menuOptions = new Button[]{schedule, dir, funds, req};

        //Set the font to each button and remove background
        for(Button b : menuOptions){
            b.setBackground(Background.EMPTY);
            b.setFont(MENU_OPTION_FONT_UNSEL);
        }

        //Set schedule button to selected to start
        schedule.setFont(MENU_OPTION_FONT_SEL);
        schedule.setTextFill(Color.BLUE);

        //Add events to buttons for when clicked
        //Changed selected button text to blue, others to black
        schedule.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLUE);
            dir.setTextFill(Color.BLACK);
            funds.setTextFill(Color.BLACK);
            req.setTextFill(Color.BLACK);

            schedule.setFont(MENU_OPTION_FONT_SEL);
            dir.setFont(MENU_OPTION_FONT_UNSEL);
            funds.setFont(MENU_OPTION_FONT_UNSEL);
            req.setFont(MENU_OPTION_FONT_UNSEL);

            borderPane.setCenter(eventSchedulePane());
            borderPane.setBottom(eventScheduleButtonPanel());
        });

        dir.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLACK);
            dir.setTextFill(Color.BLUE);
            funds.setTextFill(Color.BLACK);
            req.setTextFill(Color.BLACK);

            schedule.setFont(MENU_OPTION_FONT_UNSEL);
            dir.setFont(MENU_OPTION_FONT_SEL);
            funds.setFont(MENU_OPTION_FONT_UNSEL);
            req.setFont(MENU_OPTION_FONT_UNSEL);

            borderPane.setCenter(directoryPane());
            borderPane.setBottom(directoryButtonPanel());
        });

        funds.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLACK);
            dir.setTextFill(Color.BLACK);
            funds.setTextFill(Color.BLUE);
            req.setTextFill(Color.BLACK);

            schedule.setFont(MENU_OPTION_FONT_UNSEL);
            dir.setFont(MENU_OPTION_FONT_UNSEL);
            funds.setFont(MENU_OPTION_FONT_SEL);
            req.setFont(MENU_OPTION_FONT_UNSEL);

            borderPane.setCenter(fundsPane());
            borderPane.setBottom(fundsButtonPanel());
        });

        req.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLACK);
            dir.setTextFill(Color.BLACK);
            funds.setTextFill(Color.BLACK);
            req.setTextFill(Color.BLUE);

            schedule.setFont(MENU_OPTION_FONT_UNSEL);
            dir.setFont(MENU_OPTION_FONT_UNSEL);
            funds.setFont(MENU_OPTION_FONT_UNSEL);
            req.setFont(MENU_OPTION_FONT_SEL);

            borderPane.setCenter(expenseRequestPane());
            HBox blankHBox = new HBox();
            blankHBox.setPrefHeight(30);
            blankHBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            borderPane.setBottom(blankHBox);
        });

        //Add each of the menu options to the vbox
        for (int i=0; i<4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(menuOptions[i], new Insets(0, 0, 0, 40));
            menu.getChildren().add(menuOptions[i]);
        }
        return menu;
    }

    /**
     * Create a grid pane to show information based on menu option currently selected.
     */
    private ScrollPane directoryPane(){
        GridPane gridPane = new GridPane();

        //Get directory
        directory = dirMgmt.getDirectory();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(CENTER_INSETS);

        //There is at least one directory member
        if(directory.size() > 0) {
            //Add event to each button -> go to edit form page for name
            EventHandler<ActionEvent> dirNamePress = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button btn = (Button) event.getSource();
                    int id = Integer.parseInt(btn.getId());
                    editingIndex = id;
                    borderPane.setCenter(editDirectoryForm(directory.get(id)));
                    borderPane.setBottom(editDirectoryButtonPanel());
                }
            };

            //Buttons for directory members
            Button[] dirButtons = new Button[directory.size()];
            for (int i = 0; i < dirButtons.length; i++) {
                dirButtons[i] = new Button(directory.get(i).getName());
                dirButtons[i].setId("" + i);
                dirButtons[i].setPadding(new Insets(0, 0, 0, 0));
            }

            //Apply settings to each button: font, action event, background
            for (Button b : dirButtons) {
                //Set fonts
                b.setFont(LABEL_FONT);
                b.setBackground(Background.EMPTY);
                b.setOnAction(dirNamePress);
                b.setPadding(new Insets(0, 0, 0, 0));
                b.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                b.setTextFill(Color.BLUE);
                            }
                        });

                //Removing the shadow when the mouse cursor is off
                b.addEventHandler(MouseEvent.MOUSE_EXITED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                b.setTextFill(Color.BLACK);
                            }
                        });
            }

            //Create Text arrays for each directory field
            Text[] dirName = new Text[directory.size()];
            for (int i = 0; i < directory.size(); i++) {
                dirName[i] = new Text(directory.get(i).getName());
                dirName[i].setFont(TEXT_FONT);
            }

            Text[] dirJobTitle = new Text[directory.size()];
            for (int i = 0; i < directory.size(); i++) {
                dirJobTitle[i] = new Text(directory.get(i).getJobTitle());
                dirJobTitle[i].setFont(TEXT_FONT);
            }

            Text[] dirAddress = new Text[directory.size()];
            for (int i = 0; i < directory.size(); i++) {
                dirAddress[i] = new Text(directory.get(i).getAddress());
                dirAddress[i].setFont(TEXT_FONT);
            }

            Text[] dirPhoneNum = new Text[directory.size()];
            for (int i = 0; i < directory.size(); i++) {
                dirPhoneNum[i] = new Text(directory.get(i).getPhoneNumber());
                dirPhoneNum[i].setFont(TEXT_FONT);
            }

            //Add each directory name button followed by directory details for that name
            int counter = 0;
            for (int i = 0; i < directory.size() * 2; i += 2) {
                //Create member details Text
                Text details = new Text(dirJobTitle[counter].getText() + "\n" + dirAddress[counter].getText() + "\n" +
                        dirPhoneNum[counter].getText() + "\n");
                details.setFont(TEXT_FONT);

                //Add name button and details to grid pane
                gridPane.add(dirButtons[counter], 0, i);
                gridPane.add(details, 0, i + 1);

                //Add a seperator between directory members
                Separator separator = new Separator();
                gridPane.setValignment(separator, VPos.BOTTOM);
                gridPane.add(separator, 0, i + 1);

                counter++;
            }
        }//end if
        else{//No directory member, display message to user
            directory = new ArrayList<>(); //create default empty list
            Text noDir = new Text("There are no directory members.");
            noDir.setFont(LABEL_FONT);
            gridPane.add(noDir, 0 ,0);
        }

        //Add the grid pane to a scroll pane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return scrollPane;
    }

    /**
     * Create an HBox to hold buttons needed to interact with the information
     * being displayed in the center of the border pane.
     */
    private HBox directoryButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button addButton = new Button("Add Member");
        addButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        //If edit button is selected, switch to show dir change form and buttons
        addButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addMemberForm());
            borderPane.setBottom(addMemberButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().add(addButton);

        return buttonPanel;
    }

    /**
     * Create a form for adding a new event
     */
    private GridPane addMemberForm(){
        GridPane gridPane = new GridPane();
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);
        gridPane.setPadding(CENTER_INSETS);

        //Lables for each field
        Label[] labels = new Label[]{
                new Label("Name:"),
                new Label("Job Title:"),
                new Label("Home Address:"),
                new Label("Phone Number:")
        };

        //Set font to each label
        for(Label l : labels){
            l.setFont(TEXT_FONT);
        }

        dirNameField = new TextField();
        dirJobTitleField = new TextField();
        dirAddressField = new TextField();
        dirPhoneNumField = new TextField();

        TextField[] defaultFields = new TextField[]{dirNameField, dirJobTitleField,
                                                dirAddressField, dirPhoneNumField};

        for(TextField t : defaultFields){
            t.setPrefWidth(TEXT_FIELD_WIDTH);
        }

        //Add events to grid
        //Set font to each Text
        for(int i = 0; i < labels.length; i++){
            labels[i].setFont(TEXT_FONT);

            gridPane.add(labels[i], 0, i);
            gridPane.add(defaultFields[i], 1, i);
        }

        return gridPane;
    }

    /**
     * Buttons for add event form.
     */
    private HBox addMemberButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Add Member");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(directoryPane());
            borderPane.setBottom(directoryButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            DirectoryMember newMember = new DirectoryMember(
                    dirNameField.getText(),
                    dirJobTitleField.getText(),
                    dirAddressField.getText(),
                    dirPhoneNumField.getText()
            );
            directory.add(newMember);
            dirMgmt.updateDirectory(directory);

            borderPane.setCenter(directoryPane());
            borderPane.setBottom(directoryButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }


    /**
     * Create a form using a grid pane.
     */
    private GridPane editDirectoryForm(DirectoryMember member){
        GridPane gridPane = new GridPane();
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);
        gridPane.setPadding(CENTER_INSETS);

        //Lables for each field
        Label[] labels = new Label[]{
                new Label("Name:"),
                new Label("Job Title:"),
                new Label("Home Address:"),
                new Label("Phone Number:")
        };

        dirNameField = new TextField(member.getName());
        dirJobTitleField = new TextField(member.getJobTitle());
        dirAddressField = new TextField(member.getAddress());
        dirPhoneNumField = new TextField(member.getPhoneNumber());

        TextField[] defaultFields = new TextField[]{dirNameField, dirJobTitleField, dirAddressField, dirPhoneNumField};


        for(TextField t : defaultFields){
            t.setPrefWidth(TEXT_FIELD_WIDTH);
        }

        //Add each label to grid pane
        //Set normal text font to each
        for(int i = 0; i < labels.length; i++){
            labels[i].setFont(TEXT_FONT);

            gridPane.add(labels[i], 0, i);
            gridPane.add(defaultFields[i], 1, i);
        }

        return gridPane;
    }

    /**
     * Create an hbox to hold buttons to interact with a directory member edit form.
     */
    private HBox editDirectoryButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(directoryPane());
            borderPane.setBottom(directoryButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            DirectoryMember updatedMem = directory.get(editingIndex);
            updatedMem.setName(dirNameField.getText());
            updatedMem.setJobTitle(dirJobTitleField.getText());
            updatedMem.setAddress(dirAddressField.getText());
            updatedMem.setPhoneNumber(dirPhoneNumField.getText());
            dirMgmt.updateDirectory(directory);

            borderPane.setCenter(directoryPane());
            borderPane.setBottom(directoryButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create a grid panel to hold all events.  Wrap in a scroll pane.
     */
    private ScrollPane eventSchedulePane(){
        GridPane gridPane = new GridPane();

        //Get event list
        eventList = eventMgmt.getEventList();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(CENTER_INSETS);

        //There is at least one event
        if(eventList.size() > 0) {
            //Event for each button press -> go to edit form page for event
            //Retrieve the TeamEvent that was pressed by getting button id, which directly maps to the index
            //in eventList for the event
            EventHandler<ActionEvent> eventTitlePress = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button btn = (Button) event.getSource();
                    int id = Integer.parseInt(btn.getId());
                    editingIndex = id;
                    borderPane.setCenter(editEventForm(eventList.get(id)));
                    borderPane.setBottom(editEventButtonPanel());
                }
            };

            //Buttons for event titles
            Button[] eventButtons = new Button[eventList.size()];
            for (int i = 0; i < eventButtons.length; i++) {
                eventButtons[i] = new Button(eventList.get(i).getTitle());
                eventButtons[i].setId("" + i);
                eventButtons[i].setPadding(new Insets(0, 0, 0, 0));
            }

            //Apply settings to each button: font, action event, background
            for (Button b : eventButtons) {
                //Set fonts
                b.setFont(LABEL_FONT);
                b.setBackground(Background.EMPTY);
                b.setOnAction(eventTitlePress);
                b.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                b.setTextFill(Color.BLUE);
                            }
                        });

                //Removing the shadow when the mouse cursor is off
                b.addEventHandler(MouseEvent.MOUSE_EXITED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                b.setTextFill(Color.BLACK);
                            }
                        });
            }

            Text[] eventTitles = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventTitles[i] = new Text(eventList.get(i).getTitle());
                eventTitles[i].setFont(TEXT_FONT);
            }

            Text[] eventSpeedways = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventSpeedways[i] = new Text(eventList.get(i).getSpeedway());
                eventSpeedways[i].setFont(TEXT_FONT);
            }

            Text[] eventLocations = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventLocations[i] = new Text(eventList.get(i).getLocation());
                eventLocations[i].setFont(TEXT_FONT);
            }

            Text[] eventDates = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventDates[i] = new Text(eventList.get(i).getDate());
                eventDates[i].setFont(TEXT_FONT);
            }

            Text[] eventTimes = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventTimes[i] = new Text(eventList.get(i).getTime());
                eventTimes[i].setFont(TEXT_FONT);
            }

            Text[] eventDetails = new Text[eventList.size()];
            for (int i = 0; i < eventTitles.length; i++) {
                eventDetails[i] = new Text(eventList.get(i).getDetails());
                eventDetails[i].setFont(TEXT_FONT);
            }

            //Add each event to the grid pane
            //Add event title button above each event detail
            int counter = 0;
            for (int i = 0; i < eventTitles.length * 2; i += 2) {
                //Create event string
                Text eventInfo = new Text(eventSpeedways[counter].getText() + "\n" + eventLocations[counter].getText() + "\n" +
                        eventDates[counter].getText() + "\n" + eventTimes[counter].getText() + "\n" +
                        eventDetails[counter].getText() + "\n");
                eventInfo.setFont(TEXT_FONT);

                //Add each title and details to grid pane
                gridPane.add(eventButtons[counter], 0, i);

                int index = i + 1;
                gridPane.add(eventInfo, 0, index);

                //Add a separator between events
                Separator separator = new Separator();
                gridPane.setValignment(separator, VPos.BOTTOM);
                gridPane.add(separator, 0, index);

                counter++;
            }
        }//end if
        else{ //No events, make empty list to add to and display message to user.
            eventList = new ArrayList<>();
            Text noEvents = new Text("There are no scheduled events.");
            noEvents.setFont(LABEL_FONT);
            gridPane.add(noEvents, 0, 0);
        }

        //Add the grid pane to a scroll pane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return scrollPane;
    }

    /**
     * Create HBox to hold buttons for event schedule.
     */
    private HBox eventScheduleButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button addEventButton = new Button("Add Event");
        addEventButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        //If edit button is selected, switch to show event change form and buttons
        addEventButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventForm());
            borderPane.setBottom(addEventButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().add(addEventButton);

        return buttonPanel;
    }

    /**
     * Create a form for adding a new event
     */
    private GridPane addEventForm(){
        GridPane gridPane = new GridPane();
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);
        gridPane.setPadding(CENTER_INSETS);

        //Lables for each field
        Label[] labels = new Label[]{
                new Label("Event Name:"),
                new Label("Speedway:"),
                new Label("Location:")
        };

        //Set font to each label
        for(Label l : labels){
            l.setFont(TEXT_FONT);
        }

        //Assign TextFields
        eventTitleField = new TextField();
        eventSpeedwayField = new TextField();
        eventLocationField = new TextField();
        eventDetailsField = new TextField();

        //Each field with default text already in directory (dummy text for now)
        //For first 3 fields in form
        TextField[] defaultFields = new TextField[]{eventTitleField, eventSpeedwayField, eventLocationField};

        for(TextField t : defaultFields){
            t.setPrefWidth(TEXT_FIELD_WIDTH);
        }

        //Make choice box for date (month)
        final ChoiceBox monthBox = new ChoiceBox(FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October",
                "November", "December"));
        monthBox.getSelectionModel().selectFirst();
        eventMonth = 1;

        ChoiceBox dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31"));
        dayBox.getSelectionModel().selectFirst();
        eventDay = 1;

        monthBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventMonth = newValue.intValue() + 1;
                ChoiceBox dayBox;
                if(eventMonth == 2){
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28"));
                }else if(eventMonth == 4 || eventMonth == 6 || eventMonth == 9 || eventMonth == 11){
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28", "29", "30"));
                }else{
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28", "29", "30", "31"));
                }
                dayBox.getSelectionModel().selectFirst();
                dayBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        eventDay = newValue.intValue() + 1;
                    }
                });

                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.getChildren().addAll(monthBox, dayBox);
                gridPane.add(hBox, 1, 3);
            }
        });

        //Create choice boxes for time selection (hour/minute)
        ChoiceBox hourBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5","6","7", "8",
                "9", "10", "11", "12"));
        ChoiceBox minuteBox = new ChoiceBox(FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35",
                "40", "45", "50", "55"));

        ChoiceBox AMPMBox = new ChoiceBox(FXCollections.observableArrayList("AM", "PM"));


        hourBox.getSelectionModel().selectFirst();
        eventHour = 1;
        minuteBox.getSelectionModel().selectFirst();
        eventMinute = 0;
        AMPMBox.getSelectionModel().selectFirst();
        eventAMPM = "AM";

        hourBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventHour = newValue.intValue();
            }
        });

        minuteBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventMinute = newValue.intValue() * 5;  //convert index to minutes which are increments of 5
            }
        });

        AMPMBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0)
                    eventAMPM = "AM";
                else
                    eventAMPM = "PM";
            }
        });


        //Add events to grid, Labels[] takes care of first 3 fields
        //Set font to each Text
        for(int i = 0; i <= 5; i++){
            if(i == 3){//row for date choicebox
                Label dateLabel = new Label("Date:");
                dateLabel.setFont(TEXT_FONT);
                gridPane.add(dateLabel, 0, i);
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.getChildren().addAll(monthBox, dayBox);
                gridPane.add(hBox, 1, i);
            }else if(i == 4){
                Label timeLabel = new Label("Time:");
                timeLabel.setFont(TEXT_FONT);
                gridPane.add(timeLabel, 0, i);
                HBox hBox = new HBox();
                hBox.setSpacing(10);
                Label colon = new Label(":");
                hBox.getChildren().addAll(hourBox, colon, minuteBox, AMPMBox);
                gridPane.add(hBox, 1, i);
            }else if(i == 5){
                Label details = new Label("Details:");
                details.setFont(TEXT_FONT);
                gridPane.add(details, 0, i);
                gridPane.add(eventDetailsField, 1, i);
            }else {
                labels[i].setFont(TEXT_FONT);
                gridPane.add(labels[i], 0, i);
                gridPane.add(defaultFields[i], 1, i);
            }
        }
        return gridPane;
    }

    /**
     * Buttons for add event form.
     */
    private HBox addEventButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Add Event");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(eventSchedulePane());
            borderPane.setBottom(eventScheduleButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            //Save the event in  the event list
            TeamEvent newEvent = new TeamEvent();
            newEvent.setTitle(eventTitleField.getText());
            newEvent.setSpeedway(eventSpeedwayField.getText());
            newEvent.setLocation(eventLocationField.getText());
            newEvent.setDate(eventMonth, eventDay);
            newEvent.setTime(eventHour, eventMinute, eventAMPM);
            newEvent.setDetails(eventDetailsField.getText());

            eventList.add(newEvent);
            eventMgmt.updateEventList(eventList);

            borderPane.setCenter(eventSchedulePane());
            borderPane.setBottom(eventScheduleButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create a grid pane showing event detials, allowing user to edit them.
     * ID of button is passed in, this is the array index of the event list that this event will correspond to.
     */
    private GridPane editEventForm(TeamEvent event){
        GridPane gridPane = new GridPane();
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);
        gridPane.setPadding(CENTER_INSETS);

        //Lables for each field
        Label[] labels = new Label[]{
                new Label("Event Name:"),
                new Label("Speedway:"),
                new Label("Location:")
        };

        //Set font to each label
        for(Label l : labels){
            l.setFont(TEXT_FONT);
        }

        //Assign TextFields
        eventTitleField = new TextField(event.getTitle());
        eventSpeedwayField = new TextField(event.getSpeedway());
        eventLocationField = new TextField(event.getLocation());
        eventDetailsField = new TextField(event.getDetails());

        //Get date and time details
        eventMonth = event.getMonth();
        eventDay = event.getDay();
        eventHour = event.getHour();
        eventMinute = event.getMinute();
        eventAMPM = event.getAMPM();

        //Each field with default text already in directory (dummy text for now)
        //For first 3 fields in form
        TextField[] defaultFields = new TextField[]{eventTitleField, eventSpeedwayField, eventLocationField};

        for(TextField t : defaultFields){
            t.setPrefWidth(TEXT_FIELD_WIDTH);
        }

        //Make choice box for date (month)
        final ChoiceBox monthBox = new ChoiceBox(FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October",
                "November", "December"));
        monthBox.getSelectionModel().select(eventMonth - 1);

        ChoiceBox dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "29", "30", "31"));
        dayBox.getSelectionModel().select(eventDay - 1);

        monthBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventMonth = newValue.intValue() + 1;
                ChoiceBox dayBox;
                if(eventMonth == 2){
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28"));
                }else if(eventMonth == 4 || eventMonth == 6 || eventMonth == 9 || eventMonth == 11){
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28", "29", "30"));
                }else{
                    dayBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
                            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                            "24", "25", "26", "27", "28", "29", "30", "31"));
                }
                dayBox.getSelectionModel().selectFirst();
                dayBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        eventDay = newValue.intValue() + 1;
                    }
                });
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.getChildren().addAll(monthBox, dayBox);
                gridPane.add(hBox, 1, 3);
            }
        });

        //Create choice boxes for time selection (hour/minute)
        ChoiceBox hourBox = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5","6","7", "8",
                "9", "10", "11", "12"));
        ChoiceBox minuteBox = new ChoiceBox(FXCollections.observableArrayList("00", "05", "10", "15", "20", "25", "30", "35",
                "40", "45", "50", "55"));

        ChoiceBox AMPMBox = new ChoiceBox(FXCollections.observableArrayList("AM", "PM"));


        hourBox.getSelectionModel().select(eventHour);
        minuteBox.getSelectionModel().select(eventMinute/5);//convert from minutes to index by dividing by 5
        AMPMBox.getSelectionModel().select(eventAMPM);

        hourBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventHour = newValue.intValue();
            }
        });

        minuteBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eventMinute = newValue.intValue() * 5;  //convert index to minutes which are increments of 5;
            }
        });

        AMPMBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0)
                    eventAMPM = "AM";
                else
                    eventAMPM = "PM";
            }
        });


        //Add events to grid, Labels[] takes care of first 3 fields
        //Set font to each Text
        for(int i = 0; i <= 5; i++){
            if(i == 3){//row for date choicebox
                Label dateLabel = new Label("Date:");
                dateLabel.setFont(TEXT_FONT);
                gridPane.add(dateLabel, 0, i);
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                hBox.getChildren().addAll(monthBox, dayBox);
                gridPane.add(hBox, 1, i);
            }else if(i == 4){
                Label timeLabel = new Label("Time:");
                timeLabel.setFont(TEXT_FONT);
                gridPane.add(timeLabel, 0, i);
                HBox hBox = new HBox();
                hBox.setSpacing(10);
                Label colon = new Label(":");
                hBox.getChildren().addAll(hourBox, colon, minuteBox, AMPMBox);
                gridPane.add(hBox, 1, i);
            }else if(i == 5){
                Label details = new Label("Details:");
                details.setFont(TEXT_FONT);
                gridPane.add(details, 0, i);
                gridPane.add(eventDetailsField, 1, i);
            }else {
                labels[i].setFont(TEXT_FONT);
                gridPane.add(labels[i], 0, i);
                gridPane.add(defaultFields[i], 1, i);
            }
        }
        return gridPane;
    }

    /**
     * Create HBox to hold buttons for the edit event form.
     */
    private HBox editEventButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(eventSchedulePane());
            borderPane.setBottom(eventScheduleButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            //Save the event in  the event list, replacing the old one
            TeamEvent updatedEvent = eventList.get(editingIndex);
            updatedEvent.setTitle(eventTitleField.getText());
            updatedEvent.setSpeedway(eventSpeedwayField.getText());
            updatedEvent.setLocation(eventLocationField.getText());
            updatedEvent.setDate(eventMonth, eventDay);
            updatedEvent.setTime(eventHour, eventMinute, eventAMPM);
            updatedEvent.setDetails(eventDetailsField.getText());

            //Update the modified list with event management
            eventMgmt.updateEventList(eventList);

            borderPane.setCenter(eventSchedulePane());
            borderPane.setBottom(eventScheduleButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create funds VBox displaying available funds and a scrollable log for funds records.
     * Records will show all previous fund removals and additions, time-stamped.
     */
    private VBox fundsPane(){
        VBox vbox = new VBox();
        vbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //Set the padding around the vbox
        vbox.setPadding(CENTER_INSETS);

        //Set the amount of space in between menu items
        vbox.setSpacing(30);

        //Get total funds
        totalFunds = fundsMgmt.getFunds();

        //Create funds label
        String remFunds = NumberFormat.getInstance().format(totalFunds);
        Label fundsLabel = new Label("Available Funds: $" + remFunds);
        fundsLabel.setFont(LABEL_FONT);

        //Create funds log (scrollable pane)
        ScrollPane fundsLog = fundsLog();

        //Add funds label and log to vbox
        vbox.getChildren().addAll(fundsLabel, fundsLog);

        return vbox;
    }

    /**
     * Create a grid pane to hold transaction records.
     * Newest transition at top of grid pane.
     * Put the grid pane in a scroll pane.
     * Return the scroll pane.
     */
    private ScrollPane fundsLog(){
        //Grid pane to hold transactions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(30);
        gridPane.setPadding(CENTER_INSETS);

        //Get transactions
        transactions = fundsMgmt.getTransactions();

        if(transactions.size() > 0) {
            //Create funds log label
            Label fundsLogLabel = new Label("Previous Transactions");
            fundsLogLabel.setFont(LABEL_FONT);

            //Labels for grid pane
            Label dateLabel = new Label("Date and Time");
            dateLabel.setFont(TEXT_FONT);
            dateLabel.setUnderline(true);

            Label transactionLabel = new Label("Transaction");
            transactionLabel.setFont(TEXT_FONT);
            transactionLabel.setUnderline(true);

            Label remainingLabel = new Label("Funds Remaining");
            remainingLabel.setFont(TEXT_FONT);
            remainingLabel.setUnderline(true);

            //Array of dates as string right now
            Text[] date = new Text[transactions.size()];
            for(int i = 0; i < date.length; i++){
                DateFormat dateFormatD = new SimpleDateFormat("MMM dd, yyyy");
                DateFormat dateFormatT = new SimpleDateFormat("hh:mm:ss aa");
                Date tranDate = transactions.get(i).getDate();
                date[i] = new Text(dateFormatD.format(tranDate) + "  at  " + dateFormatT.format(tranDate));
            }


            //Array of transactions as Texts
            Text[] transaction = new Text[transactions.size()];
            for(int i = 0; i < transaction.length; i++){
                String amount = NumberFormat.getInstance().format(transactions.get(i).getAmount());
                transaction[i] = new Text(transactions.get(i).getType() + ": $" + amount);
            }

            //Array of remaining funds as Texts
            Text[] remFund = new Text[transactions.size()];
            for(int i = 0; i < transaction.length; i++){
                String amount = NumberFormat.getInstance().format(transactions.get(i).getRemainingFunds());
                remFund[i] = new Text("$" + amount);
            }

            //Add the grid pane labels to top of the grid pane
            gridPane.add(fundsLogLabel, 0, 0, 2, 1);
            gridPane.add(dateLabel, 0, 1);
            gridPane.add(transactionLabel, 1, 1);
            gridPane.add(remainingLabel, 2, 1);

            //Add transactions and remaining funds to grid pane
            //Set the fonts of each Text
            for (int i = 0; i < transactions.size(); i++) {
                date[i].setFont(TEXT_FONT);
                transaction[i].setFont(TEXT_FONT);
                remFund[i].setFont(TEXT_FONT);

                gridPane.add(date[i], 0, (i + 2));
                gridPane.add(transaction[i], 1, (i + 2));
                gridPane.add(remFund[i], 2, (i + 2));
            }
        }

        //Scroll pane to hold grid pane of transactions
        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;
    }

    /**
     * Create funds overview HBox for buttons to add/remove funds.
     */
    private HBox fundsButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button removeButton = new Button("Remove Funds");
        Button addButton = new Button("Add Funds");

        removeButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        removeButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(removeFundsForm());
            borderPane.setBottom(removeFundsButtonPanel());
        });

        addButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addFundsForm());
            borderPane.setBottom(addFundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(removeButton, addButton);

        return buttonPanel;
    }

    /**
     * Add funds form.
     */
    private VBox addFundsForm(){
        VBox vBox = new VBox();
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(CENTER_INSETS);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);

        String remFunds = NumberFormat.getInstance().format(totalFunds);
        Label remainingFunds = new Label("Available Funds: $"+ remFunds );
        remainingFunds.setFont(LABEL_FONT);

        Label addLabel = new Label("Add amount:");
        addLabel.setFont(TEXT_FONT);

        addFundsTextField = new TextField();

        gridPane.add(addLabel, 0, 1);
        gridPane.add(addFundsTextField, 1, 1);

        vBox.getChildren().addAll(remainingFunds, gridPane);

        return vBox;
    }

    /**
     * Remove funds form.
     */
    private VBox removeFundsForm(){
        VBox vBox = new VBox();
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(CENTER_INSETS);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);

        String remFunds = NumberFormat.getInstance().format(totalFunds);
        Label remainingFunds = new Label("Available Funds: $" + remFunds);
        remainingFunds.setFont(LABEL_FONT);

        Label addLabel = new Label("Remove amount:");
        addLabel.setFont(TEXT_FONT);

        removeFundsTextField = new TextField();

        gridPane.add(addLabel, 0, 1);
        gridPane.add(removeFundsTextField, 1, 1);

        vBox.getChildren().addAll(remainingFunds, gridPane);

        return vBox;
    }

    /**
     * Button panel for add funds form.
     */
    private HBox addFundsButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        submitButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(fundsPane());
            borderPane.setBottom(fundsButtonPanel());
        });

        submitButton.setOnAction((ActionEvent e) -> {
            Double amount = Double.parseDouble(addFundsTextField.getText());

            //Update total funds
            Double newTotalFunds = totalFunds + amount;
            fundsMgmt.updateFunds(newTotalFunds);

            //Create new transaction and add it to the funds log
            Transaction newTrans = new Transaction();
            newTrans.setAmount(amount);
            newTrans.setType("Added");
            newTrans.setRemainingFunds(newTotalFunds);
            newTrans.setDate(new Date());

            fundsMgmt.addTransaction(newTrans);

            borderPane.setCenter(fundsPane());
            borderPane.setBottom(fundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, submitButton);

        return buttonPanel;
    }

    /**
     * Button panel for add funds form.
     */
    private HBox removeFundsButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        cancelButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        submitButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(fundsPane());
            borderPane.setBottom(fundsButtonPanel());
        });

        submitButton.setOnAction((ActionEvent e) -> {
            Double amount = Double.parseDouble(removeFundsTextField.getText());

            //Update total funds
            Double newTotalFunds = totalFunds - amount;
            fundsMgmt.updateFunds(newTotalFunds);

            //Create new transaction and add it to the funds log
            Transaction newTrans = new Transaction();
            newTrans.setAmount(amount);
            newTrans.setType("Removed");
            newTrans.setRemainingFunds(newTotalFunds);
            newTrans.setDate(new Date());

            fundsMgmt.addTransaction(newTrans);

            borderPane.setCenter(fundsPane());
            borderPane.setBottom(fundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, submitButton);

        return buttonPanel;
    }

    /**
     * Create expense requests pane.
     */
    private ScrollPane expenseRequestPane(){
        //Grid pane to hold transactions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(60);
        gridPane.setPadding(CENTER_INSETS);

        String remFunds = NumberFormat.getInstance().format(totalFunds);
        Label remainingFunds = new Label("Available Funds: $" + remFunds);
        remainingFunds.setFont(LABEL_FONT);

        //Array of transactions as Texts
        Text[] requests = new Text[]{
                new Text(dummyExpense1),
                new Text(dummyExpense2),
                new Text(dummyExpense1),
                new Text(dummyExpense2),
                new Text(dummyExpense1)
        };

        //Set font of requests
        for(Text t : requests){
            t.setFont(TEXT_FONT);
        }

        String decline = "Decline";
        String accept = "Accept";

        //Array of remaining funds as Texts
        Button[] declineButtons = new Button[]{
                new Button(decline),
                new Button(decline),
                new Button(decline),
                new Button(decline),
                new Button(decline)
        };

        //Array of remaining funds as Texts
        Button[] acceptButtons = new Button[]{
                new Button(accept),
                new Button(accept),
                new Button(accept),
                new Button(accept),
                new Button(accept)
        };

        for(Button b : declineButtons){
            b.setPrefSize(100, 20);
        }

        for(Button b : acceptButtons){
            b.setPrefSize(100, 20);
        }

        gridPane.add(remainingFunds, 0, 0);

        //Add the requests followed by accept/decline buttons
        for(int i = 0; i < requests.length; i++){
            gridPane.add(requests[i], 0, i + 1);

            gridPane.setValignment(declineButtons[i], VPos.TOP);
            gridPane.setValignment(acceptButtons[i], VPos.CENTER);

            gridPane.add(acceptButtons[i], 1, i + 1);
            gridPane.add(declineButtons[i], 1, i + 1);
        }

        //Scroll pane to hold grid pane of transactions
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return scrollPane;
    }


    /**
     * HBox for top.
     */
    private HBox topHBox(){
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Label label = new Label("Team Owner");
        label.setFont(MENU_TITLE_FONT);
        hBox.getChildren().add(label);
        hBox.setPadding(new Insets(20, 20, 20 ,20));

        return hBox;
    }
}
