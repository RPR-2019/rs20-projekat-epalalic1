package ba.unsa.etf.rs.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class FormaController {

    public TextField nameField;
    public TextField surnameField;
    public TextField usernameField;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField repeatField;
    public ChoiceBox<String> statusField;


    @FXML
    public void initialize () {
        statusField.getItems().addAll("Student", "Ucenik", "Nijedno");
        nameField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty()) {
                nameField.getStyleClass().removeAll("poljeIspravno");
                nameField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                nameField.getStyleClass().removeAll("poljeNijeIspravno");
                nameField.getStyleClass().add("poljeIspravno");
            }
        });
        surnameField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty()) {
                surnameField.getStyleClass().removeAll("poljeIspravno");
                surnameField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                surnameField.getStyleClass().removeAll("poljeNijeIspravno");
                surnameField.getStyleClass().add("poljeIspravno");
            }
        });
        usernameField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty() || novo.length() < 6) {
                usernameField.getStyleClass().removeAll("poljeIspravno");
                usernameField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                usernameField.getStyleClass().removeAll("poljeNijeIspravno");
                usernameField.getStyleClass().add("poljeIspravno");
            }
        });
        emailField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty() || !isEmailValid(novo)) {
                emailField.getStyleClass().removeAll("poljeIspravno");
                emailField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                emailField.getStyleClass().removeAll("poljeNijeIspravno");
                emailField.getStyleClass().add("poljeIspravno");
            }
        });
        passwordField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty() || !isPasswordValid(novo)) {
                passwordField.getStyleClass().removeAll("poljeIspravno");
                passwordField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                passwordField.getStyleClass().removeAll("poljeNijeIspravno");
                passwordField.getStyleClass().add("poljeIspravno");
            }
        });
        repeatField.textProperty().addListener((obs,staro,novo) -> {
            if (novo.isEmpty()) {
                repeatField.getStyleClass().removeAll("poljeIspravno");
                repeatField.getStyleClass().add("poljeNijeIspravno");
            }
            else {
                repeatField.getStyleClass().removeAll("poljeNijeIspravno");
                repeatField.getStyleClass().add("poljeIspravno");
            }
        });


    }
    private boolean isPasswordValid (String a ){
        if (a.length() < 8) {
            return false;
        }

        int brojac = 0;
        for (int i = 0;i <a.length();i++) {
            if (a.charAt(i) >='A' && a.charAt(i) <='Z') {
                brojac++;
                break;
            }
        }
        for (int i = 0;i <a.length();i++) {
            if (a.charAt(i) >='a' && a.charAt(i) <='z') {
                brojac++;
                break;
            }
        }
        for (int i = 0;i <a.length();i++) {
            if (Character.isDigit(a.charAt(i))) {
                brojac++;
                break;
            }
        }
        if (brojac == 3 ) {
            return true;
        }
        //potrebno je dodati provjeru za specijalni karakter
        return false;
    }

    private boolean isEmailValid (String a) {
        if (!a.contains("@")) {
            return false;
        }
        for (int i = 0; i < a.length();i++) {
            if (a.indexOf("@") == 0 || a.indexOf("@") == a.length()-1) {
                return false;
            }
        }
        return true;
    }



}
