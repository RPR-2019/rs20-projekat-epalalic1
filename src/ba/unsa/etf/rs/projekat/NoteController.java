package ba.unsa.etf.rs.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NoteController {
    public TextArea textFld;
    public TextField authorFld;
    public TextField rateFld;
    public Spinner spinnerFld;
    public TextField commentFld;
    Notes notes = null;

    @FXML
    public void initialize () {
        notes = ucenik2Controller.getInstance().notes;
        textFld.setText(notes.getText());
        authorFld.setText(notes.getUsers().getUsername());

    }


}
