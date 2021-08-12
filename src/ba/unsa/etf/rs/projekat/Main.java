package ba.unsa.etf.rs.projekat;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        NotesDAO a = NotesDAO.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Biljeske sa predavanja");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }



        public static void main (String [] args) {
            launch(args);
        }

}
