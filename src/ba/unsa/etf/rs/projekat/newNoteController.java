package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class newNoteController {
    public TextArea textFld;
    public ComboBox<Type> chooseStatus;
    public ComboBox<Subjects> chooseSubject;
    public TextField nameOfNote;
    public CheckBox sortOfNote;
    private boolean nameofNoteCheck = false;
    private int selected = 0;
    public static newNoteController instance;
    public newNoteController () {
        instance = this;
    }
    public static newNoteController getInstance() {
        return instance;
    }

    public Notes notes = null;
    public Notes notes1 = null;
    public newNoteController (Notes notes2) {
        notes1 = notes2;
    }




    @FXML
    public void initialize () {
        if (notes1!=null) {
            nameOfNote.setText(notes1.getName());
            textFld.setText(notes1.getText());
        }
        NotesDAO a = NotesDAO.getInstance();
        nameOfNote.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty()) {
                nameOfNote.getStyleClass().removeAll("poljeIspravno");
                nameOfNote.getStyleClass().add("poljeNijeIspravno");
                nameofNoteCheck = false;
            }
            else {
                nameOfNote.getStyleClass().removeAll("poljeNijeIspravno");
                nameOfNote.getStyleClass().add("poljeIspravno");
                nameofNoteCheck = true;
            }
        });
        chooseStatus.setItems(a.returnAllType());
        chooseStatus.getSelectionModel().selectedItemProperty().addListener((obs,staro,novo) -> {
           chooseSubject.setItems(a.returnSubjectsWithSpecType(chooseStatus.getValue()));
        });
        sortOfNote.selectedProperty().addListener((obs,staro,novo) -> {
            if (sortOfNote.isSelected()) {
                selected = 1;
            }
            else {
                selected = 0;
            }
        });

    }


    public void closeNewNote(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void addNoteAction(ActionEvent actionEvent) {
        if (nameofNoteCheck) {
             notes = new Notes(textFld.getText(),nameOfNote.getText(),chooseSubject.getValue(),
                    main2Controller.getInstance().getUser(), selected);
            NotesDAO.getInstance().addNote(notes);
           nameOfNote.getScene().getWindow().hide();
        }
    }

    public void previewAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/previewNote.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Notes getNotes1() {
        return notes1;
    }

    public void setNotes1(Notes notes1) {
        this.notes1 = notes1;
    }
}
