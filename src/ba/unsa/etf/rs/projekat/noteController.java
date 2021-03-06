package ba.unsa.etf.rs.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class noteController {
    public TextArea textFld;
    public TextField authorFld;
    public TextField rateFld;
    public Spinner<Integer> spinnerFld;
    public TextField commentFld;
    public ListView<References> listOfComments;
    public GridPane pane;
    Notes notes = null;

    @FXML
    public void initialize () {
       SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,5,0);
       spinnerFld.setValueFactory(valueFactory);
        NotesDAO dao = NotesDAO.getInstance();
        if (user2Controller.getInstance().notes !=null ){
            notes = user2Controller.getInstance().notes;
        }
        else {
            notes = userController.getInstance().notes;
        }

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
        if (spinnerFld.getValue() == 0 || commentFld.getText().isEmpty() ||
        notes.getUsers().getId()==main2Controller.getInstance().getUser().getId()) {
            try {
                throw new canNotLeaveCommentException("Ne mozete ostaviti komentar");
            } catch (canNotLeaveCommentException e) {
               commentFld.getStyleClass().removeAll();
               commentFld.getStyleClass().add("poljeNijeIspravno");
            }
        }
        else {
            references = new References(commentFld.getText(),spinnerFld.getValue(),notes);
            listOfComments.getItems().add(references);
            dao.addReference(references);
        }
    }

    public void recordNote(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Zapi??i JSON datoteku");
            Stage stage = (Stage)rateFld.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);
            if (file == null)
                return;

            JSONFormat xml = new JSONFormat();
            xml.setNotes(notes);
            xml.writeNotes(file);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong file format");
            alert.setContentText("An error occured during file save.");
            alert.showAndWait();
        }
    }
}
