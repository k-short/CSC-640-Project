import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by ken12_000 on 11/29/2016.
 */
public class ErrorDialog extends Stage {

    public ErrorDialog(String errorStr){
        Text error = new Text(errorStr);
        Font errorFont = Font.font("Arial", FontWeight.BOLD, 18);
        error.setFont(errorFont);

        VBox vBox = new VBox(error);
        vBox.setPadding(new Insets(50, 50, 50, 50));
        vBox.getChildren().add(error);

        //Create and show scene
        Scene scene = new Scene(vBox);
        setScene(scene);
        setTitle("Error Dialog");
    }
}
