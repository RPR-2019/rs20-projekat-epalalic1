package ba.unsa.etf.rs.projekat;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class logInController {
    public TextField usernameFld;
    public PasswordField passwordFld;
    private Users user = null;

    public static logInController instance;
    public logInController () {
        instance = this;
    }
    public static logInController getInstance() {
        return instance;
    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        NotesDAO dao = NotesDAO.getInstance();
        if (dao.checkIfUserExists(usernameFld.getText(),passwordFld.getText())) {
            user = dao.returnUser(usernameFld.getText(),passwordFld.getText());

            Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
            Scene scene = new Scene(homePage);
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            Stage stage = (Stage) MainController.getInstance().buttonId.getScene().getWindow();
           // Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //stage.hide();
            stage.setScene(scene);
            stage.show();
        }
        else {
            System.out.println("Korisnik ne postoji");
        }


    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
