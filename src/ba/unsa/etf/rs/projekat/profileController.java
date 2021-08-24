package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class profileController {
    public Label nameLabel;
    public Label surnameLabel;
    public Label usernameLabel;
    public ListView<Notes> listOfNotes;
    Users users = null;
    Notes notes = null;

    @FXML
    public void initialize () {
        NotesDAO dao1 = NotesDAO.getInstance();
        main2Controller dao = main2Controller.getInstance();
        users = dao.getUser();
        nameLabel.setText(users.getName());
        surnameLabel.setText(users.getSurname());
        usernameLabel.setText(users.getUsername());
        listOfNotes.setItems(dao1.returnAllNotesByUser(users.getId()));
        listOfNotes.getSelectionModel().selectedItemProperty().addListener((obs,staro,novo) -> {
            if (novo!=null) {
                notes = novo;
            }
        });

    }

    public void editNote(ActionEvent actionEvent) {

    }

    public void deleteNote(ActionEvent actionEvent) {
        NotesDAO dao = NotesDAO.getInstance();
        dao.deleteNote(notes.getId());
        dao.deleteReferenes(notes.getId());
        listOfNotes.getItems().remove(notes);
    }
}
