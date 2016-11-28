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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by ken12_000 on 11/12/2016.
 */
public class LoginGUI extends Application {
    private int selectedUser = 0; //Starts off on owner
    private final String SELECT_USER_MSG = "Please select a user to proceed.";

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

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
            }
        });

        choiceBox.setMaxWidth(Double.MAX_VALUE);
        loginButton.setMaxWidth(Double.MAX_VALUE);

        vBox.setSpacing(30);
        vBox.setPadding(new Insets(150, 150, 150, 150));
        vBox.getChildren().addAll(choiceBox, loginButton);

        //create the scene and add it to the stage
        //Create and show scene
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Login");
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
