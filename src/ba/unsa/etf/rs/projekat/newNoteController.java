package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class newNoteController {
    public void closeNewNote(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
