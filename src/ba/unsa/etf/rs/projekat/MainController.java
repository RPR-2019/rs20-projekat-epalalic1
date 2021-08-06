package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane pane;


    public void student(ActionEvent actionEvent) throws IOException {
        BorderPane pane1 = FXMLLoader.load(getClass().getResource("/fxml/student.fxml"));
        pane.getChildren().setAll(pane1);
    }

    public void srednjoskolac(ActionEvent actionEvent) throws IOException {
        BorderPane pane1 = FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
        pane.getChildren().setAll(pane1);
    }
}
