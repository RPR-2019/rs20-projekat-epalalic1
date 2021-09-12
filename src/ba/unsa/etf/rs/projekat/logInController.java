package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class logInController {
    public TextField usernameFld;
    public PasswordField passwordFld;
    public Label wrongData;
    public GridPane pane;
    public Button logIn;
    private Users user = null;

    public static logInController instance;
    public logInController () {
        instance = this;
    }
    public static logInController getInstance() {
        return instance;
    }

    @FXML
    public void initialize () {
        pane.getStyleClass().add("colorOfBackground");
        logIn.getStyleClass().add("colorOfBackground");
    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        NotesDAO dao = NotesDAO.getInstance();
        if (dao.checkIfUserExists(usernameFld.getText(),passwordFld.getText())) {
            user = dao.returnUser(usernameFld.getText(),passwordFld.getText());

            Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
            Scene scene = new Scene(homePage);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage stage = null;
            if ((Stage) mainController.getInstance().buttonId.getScene().getWindow()!=null) {
                stage = (Stage) mainController.getInstance().buttonId.getScene().getWindow();
            }
            else if ((Stage) userController.getInstance().buttonHelp.getScene().getWindow()!=null) {
                stage = (Stage) userController.getInstance().buttonHelp.getScene().getWindow();
            }

            //Stage stage = (Stage) MainController.getInstance().buttonId.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            wrongData.setText("Korisnicki podaci nisu tacni");
        }


    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
