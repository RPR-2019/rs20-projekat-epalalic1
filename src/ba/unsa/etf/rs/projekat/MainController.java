package ba.unsa.etf.rs.projekat;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public Button proba;
    public Button buttonId;
    public Label profileLabel;
    public Button buttonId2;
    public Button logIn;
    public Button signIn;
    @FXML
    private BorderPane pane;
    @FXML
    public Button buttonHelp;
    Users users = null;
    public static MainController instance;
    public MainController () {
            instance = this;
    }
    public static MainController  getInstance() {
        return instance;
    }
    @FXML
    public void initialize () {
        pane.getStyleClass().add("colorOfBackground");
        buttonId.getStyleClass().add("colorOfBackgroundofButton");
        buttonId2.getStyleClass().add("colorOfBackgroundofButton");
        logIn.getStyleClass().add("colorOfBackgroundofButton");
        signIn.getStyleClass().add("colorOfBackgroundofButton");

    }

    public void student(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
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

    public void signAction(ActionEvent actionEvent) throws IOException {
        FormaController a = new FormaController();
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/forma.fxml"));
        loader.setController(a);
        Parent root = loader.load();
        myStage.setTitle("Novi prozor");
        myStage.setScene(new Scene(root, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        if (!myStage.isShowing()) {
            if (a.check()!=null) {
                users = a.check();
                Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
                Scene scene = new Scene(homePage);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //stage.hide();
                stage.setScene(scene);
                stage.show();

            }
        }
       

    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogIn.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
