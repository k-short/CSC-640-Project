import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by ken12_000 on 10/21/2016.
 */
public class TeamOwnerGUI extends Application{
    private final int MENU_TITLE_FONT_SIZE = 20;
    private final int MENU_OPTION_FONT_SIZE = 18;
    private final int DIR_FONT_SIZE = 14;

    String dummyDir= "Kenneth Short\n" + "Lead Car Cleaner\n" +"500 Kale Court, Greensboro, NC 27403\n" +
            "543-345-2222\n";

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Use a border pane as the root for the scene
        BorderPane borderPane = new BorderPane();

        //Create menu (VBox) to go on left side of border pane
        borderPane.setLeft(addSideMenu());

        //Create grid pane to display main info, for the center of the border pane
        borderPane.setCenter(addInfoPane());

        //Create a button panel to be added at the bottom of the border pane.
        //These are the buttons that will interact with info in center pane.
        borderPane.setBottom(addButtonPanel());

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
    private VBox addSideMenu(){
        VBox menu = new VBox();

        //Set the padding around the vbox
        menu.setPadding(new Insets(10));

        //Set the amount of space inbetween menu items
        menu.setSpacing(30);

        //Set the title for the menu
        Text title = new Text("Team Owner");
        title.setFont(Font.font("Arial", FontWeight.BOLD, MENU_TITLE_FONT_SIZE));
        menu.getChildren().add(title);

        //Array of menu options
        Text[] menuOptions = new Text[]{
                new Text("Event Schedule"),
                new Text("Team Directory"),
                new Text("Team Funds"),
                new Text("Expense Requests")};

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
    private ScrollPane addInfoPane(){
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
        for(int i = 0; i < directory.length; i++){
            gridPane.add(directory[i], 1, i);
            directory[i].setFont(Font.font(DIR_FONT_SIZE));

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
    private HBox addButtonPanel(){
        HBox buttonPanel = new HBox();
        buttonPanel.setAlignment(Pos.CENTER);

        //Set the padding around the button panel
        buttonPanel.setPadding(new Insets(10));

        //Set the gaps between buttons
        buttonPanel.setSpacing(60);

        //Create some default buttons for now
        Button b1 = new Button("Delete");
        Button b2 = new Button("Edit");

        //Add the buttons to the hbox
        buttonPanel.getChildren().addAll(b1, b2);

        return buttonPanel;
    }
}
