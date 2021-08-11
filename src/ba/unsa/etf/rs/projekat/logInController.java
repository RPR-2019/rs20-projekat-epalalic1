package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class logInController {
    public TextField usernameFld;
    public PasswordField passwordFld;


    public void logInAction(ActionEvent actionEvent) {
        NotesDAO dao = NotesDAO.getInstance();
        if (dao.checkIfUserExists(usernameFld.getText(),passwordFld.getText())) {
            System.out.println("Pronasli smo korisnika");
        }
        else {
            System.out.println("Nismo pronasli korisnika");
        }

    }
}
