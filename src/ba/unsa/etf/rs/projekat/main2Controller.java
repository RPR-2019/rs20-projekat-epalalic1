package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class main2Controller {

    public Label profileLabel;
    public static main2Controller instance;
    public Button studentBtn;
    public Button ucenikBtn;
    public BorderPane pane;

    public main2Controller () {
        instance = this;
    }
    public static main2Controller  getInstance() {
        return instance;
    }

    Users user  = null;

    @FXML
    public void initialize ()  {
        studentBtn.getStyleClass().add("colorOfBackgroundofButton");
        ucenikBtn.getStyleClass().add("colorOfBackgroundofButton");

        formaController a = new formaController();
        if (mainController.getInstance().getUsers()!=null) {
            user = mainController.getInstance().getUsers();
        }
        else if (userController.getInstance() == null && logInController.getInstance().getUser()!=null) {
            user = logInController.getInstance().getUser();
        }
        else if (userController.getInstance().getUsers()!=null && logInController.getInstance() == null) {
            user = userController.getInstance().getUsers();
        }
        else if (userController.getInstance().getUsers() != null) {
            user = userController.getInstance().getUsers();
        }
        else if (logInController.getInstance().getUser()!=null ) {
            user = logInController.getInstance().getUser();
        }
       /* else if (userController.getInstance().getUsers()!=null) {
            user = userController.getInstance().getUsers();
        }*/

        profileLabel.setText("Dobro dosao/la " + user.getUsername());
    }

    public void student(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/logedInH.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void srednjoskolac(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/logedInH.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
