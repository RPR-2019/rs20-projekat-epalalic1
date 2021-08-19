package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NoteController {
    public TextArea textFld;
    public TextField authorFld;
    public TextField rateFld;
    public Spinner<Integer> spinnerFld;
    public TextField commentFld;
    public ListView<References> listOfComments;
    Notes notes = null;

    @FXML
    public void initialize () {
        NotesDAO dao = NotesDAO.getInstance();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5,0);
        spinnerFld.setValueFactory(valueFactory);
        notes = ucenik2Controller.getInstance().notes;
        textFld.setText(notes.getText());
        authorFld.setText(notes.getUsers().getUsername());
        int velicina = dao.returnAllReferences(notes.getId()).size();
        int suma = 0;
        if (velicina!=0) {
            for (int i = 0; i < dao.returnAllReferences(notes.getId()).size(); i++) {
                suma = suma + dao.returnAllReferences(notes.getId()).get(i).getRate();
            }
            rateFld.setText(String.valueOf(suma/velicina));
        }

        listOfComments.setItems(dao.returnAllReferences(notes.getId()));

    }


    public void addRateAndComment(ActionEvent actionEvent) {
        References references = null;
        NotesDAO dao = NotesDAO.getInstance();
        if (spinnerFld.getValue() == 0 || commentFld.getText().isEmpty()) {
            System.out.println("Ne mozete dodati komentar");//ovdje baciti izuzetak
        }
        else {
            references = new References(commentFld.getText(),spinnerFld.getValue(),notes);
            listOfComments.getItems().add(references);
            dao.addReference(references);
        }
        /*References references = new References(notes.getId(),commentFld.getText(),spinnerFld.getValue(),notes);
        listOfComments.getItems().add(references);*/
    }
}
