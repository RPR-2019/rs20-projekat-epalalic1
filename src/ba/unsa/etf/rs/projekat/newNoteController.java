package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class newNoteController {
    public TextArea textFld;
    public ComboBox<Type> chooseStatus;
    public ComboBox<Subjects> chooseSubject;
    public TextField nameOfNote;
    public CheckBox sortOfNote;
    private boolean nameofNoteCheck = false;
    private int selected = 0;


    @FXML
    public void initialize () {
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
            Notes notes = new Notes(textFld.getText(),nameOfNote.getText(),chooseSubject.getValue(),
                    main2Controller.getInstance().getUser(), selected);
            NotesDAO.getInstance().addNote(notes);
            System.out.println("Uspjesno ste dodali biljesku");
        }
    }
}
