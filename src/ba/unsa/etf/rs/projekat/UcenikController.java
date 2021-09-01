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
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UcenikController {
    public static UcenikController instance;
    public TextField searchNote;
    public ComboBox<Subjects> chooseSubject;
    public ComboBox<Notes> chooseTopic;
    public TableView<Notes> resultOfSearch;
    public TableColumn<Notes, String> topicColumn;
    public TableColumn <Notes, String> subjectColumn;
    public TableColumn <Notes, String> authorColumn;
    public GridPane pane;
    public Button button1;
    public Button button2;
    public Button button3;
    public ListView<Notes> mostWanted;

    public UcenikController () {
        instance = this;
    }
    public static UcenikController  getInstance() {
        return instance;
    }
    Users users = null;

    public Button buttonHelp;
    Notes notes = null;
    private boolean topic =false, subject = false;

    @FXML
    public void initialize () {
        pane.getStyleClass().add("colorOfBackground");
        button1.getStyleClass().add("colorOfBackgroundofButton");
        button2.getStyleClass().add("colorOfBackgroundofButton");
        button3.getStyleClass().add("colorOfBackgroundofButton");
        buttonHelp.getStyleClass().add("colorOfBackgroundofButton");
        chooseTopic.getStyleClass().add("colorOfBackgroundofButton");
        chooseSubject.getStyleClass().add("colorOfBackgroundofButton");
        searchNote.getStyleClass().add("colorOfBackgroundofButton");



        MainController dao = MainController.getInstance();
        ucenik2Controller dao1 = ucenik2Controller.getInstance();
        NotesDAO a = NotesDAO.getInstance();
         if (dao.buttonId.isArmed() ) {
             Type type = new Type(2,"fakultet");
             System.out.println("Usli smo");
             chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
             chooseTopic.setItems(a.allNotesForCollege());
             resultOfSearch.getItems().setAll(a.allNotesForCollege());
             List<References> lista = a.returnAllReferences2();
             Collections.sort(lista);
             ObservableList<Notes> alist = FXCollections.observableArrayList();
             for (References item: lista) {
                 alist.add(item.getNotes());
             }
             Collections.reverse(alist);
             mostWanted.getItems().setAll(alist);
        }
        else if (dao.buttonId2.isArmed() || dao1.getBrojac()!=0) {
             Type type = new Type(1, "srednja");
             resultOfSearch.getItems().setAll(a.allNotesForSchool());
             chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
             chooseTopic.setItems(a.allNotesForSchool());
             List<References> lista = a.returnAllReferences();
             Collections.sort(lista);
             ObservableList<Notes> alist = FXCollections.observableArrayList();
             for (References item: lista) {
                 alist.add(item.getNotes());
             }
             Collections.reverse(alist);
             mostWanted.getItems().setAll(alist);
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/note2.fxml"));
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




    public void signup(ActionEvent actionEvent) throws IOException {
        FormaController a = new FormaController();
        Stage myStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/forma.fxml"));
        loader.setController(a);
        Parent root = loader.load();
        myStage.setTitle("Novi prozor");
        myStage.setScene(new Scene(root, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.showAndWait();
        if (!myStage.isShowing()) {
            if (a.check()!=null) {
                users = a.check();
                Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
                Scene scene = new Scene(homePage);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                //stage.hide();
                stage.setScene(scene);
                stage.show();

            }
        }


    }

    public void logInAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogIn.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
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
            a.addAll(dao.returnNotesBySubject(subjects.getId()));
            resultOfSearch.setItems(a);

        }
    }

}
