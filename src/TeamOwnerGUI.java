import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oracle.jrockit.jfr.StringConstantPool;

import java.awt.*;

/**
 * Created by ken12_000 on 10/21/2016.
 */
public class TeamOwnerGUI extends Application{
    private final Font MENU_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);
    private final Font MENU_OPTION_FONT = Font.font("Arial", FontWeight.NORMAL, 18);
    private final Font LABEL_FONT = Font.font("Arial", FontWeight.BOLD, 18);
    private final Font TEXT_FONT = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Insets CENTER_INSETS = new Insets(40, 40, 40, 40);
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Use a border pane as the root for the scene
        borderPane = new BorderPane();

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(addSideMenuVBox());

        //Create grid pane to display main info, for the center of the border pane.
        borderPane.setCenter(addEventScrollPane());

        //Blank HBox at bottom for border
        borderPane.setBottom(addBlankHBox());

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

        //Set the padding around the vbox
        menu.setPadding(new Insets(10));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        //Set the title for the menu
        Text title = new Text("Team Owner");
        title.setFont(MENU_TITLE_FONT);
        menu.getChildren().add(title);

        //Create menu buttons, remove button backgrounds
        ToggleButton schedule = new ToggleButton("Event Schedule");
        ToggleButton dir = new ToggleButton("Team Directory");
        ToggleButton funds = new ToggleButton("Team Funds");
        ToggleButton req = new ToggleButton("Expense Requests");

        schedule.setBackground(Background.EMPTY);
        dir.setBackground(Background.EMPTY);
        funds.setBackground(Background.EMPTY);
        req.setBackground(Background.EMPTY);

        //Add buttons to Togglegroup so only one can be toggled on
        ToggleGroup toggleGroup = new ToggleGroup();
        schedule.setToggleGroup(toggleGroup);
        dir.setToggleGroup(toggleGroup);
        funds.setToggleGroup(toggleGroup);
        req.setToggleGroup(toggleGroup);

        //Add events to buttons for when clicked
        schedule.setOnAction((ActionEvent e) -> {
            schedule.setTextFill(Color.BLUE);
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addBlankHBox());
        });

        dir.setOnAction((ActionEvent e) -> {
            dir.setTextFill(Color.BLUE);
            borderPane.setCenter(addDirectoryScrollPane());
            borderPane.setBottom(addBlankHBox());
        });

        funds.setOnAction((ActionEvent e) -> {
            funds.setTextFill(Color.BLUE);
            borderPane.setCenter(addFundsVBox());
            borderPane.setBottom(addFundsButtonsHBox());
        });
        
        //Array of menu options
        ToggleButton[] menuOptions = new ToggleButton[]{schedule, dir, funds, req};

        //Add each of the menu options to the vbox
        for (int i=0; i<4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(menuOptions[i], new Insets(0, 0, 0, 40));
            menu.getChildren().add(menuOptions[i]);

            //Set the font size of the menu option
            menuOptions[i].setFont(MENU_OPTION_FONT);

        }

        return menu;
    }

    /**
     * Create a grid pane to show information based on menu option currently selected.
     */
    private ScrollPane addDirectoryScrollPane(){
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
                borderPane.setCenter(addEditDirFormGridPane());
                borderPane.setBottom(addEditDirButtonsHBox());
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

        return scrollPane;
    }

    /**
     * Create an HBox to hold buttons needed to interact with the information
     * being displayed in the center of the border pane.
     */
    private HBox addDirectoryButtonHBox(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");

        //If edit button is selected, switch to show dir change form and buttons
        editButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEditDirFormGridPane());
            borderPane.setBottom(addEditDirButtonsHBox());
        });

        //Show a dialog asking user if they want to delete member from directory.
        deleteButton.setOnAction((ActionEvent e) -> {

        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(deleteButton, editButton);

        return buttonPanel;
    }

    /**
     * Create a form using a grid pane.
     */
    private GridPane addEditDirFormGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
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
    private HBox addEditDirButtonsHBox(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addDirectoryScrollPane());
            borderPane.setBottom(addDirectoryButtonHBox());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addDirectoryScrollPane());
            borderPane.setBottom(addDirectoryButtonHBox());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create a grid panel to hold all events.  Wrap in a scroll pane.
     */
    private ScrollPane addEventScrollPane(){
        GridPane gridPane = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();

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
                borderPane.setCenter(addEditEventGridPane());
                borderPane.setBottom(addEditEventButtonsHBox());
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

        return scrollPane;
    }

    /**
     * Create HBox to hold buttons for event schedule.
     */
    private HBox addEventButtonsHBox(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button deleteButton = new Button("Delete");
        Button editButton = new Button("Edit");

        //If edit button is selected, switch to show dir change form and buttons
        editButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEditEventGridPane());
            borderPane.setBottom(addEditEventButtonsHBox());
        });

        //Show a dialog asking user if they want to delete member from directory.
        deleteButton.setOnAction((ActionEvent e) -> {

        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(deleteButton, editButton);

        return buttonPanel;
    }

    /**
     * Create a grid pane showing event detials, allowing user to edit them.
     */
    private GridPane addEditEventGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
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
    private HBox addEditEventButtonsHBox(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create funds overview HBox for buttons to add/remove funds.
     */
    private HBox addFundsButtonsHBox(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button cancelButton = new Button("Remove Funds");
        Button saveButton = new Button("Add Funds");

        cancelButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(cancelButton, saveButton);

        return buttonPanel;
    }

    /**
     * Create funds VBox displaying available funds and a scrollable log for funds records.
     * Records will show all previous fund removals and additions, time-stamped.
     */
    private VBox addFundsVBox(){
        VBox vbox = new VBox();

        //Set the padding around the vbox
        vbox.setPadding(CENTER_INSETS);

        //Set the amount of space in between menu items
        vbox.setSpacing(30);

        //Create funds label
        Label fundsLabel = new Label("Available funds:  $6,000");
        fundsLabel.setFont(LABEL_FONT);

        //Create funds log (scrollable pane)
        ScrollPane fundsLog = addFundsLogScrollPane();

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
    private ScrollPane addFundsLogScrollPane(){
        //Grid pane to hold transactions
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(10);
        gridPane.setPadding(CENTER_INSETS);

        //Labels for grid pane
        Label transactionLabel = new Label("Transaction");
        transactionLabel.setFont(LABEL_FONT);

        Label remainingLabel = new Label("Funds Remaining");
        remainingLabel.setFont(LABEL_FONT);

        //Array of transactions as Texts
        Text[] transactions = new Text[]{
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
                new Text(dummyRem1)
        };

        //Add the grid pane labels to top of the grid pane
        gridPane.add(transactionLabel, 0, 0);
        gridPane.add(remainingLabel, 1, 0);

        //Add transactions and remaining funds to grid pane
        //Set the fonts of each Text
        for(int i = 0; i < transactions.length; i++){
            transactions[i].setFont(TEXT_FONT);
            remainingFunds[i].setFont(TEXT_FONT);

            gridPane.add(transactions[i], 0, (i + 1));
            gridPane.add(remainingFunds[i], 1, (i + 1));
        }

        //Scroll pane to hold grid pane of transactions
        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;
    }

    /**
     * Blank hbox to act as border at bottom of border pane
     */
    private HBox addBlankHBox(){
        HBox hBox = new HBox();
        hBox.setPrefHeight(20);

        return hBox;
    }

    /**
     * Blank VBox to act as border on right side border pane.
     */
    private VBox addBlankVBox(){
        VBox vBox = new VBox();
        vBox.setPrefWidth(20);

        return  vBox;
    }
}
