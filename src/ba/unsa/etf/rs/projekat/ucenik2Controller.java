package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
}
