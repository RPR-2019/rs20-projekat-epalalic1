package ba.unsa.etf.rs.projekat;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ucenik2Controller {
    public Button buttonNote;
    public TextField searchNote;
    public Button buttonHelp;
    public ComboBox<Subjects> chooseSubject;
    public ComboBox<Notes> chooseTopic;
    public TableView<Notes> resultOfSearch;
    public TableColumn<Notes,String> topicColumn;
    public TableColumn<Notes , String> subjectColumn;
    public TableColumn<Notes, String> authorColumn;
    private boolean subject = false, topic = false,tekstFld = false;

    Notes notes = null;
    public static ucenik2Controller instance;
    public ucenik2Controller () {
        instance = this;
    }
    public static ucenik2Controller getInstance() {
        return instance;
    }

    @FXML
    public void initialize () {
        if (main2Controller.getInstance().ucenikBtn.isArmed()) {
            Type type = new Type(1,"srednja");
            NotesDAO a = NotesDAO.getInstance();
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForSchool());
            topicColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            subjectColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                    () -> cellData.getValue().getSubjects().getName()
            ));
            authorColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                    () -> cellData.getValue().getUsers().getUsername()
            ));
            resultOfSearch.getItems().setAll(a.allNotesForSchool());
            chooseSubject.getSelectionModel().selectedItemProperty().addListener((obs,staro,novo) -> {
                if (chooseSubject.getValue()!=null) {
                    subject = true;
                }
            });
            chooseTopic.getSelectionModel().selectedItemProperty().addListener((obs,staro,novo) -> {
                if (chooseTopic.getValue()!=null) {
                    topic = true;
                }
            });
            resultOfSearch.getSelectionModel().selectedItemProperty().addListener((obs,staro,novo) -> {
                if (novo!=null) {
                    notes = novo;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/note.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage myStage = new Stage();
                    myStage.setTitle("Forma");
                    myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    myStage.show();
                }
            });
        }
    }

    public void logOutAction(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) buttonNote.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void addNoteAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newNote.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.showAndWait();
        if (!myStage.isShowing()) {
            if (newNoteController.getInstance().getNotes()!=null) {
                resultOfSearch.getItems().add(newNoteController.getInstance().getNotes());
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) {
        NotesDAO dao = NotesDAO.getInstance();
        if (subject && !topic) {
            resultOfSearch.setItems(dao.returnNotesBySubject(chooseSubject.getValue().getId()));
        }
        else if (!subject && topic) {
            resultOfSearch.setItems(dao.returnNotesByTopic(chooseTopic.getValue().getName()));
        }
        else  {
           resultOfSearch.setItems(dao.returnNotesBySubjectAndNote(chooseSubject.getValue().getId(), chooseTopic.getValue().getName()));
        }

    }
}
