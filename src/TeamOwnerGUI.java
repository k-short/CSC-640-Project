import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * Created by ken12_000 on 10/21/2016.
 */
public class TeamOwnerGUI extends Application{
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

    String dummyDirName = "Kenneth Short";
    String dummyDirDetails= "Lead Car Cleaner\n" +"500 Kale Court, Greensboro, NC 27403\n" +
            "543-345-2222\n";

    String dummyEventTitle = "Daytona 500 Race";
    String dummyEventDetails = "Daytona International Speedway\n" +
                        "Daytona Beach, Florida\n" + "February 24, 2017\n" + "2:00 pm\n" +
                        "500 lap race.  Winner takes all.\n";

    String dummyTran1 = "Remove:  $600";
    String dummyRem1 = "$2000";
    String dummyTran2 = "Add:  $1000";
    String dummyRem2 = "$3000";
    String dummyTran3 = "Add:  $7,500";
    String dummyRem3 = "$10,500";
    String dummyTran4 = "Remove:  $4,500";
    String dummyRem4 = "$6000";

    String dummyDate1 = "9/12/2016";
    String dummyDate2 = "9/30/2016";
    String dummyDate3 = "10/2/2016";
    String dummyDate4 = "10/19/2016";

    String dummyExpense1 = "Item:\t\tTires\nCost:\t\t$2000\nTimeline:\t\tImmediate\nPriority:\t\tNormal\n";
    String dummyExpense2 = "Item:\t\tFront Springs\nCost:\t\t$1200\nTimeline:\t\t1 Week\nPriority:\t\tHigh\n";

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Use a border pane as the root for the scene
        borderPane = new BorderPane();
        //borderPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        borderPane.setTop(createTopHBox());

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(addSideMenuVBox());

        //Create grid pane to display main info, for the center of the border pane.
        borderPane.setCenter(createEventSchedulePane());

        //Blank HBox at bottom for border
        borderPane.setBottom(createEventScheduleButtonsPanel());

        //Right border
        VBox rightBorder = new VBox();
        rightBorder.setPrefWidth(30);
        rightBorder.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setRight(rightBorder);

        //Create and show scene
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Team Owner Interface");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Create a VBox to hold the menu on left side of border pane
     */
    private VBox addSideMenuVBox(){
        VBox menu = new VBox();
        menu.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        //Set the padding around the vbox
        //menu.setPadding(new Insets(10));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        //Set the title for the menu
       /* Text title = new Text("Team Owner");
        title.setFont(MENU_TITLE_FONT);
        menu.getChildren().add(title);*/

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
        //Set to selected font
        //Set center to selected button's corresponding info
        schedule.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLUE);
            dir.setTextFill(Color.BLACK);
            funds.setTextFill(Color.BLACK);
            req.setTextFill(Color.BLACK);

            schedule.setFont(MENU_OPTION_FONT_SEL);
            dir.setFont(MENU_OPTION_FONT_UNSEL);
            funds.setFont(MENU_OPTION_FONT_UNSEL);
            req.setFont(MENU_OPTION_FONT_UNSEL);

            borderPane.setCenter(createEventSchedulePane());
            borderPane.setBottom(createEventScheduleButtonsPanel());
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

            borderPane.setCenter(createDirectoryPane());
            borderPane.setBottom(createDirectoryButtonPanel());
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

            borderPane.setCenter(createFundsPane());
            borderPane.setBottom(createFundsButtonPanel());
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

