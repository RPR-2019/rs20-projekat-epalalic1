package ba.unsa.etf.rs.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class note2Controller {
    public TextArea textFld;
    public TextField authorFld;
    public TextField rateFld;
    public ListView<References> listOfComments;
    private Notes notes;

    @FXML
    public void initialize () {
        NotesDAO dao = NotesDAO.getInstance();
        notes = UcenikController.getInstance().notes;
        textFld.setText(notes.getText());
        listOfComments.getItems().setAll(dao.returnAllReferences(notes.getId()));
        int velicina = dao.returnAllReferences(notes.getId()).size();
        int suma = 0;
        if (velicina!=0) {
            for (int i = 0; i < dao.returnAllReferences(notes.getId()).size(); i++) {
                suma = suma + dao.returnAllReferences(notes.getId()).get(i).getRate();
            }
            rateFld.setText(String.valueOf(suma/velicina));
        }
        authorFld.setText(notes.getUsers().getUsername());
    }
}
