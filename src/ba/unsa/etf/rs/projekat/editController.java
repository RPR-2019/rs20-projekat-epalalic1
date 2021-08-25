package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class editController {
    public ComboBox<Type> chooseStatus;
    public ComboBox<Subjects> chooseSubject;
    public TextField nameOfNote;
    public CheckBox sortOfNote;
    public TextArea textFld;
    private Notes notes;
    public editController(Notes  notes) {
        this.notes = notes;
    }
    boolean nameofNoteCheck = false;
    int selected = 0;
    @FXML
    public void initialize () {
        if (notes!=null) {
            textFld.setText(notes.getText());
            nameOfNote.setText(notes.getName());
            chooseStatus.setValue(notes.getSubjects().getType());
            chooseSubject.setValue(notes.getSubjects());
            if (notes.getSort() == 1) {
                sortOfNote.fire();
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

    }

    public void addNoteAction(ActionEvent actionEvent) {
       notes = new Notes(notes.getId(),textFld.getText(),nameOfNote.getText(),chooseSubject.getValue(),
               profileController.getInstance().getUsers(),selected);
       NotesDAO dao = NotesDAO.getInstance();
       dao.editNote(notes);


    }

    public void previewAction(ActionEvent actionEvent) {
        System.out.println("Okej");
    }

    public void closeNewNote(ActionEvent actionEvent) {
        System.out.println("Okej");
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
