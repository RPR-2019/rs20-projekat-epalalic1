package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ucenik2Controller {
    public Button buttonNote;

    public void logOutAction(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) buttonNote.getScene().getWindow();
        //Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void addNoteAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newNote.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }
}
