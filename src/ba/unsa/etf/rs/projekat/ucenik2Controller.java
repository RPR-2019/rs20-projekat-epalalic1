package ba.unsa.etf.rs.projekat;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public MenuItem logOutBtn;
    public MenuButton menuItem;
    private boolean subject = false, topic = false,tekstFld = false;
    private int brojac = 0;
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
        NotesDAO a = NotesDAO.getInstance();
        if (main2Controller.getInstance().ucenikBtn.isArmed()) {
            Type type = new Type(1,"srednja");
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForSchool());
            resultOfSearch.getItems().setAll(a.allNotesForSchool());

        }
        else if (main2Controller.getInstance().studentBtn.isArmed()) {
            Type type = new Type(2,"fakultet");
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForCollege());
            resultOfSearch.getItems().setAll(a.allNotesForCollege());
        }
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getSubjects().getName()
        ));
        authorColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getUsers().getUsername()
        ));

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

    public void logOutAction(ActionEvent actionEvent) throws IOException {
        brojac++;
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/srednjoskolac.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) buttonHelp.getScene().getWindow();
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
                if (newNoteController.getInstance().getNotes().getSort()!=0) {
                    resultOfSearch.getItems().add(newNoteController.getInstance().getNotes());
                    chooseTopic.getItems().add(newNoteController.getInstance().getNotes());
                }
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) {
        NotesDAO dao = NotesDAO.getInstance();
        if (subject && !topic && searchNote.getText().isEmpty()) {
            resultOfSearch.setItems(dao.returnNotesBySubject(chooseSubject.getValue().getId()));
        }
        else if (!subject && topic && searchNote.getText().isEmpty()) {
            resultOfSearch.setItems(dao.returnNotesByTopic(chooseTopic.getValue().getName()));
        }
        else if (subject && topic && searchNote.getText().isEmpty()) {
           resultOfSearch.setItems(dao.returnNotesBySubjectAndNote(chooseSubject.getValue().getId(), chooseTopic.getValue().getName()));
        }
        else if (!subject && !topic && !searchNote.getText().isEmpty()) {
            ObservableList<Notes> a = FXCollections.observableArrayList();
            a.addAll(dao.returnNotesByTopic(searchNote.getText()));
            Subjects subjects = dao.returnSubjectByName(searchNote.getText());
            if (subjects!=null) {
                a.addAll(dao.returnNotesBySubject(subjects.getId()));
            }

            resultOfSearch.setItems(a);

        }

    }

    public int getBrojac() {
        return brojac;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    public void seeProfile(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/profile.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) buttonHelp.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
