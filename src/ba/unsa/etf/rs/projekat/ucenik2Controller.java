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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
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
    public GridPane pane;
    public Button buttonSearch;
    public ListView<Notes> mostWanted;
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
    Type type = null;
    @FXML
    public void initialize () {
        buttonHelp.getStyleClass().add("colorOfBackgroundofButton");
        chooseTopic.getStyleClass().add("colorOfBackgroundofButton");
        chooseSubject.getStyleClass().add("colorOfBackgroundofButton");
        searchNote.getStyleClass().add("colorOfBackgroundofButton");
        menuItem.getStyleClass().add("colorOfBackgroundofButton");
        buttonNote.getStyleClass().add("colorOfBackgroundofButton");
        buttonSearch.getStyleClass().add("colorOfBackgroundofButton");


        NotesDAO a = NotesDAO.getInstance();
        if (main2Controller.getInstance().ucenikBtn.isArmed()) {
             type = new Type(1,"srednja");
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForSchool());
            resultOfSearch.getItems().setAll(a.allNotesForSchool());
            List<References> lista = a.returnAllReferences();
            Collections.sort(lista);
            mostWanted.setItems(sortByRate(lista));

        }
        else if (main2Controller.getInstance().studentBtn.isArmed()) {
            type = new Type(2,"fakultet");
            chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
            chooseTopic.setItems(a.allNotesForCollege());
            resultOfSearch.getItems().setAll(a.allNotesForCollege());
            List<References> lista = a.returnAllReferences2();
            Collections.sort(lista);
            mostWanted.setItems(sortByRate(lista));
        }
        else if (profileController.getInstance().goBackButton.isArmed()) {
            if (profileController.getInstance().getUsers().getStatus().getId() == 1) {
                type = new Type(1,"srednja");
                chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
                chooseTopic.setItems(a.allNotesForSchool());
                resultOfSearch.getItems().setAll(a.allNotesForSchool());
            }
            else if (profileController.getInstance().getUsers().getStatus().getId() == 2) {
                type = new Type(2,"fakultet");
                chooseSubject.setItems(a.returnSubjectsWithSpecType(type));
                chooseTopic.setItems(a.allNotesForCollege());
                resultOfSearch.getItems().setAll(a.allNotesForCollege());
            }

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
                if (newNoteController.getInstance().getNotes().getSort()!=0 &&
                newNoteController.getInstance().getNotes().getSubjects().getType().getId() == type.getId() ) {
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
    public ObservableList<Notes> sortByRate (List<References> lista) {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        int [] fr = new int[lista.size()];
        List<References> rez = new ArrayList<>();
        for (int i = 0;i < lista.size();i++) {
            int count = 1;
            int sum = lista.get(i).getRate();
            for (int j = i+1;j<lista.size();j++) {
                if (lista.get(i).getNotes().getId() == lista.get(j).getNotes().getId()) {
                    count++;
                    sum = sum + lista.get(i).getRate();
                    fr[i] = -1;

                }
            }
            if (fr[i] != -1) {
                fr[i] = sum/count;
                rez.add(lista.get(i));
            }
        }

        ObservableList<Notes> alist = FXCollections.observableArrayList();
        for (References item: rez) {
            alist.add(item.getNotes());
        }
        Collections.reverse(alist);
        return  alist;
    }

    public void helpAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help2.fxml"));
        Parent root = (Parent) loader.load();
        Stage myStage = new Stage();
        myStage.setTitle("Forma");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.setResizable(false);
        myStage.show();
    }

    public void goBackAction(ActionEvent actionEvent) throws IOException {
        Parent homePage  =  FXMLLoader.load(getClass().getResource("/fxml/main2.fxml"));
        Scene scene = new Scene(homePage);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        //stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
