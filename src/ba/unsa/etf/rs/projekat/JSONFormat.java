package ba.unsa.etf.rs.projekat;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JSONFormat {
    private Notes notes;

    public JSONFormat () {

    }



    public void writeNotes (File file) {
        JSONObject note = new JSONObject();
        note.put("Naziv", notes.getName());
        note.put("Autor", notes.getUsers().getUsername());
        note.put("Predmet", notes.getSubjects().getName());
        note.put("Tekst", notes.getText());
        try {
            Files.writeString(file.toPath(), note.toString());
        } catch (IOException e) {
            return;
        }


    }


    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }
}
