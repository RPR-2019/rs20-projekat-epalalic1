package ba.unsa.etf.rs.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class previewController {
    public TextArea previewFld;
    @FXML
    public void initialize () {
        previewFld.setText(newNoteController.getInstance().textFld.getText());
    }
}
