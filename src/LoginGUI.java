import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by ken12_000 on 11/12/2016.
 */
public class LoginGUI extends Application {
    private int selectedUser = 0; //Starts off on owner
    private final String SELECT_USER_MSG = "Please select a user to proceed.";
    private final Font MENU_TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 20);

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        vBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        // nascar logo
        File nascarFile = new File("img/nascar-85h.png");
        Image nascarImage = new Image(nascarFile.toURI().toString());
        ImageView nascarView = new ImageView(nascarImage);

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Team Owner", "Crew Chief"));

        choiceBox.setTooltip(new Tooltip("Select user"));
        choiceBox.getSelectionModel().selectFirst();

        //Create choice box to allow user to select interface (team owner/crew chief)
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedUser = newValue.intValue();
            }
        });

        //Create login button that navigates to the selected interface
        Button loginButton = new Button("Login");
        loginButton.setOnAction((ActionEvent e) -> {
            if(selectedUser == 0){
                Stage curStage = new TeamOwnerGUI();
                curStage.show();
                primaryStage.close();
            }else if(selectedUser == 1){
                //go to crew chief interface
                Stage curStage = new CrewChiefGUI();
                curStage.show();
                primaryStage.close();
            }
        });

        choiceBox.setMaxWidth(Double.MAX_VALUE);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setFont(MENU_TITLE_FONT);

        vBox.setSpacing(30);
        vBox.setPadding(new Insets(150, 150, 150, 150));
        vBox.getChildren().addAll(nascarView, choiceBox, loginButton);

        //create the scene and add it to the stage
        //Create and show scene
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Login");
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
