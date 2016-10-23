import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Jonathon Tovey on 10/21/2016 based on Ken Short's example.
 */
public class CrewChiefGUI extends Application{
    private final Font MENU_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);
    private final Font HEADER_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 40);
    private final Font MENU_OPTION_FONT = Font.font("Arial", FontWeight.NORMAL, 18);
    private final Font LABEL_FONT = Font.font("Arial", FontWeight.BOLD, 18);
    private final Font TEXT_FONT = Font.font("Arial", FontWeight.NORMAL, 14);
    private final int DIR_FONT_SIZE = 14;
    private BorderPane borderPane;

    String dummyExpense1 = "Item:\t\tTires\nCost:\t\t$2000\nTimeline:\t\tImmediate\nPriority:\t\tNormal\n";
    String dummyExpense2 = "Item:\t\tFront Springs\nCost:\t\t$1200\nTimeline:\t\t1 Week\nPriority:\t\tHigh\n";

    String dummyIssue1 = "Issue:\t\t\ttire inventory low\nSolution:\t\t\torder more tires\n" +
            "Actions Taken:\t\ttire expense submitted\nTimeline:\t\t\tImmediate\nPriority:\t\t\tNormal\n" +
            "Status:\t\t\tUnresolved\n";

    String dummyIssue2 = "Issue:\t\t\tfront suspension sounds worn\nSolution:\t\t\tInspect\n" +
            "Actions Taken:\t\tinspected suspension, springs need replacement\nTimeline:\t\t\tImmediate\n" +
            "Priority:\t\t\tHigh\nStatus:\t\t\tResolved\nResolver:\t\t\tRicky\n";

    String dummyIssue3 = "Issue:\t\t\tfront springs need replacement\nSolution:\t\t\torder and replace front springs\n" +
            "Actions Taken:\t\tfront spring expense submitted\nTimeline:\t\t\t1 Week\nPriority:\t\t\tHigh\n" +
            "Status:\t\t\tUnresolved\n";

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Use a border pane as the root for the scene
        borderPane = new BorderPane();

        borderPane.setTop(createTeamBranding());

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(createSideMenuVBox());

        borderPane.setRight(createTimeLogger());

        //Create grid pane to display main info, for the center of the border pane.
        borderPane.setCenter(createStupidIntro());

        //Create and show scene
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crew Chief Interface");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private HBox createTeamBranding(){

        HBox header = new HBox();

        header.setPadding(new Insets(10));
        header.setSpacing(30);
        header.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text title = new Text("#26 Wonder\nDriver Ricky Bobby");
        title.setFont(HEADER_TITLE_FONT);
        title.setFill(Paint.valueOf("#CC0000"));

        // nascar logo
        File nascarFile = new File("img/nascar-85h.png");
        Image nascarImage = new Image(nascarFile.toURI().toString());
        ImageView nascarView = new ImageView(nascarImage);

        // ricky bobby image
        File rbFile = new File("img/ricky-bobby-125h.png");
        Image rbImage = new Image(rbFile.toURI().toString());
        ImageView rbView = new ImageView(rbImage);

        title.setTextAlignment(TextAlignment.CENTER);

        header.getChildren().add(nascarView);
        header.getChildren().add(title);
        header.getChildren().add(rbView);

        header.setAlignment(Pos.CENTER);

        return header;
    }

    /**
     * Create a VBox to hold the menu on left side of border pane
     */
    private VBox createSideMenuVBox(){
        VBox menu = new VBox();

        //Set the padding around the vbox
        menu.setPadding(new Insets(10));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        //Set the title for the menu
        Text title = new Text("Crew Chief");
        title.setFont(MENU_TITLE_FONT);
        menu.getChildren().add(title);

        //Create menu buttons
        ToggleButton requestExpenses = new ToggleButton("Request Expense");
        ToggleButton prioritizeIssues = new ToggleButton("Prioritize Issues");
        ToggleButton viewTimes = new ToggleButton("View Times");

        //Add buttons to Togglegroup so only one can be toggled on
        ToggleGroup toggleGroup = new ToggleGroup();
        requestExpenses.setToggleGroup(toggleGroup);
        prioritizeIssues.setToggleGroup(toggleGroup);
        viewTimes.setToggleGroup(toggleGroup);

        //Add events to buttons to execute when clicked
        requestExpenses.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createRequestExpenseScrollPane());
            borderPane.setBottom(addEventButtonsHBox());
        });

        prioritizeIssues.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createPrioritizeIssuesScrollPane());
            borderPane.setBottom(addDirectoryButtonHBox());
        });

        viewTimes.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(addFundsVBox());
            borderPane.setBottom(addFundsButtonsHBox());
        });


        //Array of menu options
        ToggleButton[] menuOptions = new ToggleButton[]{requestExpenses, prioritizeIssues, viewTimes};

        //Add each of the menu options to the vbox
        for (int i=0; i<menuOptions.length; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(menuOptions[i], new Insets(0, 0, 0, 40));
            menu.getChildren().add(menuOptions[i]);

            //Set the font size of the menu option
            menuOptions[i].setFont(MENU_OPTION_FONT);

        }

        return menu;
    }

    private VBox createStupidIntro(){

        VBox intro = new VBox();

        intro.setPadding(new Insets(10));
        intro.setSpacing(30);

        // ricky bobby image
        File rbFile = new File("img/ricky-bobby-750h.png");
        Image rbImage = new Image(rbFile.toURI().toString());
        ImageView rbView = new ImageView(rbImage);
        intro.getChildren().add(rbView);

        Text quote = new Text("\"If you ain't first, you're last.™\" - Ricky Bobby");
        quote.setFont(MENU_TITLE_FONT);
        quote.setFill(Paint.valueOf("#CC0000"));
        quote.setTextAlignment(TextAlignment.CENTER);
        intro.getChildren().add(quote);

        intro.setAlignment(Pos.CENTER);

        return intro;
    }

    private VBox createTimeLogger(){

        VBox timeLogger = new VBox();

        timeLogger.setPadding(new Insets(10));
        timeLogger.setSpacing(30);
        timeLogger.setAlignment(Pos.TOP_CENTER);

        Text title = new Text("Log Times");
        title.setFont(MENU_TITLE_FONT);
        title.setTextAlignment(TextAlignment.CENTER);
        timeLogger.getChildren().add(title);

        //Lables for each field
        Label[] labels = new Label[]{
                new Label("Event:"),
                new Label("Lap:"),
                new Label("Time:")
        };

        //Each field with default text already in directory (dummy text for now)
        TextField[] defaultFields = new TextField[]{
                new TextField("2016081"),
                new TextField("64"),
                new TextField("63.149")
        };

        //Add each label to grid pane
        //Set normal text font to each
        for(int i = 0; i < labels.length; i++){
            labels[i].setFont(TEXT_FONT);
            timeLogger.getChildren().add(labels[i]);
            timeLogger.getChildren().add(defaultFields[i]);
        }

        ToggleButton logTime = new ToggleButton("Log Time");

        timeLogger.getChildren().add(logTime);

        return timeLogger;
    }


    /**
     * Create a grid pane to show information based on menu option currently selected.
     */
    private ScrollPane createRequestExpenseScrollPane(){

        GridPane gridPane = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //Dummy info to be added for now
        Text[] expenseList = new Text[]{
                new Text(dummyExpense1),
                new Text(dummyExpense2),
                new Text(dummyExpense1),
                new Text(dummyExpense2),
                new Text(dummyExpense1),
                new Text(dummyExpense2),
                new Text(dummyExpense1),
                new Text(dummyExpense2),
        };

        //Add each directory string to the grid pane
        //Set font for each
        for(int i = 0; i < expenseList.length; i++){
            gridPane.add(expenseList[i], 1, i);
            expenseList[i].setFont(TEXT_FONT);

            //Add a seperator between directory members
            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, i, 2, 1);

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
     * Create a grid pane to show information based on menu option currently selected.
     */
    private ScrollPane createPrioritizeIssuesScrollPane(){
        GridPane gridPane = new GridPane();
        ToggleGroup toggleGroup = new ToggleGroup();

        //Set padding and gaps
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        int order = 1;

        //Dummy info to be added for now
        Text[] expenseList = new Text[]{
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue1),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue2),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue3),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue1),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue2),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue3),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue1),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue2),
                new Text("Order:\t\t\t" + order++ + "\n" + dummyIssue3),
        };

        //Add each directory string to the grid pane
        //Set font for each
        for(int i = 0; i < expenseList.length; i++){
            gridPane.add(expenseList[i], 1, i);
            expenseList[i].setFont(TEXT_FONT);

            //Add a seperator between directory members
            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, i, 2, 1);

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
            borderPane.setCenter(createRequestExpenseScrollPane());
            borderPane.setBottom(addDirectoryButtonHBox());
        });

        saveButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createRequestExpenseScrollPane());
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

        };

        //Add each directory string to the grid pane, setting a font size
        for(int i = 0; i < directory.length; i++){
            gridPane.add(directory[i], 1, i);
            //gridPane.setGridLinesVisible(true);
            directory[i].setFont(TEXT_FONT);

            //Add a seperator between events
            //Add a seperator between directory members
            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, i, 2, 1);

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
        vbox.setPadding(new Insets(30));

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
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        //Labels for grid pane
        Label transactionLabel = new Label("Transaction");
        transactionLabel.setFont(LABEL_FONT);

        Label remainingLabel = new Label("Funds Remaining");
        remainingLabel.setFont(LABEL_FONT);

        //Array of transactions as Texts
        Text[] transactions = new Text[]{

        };

        //Array of remaining funds as Texts
        Text[] remainingFunds = new Text[]{

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
}
