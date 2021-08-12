package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class main2Controller {

    public Label profileLabel;

    Users user  = null;

    @FXML
    public void initialize ()  {
        user = MainController.getInstance().getUsers();
        profileLabel.setText("Dobro dosao/la " + user.getUsername());
    }

    public void student(ActionEvent actionEvent) throws IOException {
        //ovdje ovo ne treba jer sad se korisnik logovao i potrebno je izmijeniti prozore student i srednjoskolac
        //jer sad umjesto dugmica log in i sign in treba da pise dodaj biljesku  ili slicno
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/student.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void srednjoskolac(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
