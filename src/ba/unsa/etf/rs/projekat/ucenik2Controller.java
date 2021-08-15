package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ucenik2Controller {
    public Button buttonNote;
    public TextField searchNote;
    public Button buttonHelp;
    public ListView<Notes> resutOfSearch;
    public ComboBox<Subjects> chooseSubject;
    public ComboBox<Notes> chooseTopic;

    @FXML
    public void initialize () {
        if (main2Controller.getInstance().ucenikBtn.isArmed()) {
            Type type = new Type(1,"srednja");
            NotesDAO a = NotesDAO.getInstance();
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForSchool());
            resutOfSearch.getItems().setAll(a.returnAllNotes());
        }
    }

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
        if (!myStage.isShowing()) {
            if (newNoteController.getInstance().getNotes()!=null) {
                resutOfSearch.getItems().add(newNoteController.getInstance().getNotes());
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) {
    }
}
