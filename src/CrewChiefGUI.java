import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Jonathon Tovey on 10/21/2016 based on Ken Short's example.
 */
public class CrewChiefGUI extends Stage {
    private final Font MENU_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);
    private final Font HEADER_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 40);
    private final Font MENU_OPTION_FONT = Font.font("Arial", FontWeight.NORMAL, 18);
    private final Font LABEL_FONT = Font.font("Arial", FontWeight.BOLD, 18);
    private final Font TEXT_FONT = Font.font("Arial", FontWeight.NORMAL, 14);
    private final int DIR_FONT_SIZE = 14;
    private BorderPane borderPane;

    private ArrayList<TimeRecord> timeRecords;
    private ArrayList<TeamEvent> eventList;
    private int selectedEvent;
    private int currentLap = 1;

    private ArrayList<IssueRecord> issueRecords;

    private ArrayList<ExpenseRequest> expenseRequests;

    public CrewChiefGUI() {

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
        setScene(scene);
        setTitle("Crew Chief Interface");
        setMaximized(true);
        //show();

    }

    private HBox createTeamBranding() {

        HBox header = new HBox();

        header.setPadding(new Insets(10));
        header.setSpacing(30);
        header.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Text title = new Text("#26\nSponsor: Wonder\nDriver: Ricky Bobby");
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

    private VBox createStupidIntro() {

        VBox intro = new VBox();

        intro.setPadding(new Insets(10));
        intro.setSpacing(30);

        Text quote = new Text("\"If you ain't first, you're last.™\" - Ricky Bobby");
        quote.setFont(MENU_TITLE_FONT);
        quote.setFill(Paint.valueOf("#CC0000"));
        quote.setTextAlignment(TextAlignment.CENTER);
        intro.getChildren().add(quote);

        // ricky bobby image
        File rbFile = new File("img/ricky-bobby-750h.png");
        Image rbImage = new Image(rbFile.toURI().toString());
        ImageView rbView = new ImageView(rbImage);
        intro.getChildren().add(rbView);

        intro.setAlignment(Pos.CENTER);

        return intro;
    }

    /**
     * Create a VBox to hold the menu on left side of border pane
     */
    private VBox createSideMenuVBox() {
        VBox menu = new VBox();

        //Set the padding around the vbox
        menu.setPadding(new Insets(10));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        int prefWidth = 200;

        //Set the title for the menu
        Text title = new Text("Crew Chief");
        title.setFont(MENU_TITLE_FONT);
        title.setTextAlignment(TextAlignment.CENTER);
        title.prefWidth(prefWidth);
        menu.getChildren().add(title);

        //Create menu buttons
        ToggleButton intro = new ToggleButton("Intro");
        intro.setPrefWidth(prefWidth);
        intro.setFont(MENU_TITLE_FONT);
        ToggleButton requestExpenses = new ToggleButton("Request Expense");
        requestExpenses.setPrefWidth(prefWidth);
        requestExpenses.setFont(MENU_TITLE_FONT);
        ToggleButton prioritizeIssues = new ToggleButton("Prioritize Issues");
        prioritizeIssues.setPrefWidth(prefWidth);
        prioritizeIssues.setFont(MENU_TITLE_FONT);
        ToggleButton viewTimes = new ToggleButton("View Times");
        viewTimes.setPrefWidth(prefWidth);
        viewTimes.setFont(MENU_TITLE_FONT);

        //Add buttons to Togglegroup so only one can be toggled on
        ToggleGroup toggleGroup = new ToggleGroup();
        intro.setToggleGroup(toggleGroup);
        requestExpenses.setToggleGroup(toggleGroup);
        prioritizeIssues.setToggleGroup(toggleGroup);
        viewTimes.setToggleGroup(toggleGroup);

        intro.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createStupidIntro());
            intro.setSelected(false);
        });

        //Add events to buttons to execute when clicked
        requestExpenses.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createRequestExpenseScrollPane());
            requestExpenses.setSelected(false);
        });

        prioritizeIssues.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createPrioritizeIssuesScrollPane());
            prioritizeIssues.setSelected(false);
        });

        viewTimes.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createViewTimesScrollPane());
            viewTimes.setSelected(false);
        });

        //Array of menu options
        ToggleButton[] menuOptions = new ToggleButton[]{intro, requestExpenses, prioritizeIssues, viewTimes};

        //Add each of the menu options to the vbox
        for (int i = 0; i < menuOptions.length; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(menuOptions[i], new Insets(0, 0, 0, 40));
            menu.getChildren().add(menuOptions[i]);

            //Set the font size of the menu option
            menuOptions[i].setFont(MENU_OPTION_FONT);

        }

        return menu;
    }


    // get array of events for createTimeLogger
    private String[] getEventsArray() {
        EventAccess ea = new EventAccess();
        eventList = ea.getEvents();

        String[] eventStrs = new String[eventList.size()];
        int eventItr = 0;

        if (eventList == null) {
            return null;
        }

        for (TeamEvent te : eventList) {
            String str = te.getTitle();
            str += " (" + te.getDate() + ")";
            eventStrs[eventItr++] = str;
        }

        return eventStrs;
    }

    private VBox createTimeLogger() {

        VBox timeLogger = new VBox();

        timeLogger.setPadding(new Insets(10));
        timeLogger.setSpacing(30);
        timeLogger.setAlignment(Pos.TOP_CENTER);

        Text title = new Text("Log Times");
        title.setFont(MENU_TITLE_FONT);
        title.setTextAlignment(TextAlignment.CENTER);
        timeLogger.getChildren().add(title);

        Label eventLabel = new Label("Event:");
        eventLabel.setFont(TEXT_FONT);
        timeLogger.getChildren().add(eventLabel);

        String[] events = getEventsArray();
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(events));
        choiceBox.setTooltip(new Tooltip("Select Event"));
        choiceBox.getSelectionModel().selectFirst();

        //Create choice box to allow user to select interface (team owner/crew chief)
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedEvent = newValue.intValue();
            }
        });

        timeLogger.getChildren().add(choiceBox);

        Label lapLabel = new Label("Lap");
        lapLabel.setFont(TEXT_FONT);
        TextField lapField = new TextField(String.valueOf(currentLap));
        timeLogger.getChildren().addAll(lapLabel, lapField);

        Label timeLabel = new Label("Time");
        timeLabel.setFont(TEXT_FONT);
        TextField timeField = new TextField("");
        timeLogger.getChildren().addAll(timeLabel, timeField);

        ToggleButton logTime = new ToggleButton("Log Time");
        logTime.setFont(MENU_TITLE_FONT);
        logTime.setOnAction((ActionEvent e) -> {

            int lap = Integer.parseInt(lapField.getText());
            Double time = Double.parseDouble(timeField.getText());
            TimeRecord tr = new TimeRecord(eventList.get(selectedEvent).getTitle(), eventList.get(selectedEvent).getDate(), lap, time);

            timeRecords = TimeAccess.getTimeRecords();
            if (timeRecords == null) {
                timeRecords = new ArrayList<TimeRecord>();
            }
            timeRecords.add(tr);

            TimeAccess.saveTimeRecords(timeRecords);

            currentLap = lap;
            lapField.setText(String.valueOf(++currentLap));
            timeField.setText("");
            logTime.setSelected(false);

        });

        timeLogger.getChildren().add(logTime);

        return timeLogger;
    }

    private ScrollPane createRequestExpenseScrollPane() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        int gridRow = 0;

        Button addButton = new Button("Add New Expense Request");
        addButton.setFont(MENU_TITLE_FONT);
        addButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createExpenseRequestFormGridPaneAdd());
        });
        gridPane.add(addButton, 0, gridRow++, 3, 1);

        expenseRequests = ExpenseAccess.getExpenseRequests();

        if (expenseRequests == null) {
            gridPane.add(new Text("No Expense Requests"), 0, gridRow++, 3, 1);
            ScrollPane scrollPane = new ScrollPane(gridPane);
            return scrollPane;
        }

        for (ExpenseRequest er : expenseRequests) {

            Text txtDescription = new Text(String.valueOf(er.getDescription()));
            txtDescription.setFont(LABEL_FONT);
            Text txtCost = new Text(String.valueOf(er.getCost()));
            txtCost.setFont(TEXT_FONT);
            Text txtTimeline = new Text(er.getTimeline());
            txtTimeline.setFont(TEXT_FONT);
            Text txtPriority = new Text(er.getPriority());
            txtPriority.setFont(TEXT_FONT);

            Text descriptionTxt = new Text("Description: ");
            Text costTxt = new Text("Cost: ");
            Text timelineTxt = new Text("Timeline: ");
            Text priorityTxt = new Text("Priority: ");

            gridPane.add(descriptionTxt, 0, gridRow, 1, 1);
            gridPane.add(txtDescription, 1, gridRow++, 1, 1);

            gridPane.add(costTxt, 0, gridRow, 1, 1);
            gridPane.add(txtCost, 1, gridRow++, 1, 1);

            gridPane.add(timelineTxt, 0, gridRow, 1, 1);
            gridPane.add(txtTimeline, 1, gridRow++, 1, 1);

            gridPane.add(priorityTxt, 0, gridRow, 1, 1);
            gridPane.add(txtPriority, 1, gridRow++, 1, 1);

            Button editButton = new Button("Edit");
            editButton.setFont(MENU_TITLE_FONT);
            editButton.setOnAction((ActionEvent e) -> {
                borderPane.setCenter(createExpenseRequestFormGridPaneEdit(er));
            });

            Button delButton = new Button("Delete");
            delButton.setFont(MENU_TITLE_FONT);
            delButton.setOnAction((ActionEvent e) -> {
                expenseRequests.remove(er);
                ExpenseAccess.saveExpenseRequests(expenseRequests);
                borderPane.setCenter(createRequestExpenseScrollPane());
            });

            gridPane.add(editButton, 0, gridRow, 1, 1);
            gridPane.add(delButton, 2, gridRow++, 1, 1);

            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, gridRow++, 3, 1);
        }


        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;
    }

    private GridPane createExpenseRequestFormGridPaneAdd() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        int gridRow = 0;

        Label descriptionLabel = new Label("Description: ");
        Label costLabel = new Label("Cost: ");
        Label timelineLabel = new Label("Timeline: ");
        Label priorityLabel = new Label("Priority: ");

        TextField descriptionField = new TextField("");
        TextField costField = new TextField("");
        TextField timelineField = new TextField("");
        TextField priorityField = new TextField("");

        gridPane.add(descriptionLabel, 0, gridRow, 1, 1);
        gridPane.add(descriptionField, 1, gridRow++, 1, 1);

        gridPane.add(costLabel, 0, gridRow, 1, 1);
        gridPane.add(costField, 1, gridRow++, 1, 1);

        gridPane.add(timelineLabel, 0, gridRow, 1, 1);
        gridPane.add(timelineField, 1, gridRow++, 1, 1);

        gridPane.add(priorityLabel, 0, gridRow, 1, 1);
        gridPane.add(priorityField, 1, gridRow++, 1, 1);

        Button saveButton = new Button("Add New Expense Request");
        saveButton.setFont(MENU_TITLE_FONT);
        saveButton.setOnAction((ActionEvent e) -> {
            if (expenseRequests == null) {
                expenseRequests = new ArrayList<ExpenseRequest>();
            }
            ExpenseRequest er = new ExpenseRequest(descriptionField.getText(),
                    Double.parseDouble(costField.getText()),
                    timelineField.getText(),
                    priorityField.getText());
            expenseRequests.add(er);
            ExpenseAccess.saveExpenseRequests(expenseRequests);

            borderPane.setCenter(createRequestExpenseScrollPane());
        });

        gridPane.add(saveButton, 1, ++gridRow, 1, 1);

        return gridPane;
    }

    private GridPane createExpenseRequestFormGridPaneEdit(ExpenseRequest record) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        int gridRow = 0;

        Label descriptionLabel = new Label("Description: ");
        Label costLabel = new Label("Cost: ");
        Label timelineLabel = new Label("Timeline: ");
        Label priorityLabel = new Label("Priority: ");

        TextField descriptionField = new TextField(record.getDescription());
        TextField costField = new TextField(String.valueOf(record.getCost()));
        TextField timelineField = new TextField(record.getTimeline());
        TextField priorityField = new TextField(record.getPriority());

        gridPane.add(descriptionLabel, 0, gridRow, 1, 1);
        gridPane.add(descriptionField, 1, gridRow++, 1, 1);

        gridPane.add(costLabel, 0, gridRow, 1, 1);
        gridPane.add(costField, 1, gridRow++, 1, 1);

        gridPane.add(timelineLabel, 0, gridRow, 1, 1);
        gridPane.add(timelineField, 1, gridRow++, 1, 1);

        gridPane.add(priorityLabel, 0, gridRow, 1, 1);
        gridPane.add(priorityField, 1, gridRow++, 1, 1);

        Button saveButton = new Button("Save Changes");
        saveButton.setFont(MENU_TITLE_FONT);
        saveButton.setOnAction((ActionEvent e) -> {
            expenseRequests.remove(record);
            ExpenseRequest er = new ExpenseRequest(descriptionField.getText(),
                    Double.parseDouble(costField.getText()),
                    timelineField.getText(),
                    priorityField.getText());
            expenseRequests.add(er);
            ExpenseAccess.saveExpenseRequests(expenseRequests);

            borderPane.setCenter(createRequestExpenseScrollPane());
        });

        gridPane.add(saveButton, 1, ++gridRow, 1, 1);

        return gridPane;

    }

    public static Comparator<IssueRecord> issueComparator = new Comparator<IssueRecord>() {

        @Override
        public int compare(IssueRecord ir1, IssueRecord ir2) {

            return (ir1.getOrder() - ir2.getOrder());
        }

    };

    public void sortIssues(){

        issueRecords.sort(issueComparator);

    }


    private ScrollPane createPrioritizeIssuesScrollPane() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        int gridRow = 0;

        Button addButton = new Button("Add New Issue");
        addButton.setFont(MENU_TITLE_FONT);
        addButton.setOnAction((ActionEvent e) -> {
            borderPane.setCenter(createPrioritizeIssuesFormGridPaneAdd());
        });
        gridPane.add(addButton, 0, gridRow++, 3, 1);

        issueRecords = IssueAccess.getIssueRecords();

        if (issueRecords == null) {
            gridPane.add(new Text("No Issue Records"), 0, gridRow++, 1, 1);
            ScrollPane scrollPane = new ScrollPane(gridPane);
            return scrollPane;
        } else {
            sortIssues();
        }

        for (IssueRecord ir : issueRecords) {

            Text txtOrder = new Text(String.valueOf(ir.getOrder()));
            txtOrder.setFont(LABEL_FONT);
            Text txtDescription = new Text(ir.description);
            txtDescription.setFont(TEXT_FONT);
            Text txtSolution = new Text(ir.solution);
            txtSolution.setFont(TEXT_FONT);
            Text txtActionsTaken = new Text(ir.actionsTaken);
            txtActionsTaken.setFont(TEXT_FONT);
            Text txtTimeline = new Text(ir.timeline);
            txtTimeline.setFont(TEXT_FONT);
            Text txtPriority = new Text(ir.priority);
            txtPriority.setFont(TEXT_FONT);
            Text txtStatus = new Text(ir.status);
            txtStatus.setFont(TEXT_FONT);


            Text orderTxt = new Text("Order: ");
            Text descriptionTxt = new Text("Description: ");
            Text solutionTxt = new Text("Solution: ");
            Text actionsTakenTxt = new Text("Actions Taken: ");
            Text timelineTxt = new Text("Timeline: ");
            Text priorityTxt = new Text("Priority: ");
            Text statusTxt = new Text("Status: ");


            gridPane.add(orderTxt, 0, gridRow, 1, 1);
            gridPane.add(txtOrder, 1, gridRow++, 1, 1);

            gridPane.add(descriptionTxt, 0, gridRow, 1, 1);
            gridPane.add(txtDescription, 1, gridRow++, 1, 1);

            gridPane.add(solutionTxt, 0, gridRow, 1, 1);
            gridPane.add(txtSolution, 1, gridRow++, 1, 1);

            gridPane.add(actionsTakenTxt, 0, gridRow, 1, 1);
            gridPane.add(txtActionsTaken, 1, gridRow++, 1, 1);

            gridPane.add(timelineTxt, 0, gridRow, 1, 1);
            gridPane.add(txtTimeline, 1, gridRow++, 1, 1);

            gridPane.add(priorityTxt, 0, gridRow, 1, 1);
            gridPane.add(txtPriority, 1, gridRow++, 1, 1);

            gridPane.add(statusTxt, 0, gridRow, 1, 1);
            gridPane.add(txtStatus, 1, gridRow++, 1, 1);


            Button editButton = new Button("Edit");
            editButton.setFont(MENU_TITLE_FONT);
            editButton.setOnAction((ActionEvent e) -> {
                borderPane.setCenter(createPrioritizeIssuesFormGridPaneEdit(ir));
            });

            Button delButton = new Button("Delete");
            delButton.setFont(MENU_TITLE_FONT);
            delButton.setOnAction((ActionEvent e) -> {

                // reorder on deletion
                int currentOrder = ir.getOrder();
                issueRecords.remove(ir);
                for(IssueRecord ir2 : issueRecords){
                    if(ir2.getOrder() > currentOrder){
                        ir2.setOrder(ir2.getOrder() - 1);
                    }
                }

                sortIssues();
                IssueAccess.saveIssueRecords(issueRecords);
                borderPane.setCenter(createPrioritizeIssuesScrollPane());
            });

            gridPane.add(editButton, 0, gridRow, 1, 1);
            gridPane.add(delButton, 2, gridRow++, 1, 1);

            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, gridRow++, 3, 1);
        }


        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;

    }


    private GridPane createPrioritizeIssuesFormGridPaneAdd() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        int gridRow = 0;

        Label orderLabel = new Label("Order: ");
        Label descriptionLabel = new Label("Description: ");
        Label solutionLabel = new Label("Solution: ");
        Label actionsTakenLabel = new Label("Actions Taken: ");
        Label timelineLabel = new Label("Timeline: ");
        Label priorityLabel = new Label("Priority: ");
        Label statusLabel = new Label("Status: ");

        TextField orderField = new TextField("");
        TextField descriptionField = new TextField("");
        TextField solutionField = new TextField("");
        TextField actionsTakenField = new TextField("");
        TextField timelineField = new TextField("");
        TextField priorityField = new TextField("");
        TextField statusField = new TextField("");

        gridPane.add(orderLabel, 0, gridRow, 1, 1);
        gridPane.add(orderField, 1, gridRow++, 1, 1);

        gridPane.add(descriptionLabel, 0, gridRow, 1, 1);
        gridPane.add(descriptionField, 1, gridRow++, 1, 1);

        gridPane.add(solutionLabel, 0, gridRow, 1, 1);
        gridPane.add(solutionField, 1, gridRow++, 1, 1);

        gridPane.add(actionsTakenLabel, 0, gridRow, 1, 1);
        gridPane.add(actionsTakenField, 1, gridRow++, 1, 1);

        gridPane.add(timelineLabel, 0, gridRow, 1, 1);
        gridPane.add(timelineField, 1, gridRow++, 1, 1);

        gridPane.add(priorityLabel, 0, gridRow, 1, 1);
        gridPane.add(priorityField, 1, gridRow++, 1, 1);

        gridPane.add(statusLabel, 0, gridRow, 1, 1);
        gridPane.add(statusField, 1, gridRow++, 1, 1);

        Button saveButton = new Button("Add New Issue");
        saveButton.setFont(MENU_TITLE_FONT);
        saveButton.setOnAction((ActionEvent e) -> {
            if (issueRecords == null) {
                issueRecords = new ArrayList<IssueRecord>();
            }
            IssueRecord ir = new IssueRecord(Integer.parseInt(orderField.getText()),
                    descriptionField.getText(),
                    solutionField.getText(),
                    actionsTakenField.getText(),
                    timelineField.getText(),
                    priorityField.getText(),
                    statusField.getText());

            // reorder on addition
            int currentOrder = ir.getOrder();
            for(IssueRecord ir2 : issueRecords){
                if(ir2.getOrder() >= currentOrder){
                    ir2.setOrder(ir2.getOrder() + 1);
                }
            }

            issueRecords.add(ir);
            sortIssues();
            IssueAccess.saveIssueRecords(issueRecords);

            borderPane.setCenter(createPrioritizeIssuesScrollPane());
        });

        gridPane.add(saveButton, 1, ++gridRow, 1, 1);

        return gridPane;
    }

    private GridPane createPrioritizeIssuesFormGridPaneEdit(IssueRecord record) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        int gridRow = 0;

        Label orderLabel = new Label("Order: ");
        Label descriptionLabel = new Label("Description: ");
        Label solutionLabel = new Label("Solution: ");
        Label actionsTakenLabel = new Label("Actions Taken: ");
        Label timelineLabel = new Label("Timeline: ");
        Label priorityLabel = new Label("Priority: ");
        Label statusLabel = new Label("Status: ");

        TextField orderField = new TextField(Integer.toString(record.getOrder()));
        TextField descriptionField = new TextField(record.getDescription());
        TextField solutionField = new TextField(record.getSolution());
        TextField actionsTakenField = new TextField(record.getActionsTaken());
        TextField timelineField = new TextField(record.getTimeline());
        TextField priorityField = new TextField(record.getPriority());
        TextField statusField = new TextField(record.getStatus());

        gridPane.add(orderLabel, 0, gridRow, 1, 1);
        gridPane.add(orderField, 1, gridRow++, 1, 1);

        gridPane.add(descriptionLabel, 0, gridRow, 1, 1);
        gridPane.add(descriptionField, 1, gridRow++, 1, 1);

        gridPane.add(solutionLabel, 0, gridRow, 1, 1);
        gridPane.add(solutionField, 1, gridRow++, 1, 1);

        gridPane.add(actionsTakenLabel, 0, gridRow, 1, 1);
        gridPane.add(actionsTakenField, 1, gridRow++, 1, 1);

        gridPane.add(timelineLabel, 0, gridRow, 1, 1);
        gridPane.add(timelineField, 1, gridRow++, 1, 1);

        gridPane.add(priorityLabel, 0, gridRow, 1, 1);
        gridPane.add(priorityField, 1, gridRow++, 1, 1);

        gridPane.add(statusLabel, 0, gridRow, 1, 1);
        gridPane.add(statusField, 1, gridRow++, 1, 1);

        Button saveButton = new Button("Save Changes");
        saveButton.setFont(MENU_TITLE_FONT);
        saveButton.setOnAction((ActionEvent e) -> {

            // reorder on deletion
            int currentOrder = record.getOrder();
            issueRecords.remove(record);
            for(IssueRecord ir2 : issueRecords){
                if(ir2.getOrder() > currentOrder){
                    ir2.setOrder(ir2.getOrder() - 1);
                }
            }

            if (issueRecords == null) {
                issueRecords = new ArrayList<IssueRecord>();
            }
            IssueRecord ir = new IssueRecord(Integer.parseInt(orderField.getText()),
                    descriptionField.getText(),
                    solutionField.getText(),
                    actionsTakenField.getText(),
                    timelineField.getText(),
                    priorityField.getText(),
                    statusField.getText());

            // reorder on addition
            currentOrder = ir.getOrder();
            for(IssueRecord ir2 : issueRecords){
                if(ir2.getOrder() >= currentOrder){
                    ir2.setOrder(ir2.getOrder() + 1);
                }
            }

            issueRecords.add(ir);
            sortIssues();

            IssueAccess.saveIssueRecords(issueRecords);
            borderPane.setCenter(createPrioritizeIssuesScrollPane());
        });

        gridPane.add(saveButton, 1, ++gridRow, 1, 1);

        return gridPane;

    }


    private ScrollPane createViewTimesScrollPane() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        int gridRow = 0;

        timeRecords = TimeAccess.getTimeRecords();

        if (timeRecords == null) {
            gridPane.getChildren().add(new Text("No Lap Records"));
            ScrollPane scrollPane = new ScrollPane(gridPane);
            return scrollPane;
        }

        for (TimeRecord tr : timeRecords) {
            Text txt1 = new Text(tr.eventName + " (" + tr.eventDate + ")");
            txt1.setFont(LABEL_FONT);
            Text txt2 = new Text(String.valueOf(tr.lapNum));
            txt2.setFont(TEXT_FONT);
            Text txt3 = new Text(String.valueOf(tr.lapTime));
            txt3.setFont(TEXT_FONT);

            Text lapTxt = new Text("Lap: ");
            Text timeTxt = new Text("Time: ");

            gridPane.add(txt1, 0, gridRow++, 3, 1);

            gridPane.add(lapTxt, 0, gridRow, 2, 1);
            gridPane.add(txt2, 2, gridRow++, 1, 1);

            gridPane.add(timeTxt, 0, gridRow, 2, 1);
            gridPane.add(txt3, 2, gridRow++, 1, 1);

            Button editButton = new Button("Edit");
            editButton.setFont(MENU_TITLE_FONT);
            editButton.setOnAction((ActionEvent e) -> {
                borderPane.setCenter(createTimeRecordFormGridPaneEdit(tr));
            });

            Button delButton = new Button("Delete");
            delButton.setFont(MENU_TITLE_FONT);
            delButton.setOnAction((ActionEvent e) -> {
                timeRecords.remove(tr);
                TimeAccess.saveTimeRecords(timeRecords);
                borderPane.setCenter(createViewTimesScrollPane());
            });

            gridPane.add(editButton, 0, gridRow, 1, 1);
            gridPane.add(delButton, 2, gridRow++, 1, 1);

            Separator separator = new Separator();
            gridPane.setValignment(separator, VPos.BOTTOM);
            gridPane.add(separator, 0, gridRow++, 3, 1);
        }


        ScrollPane scrollPane = new ScrollPane(gridPane);

        return scrollPane;
    }

    private GridPane createTimeRecordFormGridPaneEdit(TimeRecord record) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        int gridRow = 0;

        Label nameLabel = new Label("Event Name: ");
        Label dateLabel = new Label("Event Date: ");
        Label numLabel = new Label("Lap Number: ");
        Label timeLabel = new Label("Lap Time: ");

        TextField nameField = new TextField(record.getEventName());
        TextField dateField = new TextField(String.valueOf(record.getEventDate()));
        TextField numField = new TextField(String.valueOf(record.getLapNum()));
        TextField timeField = new TextField(String.valueOf(record.getLapTime()));

        gridPane.add(nameLabel, 0, gridRow, 1, 1);
        gridPane.add(nameField, 1, gridRow++, 1, 1);

        gridPane.add(dateLabel, 0, gridRow, 1, 1);
        gridPane.add(dateField, 1, gridRow++, 1, 1);

        gridPane.add(numLabel, 0, gridRow, 1, 1);
        gridPane.add(numField, 1, gridRow++, 1, 1);

        gridPane.add(timeLabel, 0, gridRow, 1, 1);
        gridPane.add(timeField, 1, gridRow++, 1, 1);

        Button saveButton = new Button("Save Changes");
        saveButton.setFont(MENU_TITLE_FONT);
        saveButton.setOnAction((ActionEvent e) -> {
            timeRecords.remove(record);
            TimeRecord tr = new TimeRecord(nameField.getText(),
                    dateField.getText(),
                    Integer.parseInt(numField.getText()),
                    Double.parseDouble(timeField.getText()));
            timeRecords.add(tr);
            TimeAccess.saveTimeRecords(timeRecords);

            borderPane.setCenter(createViewTimesScrollPane());
        });

        gridPane.add(saveButton, 1, ++gridRow, 1, 1);

        return gridPane;
    }
}

