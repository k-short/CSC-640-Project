import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by ken12_000 on 10/21/2016.
 */
public class TeamOwnerGUI extends Application{
    private final int MENU_TITLE_FONT_SIZE = 20;
    private final int MENU_OPTION_FONT_SIZE = 18;
    private final int DIR_FONT_SIZE = 14;
    private BorderPane borderPane;

    String dummyDir= "Kenneth Short\n" + "Lead Car Cleaner\n" +"500 Kale Court, Greensboro, NC 27403\n" +
            "543-345-2222\n";

    String dummyEvent = "Datona 500 Race\n" + "Daytona International Speedway\n" +
                        "Daytona Beach, Florida\n" + "February 24, 2017\n" + "2:00 pm\n" +
                        "500 lap race.  Winner takes all.\n";

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Use a border pane as the root for the scene
        borderPane = new BorderPane();

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(addSideMenuVBox());

        //Create grid pane to display main info, for the center of the border pane.
        borderPane.setCenter(addEventScrollPane());

        //Create a button panel to be added at the bottom of the border pane.
        //These are the buttons that will interact with info in center pane.
        borderPane.setBottom(addEventButtonsHBox());

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
        title.setFont(Font.font("Arial", FontWeight.BOLD, MENU_TITLE_FONT_SIZE));
        menu.getChildren().add(title);

        //Create menu buttons
        ToggleButton schedule = new ToggleButton("Event Schedule");
        ToggleButton dir = new ToggleButton("Team Directory");
        ToggleButton funds = new ToggleButton("Team Funds");
        ToggleButton req = new ToggleButton("Expense Requests");

        //Add buttons to Togglegroup so only one can be toggled on
        ToggleGroup toggleGroup = new ToggleGroup();
        schedule.setToggleGroup(toggleGroup);
        dir.setToggleGroup(toggleGroup);
        funds.setToggleGroup(toggleGroup);
        req.setToggleGroup(toggleGroup);

        //Add events to buttons for when clicked
        schedule.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addEventScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        dir.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addDirectoryScrollPane());
            borderPane.setBottom(addDirectoryButtonHBox());
        });




        //Array of menu options
        ToggleButton[] menuOptions = new ToggleButton[]{schedule, dir, funds, req};

        //Add each of the menu options to the vbox
        for (int i=0; i<4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(menuOptions[i], new Insets(0, 0, 0, 40));
            menu.getChildren().add(menuOptions[i]);

            //Set the font size of the menu option
            menuOptions[i].setFont(Font.font(MENU_OPTION_FONT_SIZE));

        }

        return menu;
    }

    /**
     * Create a grid pane to show information based on menu option currently selected.
     */
    private ScrollPane addDirectoryScrollPane(){
        GridPane gridPane = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //Dummy info to be added for now
        Text[] directory = new Text[]{
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
                new Text(dummyDir),
        };

        //Add each directory string to the grid pane, setting a font size
        for(int i = 0; i < directory.length; i+=2){
            gridPane.add(directory[i], 1, i);
            directory[i].setFont(Font.font(DIR_FONT_SIZE));

            //Add a seperator between directory members
            int index = i + 1;
            gridPane.add(new Separator(), 0, index, 2, 1);

            //Create a radio button to be added next to each directory member
            RadioButton radioButton = new RadioButton("");
            radioButton.setToggleGroup(toggleGroup);
            gridPane.setValignment(radioButton, VPos.TOP); //Put button at top of cell
            gridPane.add(radioButton, 0, i);
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
            borderPane.setBottom(addEditDirButtonHBox());
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
        gridPane.setPadding(new Insets(25, 25, 25, 25));

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

        for(int i = 0; i < labels.length; i++){
            gridPane.add(labels[i], 0, i);
            gridPane.add(defaultFields[i], 1, i);
        }

        return gridPane;
    }

    /**
     * Create an hbox to hold buttons to interact with a directory member edit form.
     */
    private HBox addEditDirButtonHBox(){
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
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //Dummy info to be added for now
        Text[] directory = new Text[]{
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
                new Text(dummyEvent),
        };

        //Add each directory string to the grid pane, setting a font size
        for(int i = 0; i < directory.length; i+=2){
            gridPane.add(directory[i], 1, i);
            //gridPane.setGridLinesVisible(true);
            directory[i].setFont(Font.font(DIR_FONT_SIZE));

            //Add a seperator between events
            int index = i + 1;
            gridPane.add(new Separator(), 0, index, 2, 1);

            //Create a radio button to be added next to each directory member
            RadioButton radioButton = new RadioButton("");
            radioButton.setToggleGroup(toggleGroup);
            gridPane.setValignment(radioButton, VPos.TOP); //Put button at top of cell
            gridPane.add(radioButton, 0, i);
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
        gridPane.setPadding(new Insets(25, 25, 25, 25));

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

        for(int i = 0; i < labels.length; i++){
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
     * Create
     */
}
