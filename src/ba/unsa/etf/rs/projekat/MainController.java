package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    @FXML
    private BorderPane pane;


    public void student(ActionEvent actionEvent) throws IOException {
        BorderPane pane1 = FXMLLoader.load(getClass().getResource("/fxml/student.fxml"));
        pane.getChildren().setAll(pane1);
    }

    public void srednjoskolac(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/srednjoskolac.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Biljeske s predavanja");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setFullScreen(true);
        myStage.show();
    }
}
