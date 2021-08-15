package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UcenikController {
    public static UcenikController instance;
    public TextField searchNote;
    public ListView<Notes> resultOfSearch;
    public ComboBox<Subjects> chooseSubject;
    public ComboBox<String> chooseTopic;

    public UcenikController () {
        instance = this;
    }
    public static UcenikController  getInstance() {
        return instance;
    }
    Users users = null;

    public Button buttonHelp;

    @FXML
    public void initialize () {

    }




    public void signup(ActionEvent actionEvent) throws IOException {
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


    public void searchAction(ActionEvent actionEvent) {

    }
}