            borderPane.setCenter(createExpenseRequestPane());
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
    private ScrollPane createDirectoryPane(){
        GridPane gridPane = new GridPane();


        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(CENTER_INSETS);

        //Dummy dir names to be added (buttons)
        Button name1 = new Button(dummyDirName);
        Button name2 = new Button(dummyDirName);
        Button name3 = new Button(dummyDirName);
        Button name4 = new Button(dummyDirName);
        Button name5 = new Button(dummyDirName);
        Button name6 = new Button(dummyDirName);
        Button name7 = new Button(dummyDirName);
        Button name8 = new Button(dummyDirName);
        Button name9 = new Button(dummyDirName);

        //Add event to each button -> go to edit form page for name
        EventHandler<ActionEvent> dirNamePress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                borderPane.setCenter(createEditDirectoryForm());
                borderPane.setBottom(createEditDirectoryButtonPanel());
            }
        };

        //Add each button to Button array to be added to grid pane
        Button[] dirNames = new Button[]{ name1, name2, name3, name4, name5, name6, name7, name8, name9 };

        //Apply settings to each button: font, action event, background
        for(Button b : dirNames){
            //Set fonts
            b.setFont(LABEL_FONT);
            b.setBackground(Background.EMPTY);
            b.setOnAction(dirNamePress);
            b.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b.setTextFill(Color.BLUE);
                        }
                    });

            //Removing the shadow when the mouse cursor is off
            b.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b.setTextFill(Color.BLACK);
                        }
                    });
        }

        //Dummy info (details) to be added for now
        Text[] dirDetails = new Text[]{
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
                new Text(dummyDirDetails),
        };

        //Set font of directory details Texts
        for(Text t : dirDetails){
            t.setFont(TEXT_FONT);
        }

        //Add each directory name button followed by directory details for that name
        //Set font for each
        int counter = 0;
        for(int i = 0; i < dirNames.length*2; i+=2){
            gridPane.add(dirNames[counter], 0, i);
            gridPane.add(dirDetails[counter], 0, i+1);

            //Add a seperator between directory members
            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, i+1);

            counter ++;
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
    private HBox createDirectoryButtonPanel(){
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
            borderPane.setCenter(createAddMemberForm());
            borderPane.setBottom(createAddMemberButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().add(addButton);

        return buttonPanel;
    }

    /**
     * Create a form using a grid pane.
     */
    private GridPane createEditDirectoryForm(){
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

        //Each field with default text already in directory (dummy text for now)
        TextField[] defaultFields = new TextField[]{
                new TextField("Kenneth Short"),
                new TextField("Lead Car Cleaner"),
                new TextField("500 Kale Court, Greensboro, NC 27403"),
                new TextField("543-345-2222")
        };

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
    private HBox createEditDirectoryButtonPanel(){
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
            borderPane.setCenter(createDirectoryPane());
            borderPane.setBottom(createDirectoryButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createDirectoryPane());
            borderPane.setBottom(createDirectoryButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create a grid panel to hold all events.  Wrap in a scroll pane.
     */
    private ScrollPane createEventSchedulePane(){
        GridPane gridPane = new GridPane();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(CENTER_INSETS);

        //Selectable Event titles
        Button eventTitle1 = new Button(dummyEventTitle);
        Button eventTitle2 = new Button(dummyEventTitle);
        Button eventTitle3 = new Button(dummyEventTitle);
        Button eventTitle4 = new Button(dummyEventTitle);
        Button eventTitle5 = new Button(dummyEventTitle);
        Button eventTitle6 = new Button(dummyEventTitle);
        Button eventTitle7 = new Button(dummyEventTitle);
        Button eventTitle8 = new Button(dummyEventTitle);
        Button eventTitle9 = new Button(dummyEventTitle);

        //Event for each button press -> go to edit form page for event
        EventHandler<ActionEvent> eventTitlePress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                borderPane.setCenter(createEditEventForm());
                borderPane.setBottom(createEditEventButtonPanel());
            }
        };

        //Array of Buttons to hold event titles
        Button[] eventTitles = new Button[]{eventTitle1, eventTitle2, eventTitle3, eventTitle4, eventTitle5,
                                            eventTitle6, eventTitle7, eventTitle8, eventTitle9};

        //Apply settings to each button: font, action event, background
        for(Button b : eventTitles){
            //Set fonts
            b.setFont(LABEL_FONT);
            b.setBackground(Background.EMPTY);
            b.setOnAction(eventTitlePress);
            b.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b.setTextFill(Color.BLUE);
                        }
                    });

            //Removing the shadow when the mouse cursor is off
            b.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            b.setTextFill(Color.BLACK);
                        }
                    });
        }

        //Dummy info to be added for now
        Text[] eventDetails = new Text[]{
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
                new Text(dummyEventDetails),
        };

        //Apply font to each event detail
        for(Text t : eventDetails){
            t.setFont(TEXT_FONT);
        }

        //Add each event to the grid pane
        //Add event title button above each event detail
        int counter = 0;
        for(int i = 0; i < eventTitles.length*2; i+=2){
            //Add each title and details to grid pane
            gridPane.add(eventTitles[counter], 0, i);

            int index = i + 1;
            gridPane.add(eventDetails[counter], 0, index);

            //Add a seperator between events
            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, index);

            counter++;
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
    private HBox createEventScheduleButtonsPanel(){
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

        //If edit button is selected, switch to show dir change form and buttons
        addEventButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createAddEventForm());
            borderPane.setBottom(createAddEventButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().add(addEventButton);

        return buttonPanel;
    }

    /**
     * Create a grid pane showing event detials, allowing user to edit them.
     */
    private GridPane createEditEventForm(){
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
                new Label("Location:"),
                new Label("Date:"),
                new Label("Time:"),
                new Label("Details:")
        };

        //Each field with default text already in directory (dummy text for now)
        TextField[] defaultFields = new TextField[]{
                new TextField("Daytona 500 Race"),
                new TextField("Daytona Internaltional Speedway"),
                new TextField("Daytona Beach, Florida"),
                new TextField("February 24, 2017"),
                new TextField("2:00 pm"),
                new TextField("500 lap race.  Winner takes all.")
        };

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
     * Create HBox to hold buttons for the edit event form.
     */
    private HBox createEditEventButtonPanel(){
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
            borderPane.setCenter(createEventSchedulePane());
            borderPane.setBottom(createEventScheduleButtonsPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createEventSchedulePane());
            borderPane.setBottom(createEventScheduleButtonsPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create funds overview HBox for buttons to add/remove funds.
     */
    private HBox createFundsButtonPanel(){
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
            borderPane.setCenter(createRemoveFundsForm());
            borderPane.setBottom(createRemoveFundsButtonPanel());
        });

        addButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createAddFundsForm());
            borderPane.setBottom(createAddFundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(removeButton, addButton);

        return buttonPanel;
    }

    /**
     * Create funds VBox displaying available funds and a scrollable log for funds records.
     * Records will show all previous fund removals and additions, time-stamped.
     */
    private VBox createFundsPane(){
        VBox vbox = new VBox();
        vbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //Set the padding around the vbox
        vbox.setPadding(CENTER_INSETS);

        //Set the amount of space in between menu items
        vbox.setSpacing(30);

        //Create funds label
        Label fundsLabel = new Label("Available Funds:  $6,000");
        fundsLabel.setFont(LABEL_FONT);

        //Create funds log (scrollable pane)
        ScrollPane fundsLog = createFundsLog();

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
    private ScrollPane createFundsLog(){
        //Grid pane to hold transactions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(30);
        gridPane.setPadding(CENTER_INSETS);
        //gridPane.setPadding(new Insets(5, 5, 5, 5));

        //Create funds log label
        Label fundsLogLabel = new Label("Previous Transactions");
        fundsLogLabel.setFont(LABEL_FONT);

        //Labels for grid pane
        Label dateLabel = new Label("Date");
        dateLabel.setFont(TEXT_FONT);
        dateLabel.setUnderline(true);

        Label transactionLabel = new Label("Transaction");
        transactionLabel.setFont(TEXT_FONT);
        transactionLabel.setUnderline(true);

        Label remainingLabel = new Label("Funds Remaining");
        remainingLabel.setFont(TEXT_FONT);
        remainingLabel.setUnderline(true);

        //Array of dates as string right now
        Text[] dates = new Text[]{
                new Text(dummyDate4),
                new Text(dummyDate3),
                new Text(dummyDate2),
                new Text(dummyDate1),
                new Text(dummyDate4),
                new Text(dummyDate3),
                new Text(dummyDate2),
                new Text(dummyDate1),
                new Text(dummyDate4),
                new Text(dummyDate3),
                new Text(dummyDate2),
                new Text(dummyDate1),
                new Text(dummyDate4),
                new Text(dummyDate3),
                new Text(dummyDate2),
                new Text(dummyDate1)
        };

        //Array of transactions as Texts
        Text[] transactions = new Text[]{
                new Text(dummyTran4),
                new Text(dummyTran3),
                new Text(dummyTran2),
                new Text(dummyTran1),
                new Text(dummyTran4),
                new Text(dummyTran3),
                new Text(dummyTran2),
                new Text(dummyTran1),
                new Text(dummyTran4),
                new Text(dummyTran3),
                new Text(dummyTran2),
                new Text(dummyTran1),
                new Text(dummyTran4),
                new Text(dummyTran3),
                new Text(dummyTran2),
                new Text(dummyTran1)
        };

        //Array of remaining funds as Texts
        Text[] remainingFunds = new Text[]{
                new Text(dummyRem4),
                new Text(dummyRem3),
                new Text(dummyRem2),
                new Text(dummyRem1),
                new Text(dummyRem4),
                new Text(dummyRem3),
                new Text(dummyRem2),
                new Text(dummyRem1),
                new Text(dummyRem4),
                new Text(dummyRem3),
                new Text(dummyRem2),
                new Text(dummyRem1),
                new Text(dummyRem1),
                new Text(dummyRem4),
                new Text(dummyRem3),
                new Text(dummyRem2),
                new Text(dummyRem1)
        };

        //Add the grid pane labels to top of the grid pane
        gridPane.add(fundsLogLabel, 0, 0, 2, 1);
        gridPane.add(dateLabel, 0, 1);
        gridPane.add(transactionLabel, 1, 1);
        gridPane.add(remainingLabel, 2, 1);

        //Add transactions and remaining funds to grid pane
        //Set the fonts of each Text
        for(int i = 0; i < transactions.length; i++){
            dates[i].setFont(TEXT_FONT);
            transactions[i].setFont(TEXT_FONT);
            remainingFunds[i].setFont(TEXT_FONT);

            gridPane.add(dates[i], 0, (i + 2));
            gridPane.add(transactions[i], 1, (i + 2));
            gridPane.add(remainingFunds[i], 2, (i + 2));
        }

        //Scroll pane to hold grid pane of transactions
        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;
    }

    /**
     * Create a form for adding a new event
     */
    private GridPane createAddEventForm(){
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
                new Label("Location:"),
                new Label("Date:"),
                new Label("Time:"),
                new Label("Details:")
        };

        //Set font to each label
        for(Label l : labels){
            l.setFont(TEXT_FONT);
        }

        TextField[] defaultFields = new TextField[]{
                new TextField(),
                new TextField(),
                new TextField(),
                new TextField(),
                new TextField(),
                new TextField()
        };

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
    private HBox createAddEventButtonPanel(){
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
            borderPane.setCenter(createEventSchedulePane());
            borderPane.setBottom(createEventScheduleButtonsPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createEventSchedulePane());
            borderPane.setBottom(createEventScheduleButtonsPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create a form for adding a new event
     */
    private GridPane createAddMemberForm(){
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

        TextField[] defaultFields = new TextField[]{
                new TextField(),
                new TextField(),
                new TextField(),
                new TextField()
        };

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
    private HBox createAddMemberButtonPanel(){
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
            borderPane.setCenter(createDirectoryPane());
            borderPane.setBottom(createDirectoryButtonPanel());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createDirectoryPane());
            borderPane.setBottom(createDirectoryButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * HBox for top.
     */
    private HBox createTopHBox(){
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Label label = new Label("Team Owner");
        label.setFont(MENU_TITLE_FONT);
        hBox.getChildren().add(label);
        hBox.setPadding(new Insets(20, 20, 20 ,20));

        return hBox;
    }

    /**
     * Add funds form.
     */
    private VBox createAddFundsForm(){
        VBox vBox = new VBox();
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(CENTER_INSETS);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);

        Label remainingFunds = new Label("Available Funds:  $6,000");
        remainingFunds.setFont(LABEL_FONT);

        Label addLabel = new Label("Add amount:");
        addLabel.setFont(TEXT_FONT);

        TextField addField = new TextField();

        gridPane.add(addLabel, 0, 1);
        gridPane.add(addField, 1, 1);

        vBox.getChildren().addAll(remainingFunds, gridPane);

        return vBox;
    }

    /**
     * Remove funds form.
     */
    private VBox createRemoveFundsForm(){
        VBox vBox = new VBox();
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setPadding(CENTER_INSETS);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(FORM_HGAP);
        gridPane.setVgap(FORM_VGAP);

        Label remainingFunds = new Label("Available Funds:  $6,000");
        remainingFunds.setFont(LABEL_FONT);

        Label addLabel = new Label("Remove amount:");
        addLabel.setFont(TEXT_FONT);

        TextField addField = new TextField();

        gridPane.add(addLabel, 0, 1);
        gridPane.add(addField, 1, 1);

        vBox.getChildren().addAll(remainingFunds, gridPane);

        return vBox;
    }

    /**
     * Button panel for add funds form.
     */
    private HBox createAddFundsButtonPanel(){
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
            borderPane.setCenter(createFundsPane());
            borderPane.setBottom(createFundsButtonPanel());
        });

        submitButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createFundsPane());
            borderPane.setBottom(createFundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, submitButton);

        return buttonPanel;
    }

    /**
     * Button panel for add funds form.
     */
    private HBox createRemoveFundsButtonPanel(){
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
            borderPane.setCenter(createFundsPane());
            borderPane.setBottom(createFundsButtonPanel());
        });

        submitButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createFundsPane());
            borderPane.setBottom(createFundsButtonPanel());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, submitButton);

        return buttonPanel;
    }

    /**
     * Create expense requests pane.
     */
    private ScrollPane createExpenseRequestPane(){
        //Grid pane to hold transactions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(60);
        gridPane.setPadding(CENTER_INSETS);

        Label remainingFunds = new Label("Available Funds:  $6,000");
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
}
