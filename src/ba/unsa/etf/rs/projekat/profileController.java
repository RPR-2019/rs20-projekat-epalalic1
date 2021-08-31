package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.AnnotatedWildcardType;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class profileController {
    public Label nameLabel;
    public Label surnameLabel;
    public Label usernameLabel;
    public ListView<Notes> listOfNotes;
    Users users = null;
    Notes notes = null;

    public static profileController instance;
    public profileController() {
        instance = this;
    }
    public static profileController getInstance() {
        return instance;
    }

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

    public void editNote(ActionEvent actionEvent) throws IOException {
        editController a = new editController(notes);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editNote.fxml"));
        loader.setController(a);
        Parent root = loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        if (!myStage.isShowing()) {
            if (a.getNotes()!=null) {
                listOfNotes.getItems().removeAll();
                listOfNotes.getItems().setAll(NotesDAO.getInstance().returnAllNotesByUser(notes.getUsers().getId()));
            }
        }
    }

    public void deleteNote(ActionEvent actionEvent) {
        NotesDAO dao = NotesDAO.getInstance();
        dao.deleteNote(notes.getId());
        dao.deleteReferenes(notes.getId());
        listOfNotes.getItems().remove(notes);
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void editProfile(ActionEvent actionEvent) throws IOException {
        NotesDAO dao = NotesDAO.getInstance();
        FormaController a = new FormaController();
        a.setUser1(users);
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/forma.fxml"));
        loader.setController(a);
        Parent root = loader.load();
        myStage.setTitle("Novi prozor");
        myStage.setScene(new Scene(root, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        if (!myStage.isShowing()) {
            Users users1 = dao.returnUser(a.getUser1().getId());
            users = users1;
            usernameLabel.setText(users1.getUsername());
            nameLabel.setText(users1.getName());
            surnameLabel.setText(users1.getSurname());
        }
    }

    public void goBackAction(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/logedInH.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
