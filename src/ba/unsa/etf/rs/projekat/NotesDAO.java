package ba.unsa.etf.rs.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NotesDAO {
    private static NotesDAO instance;
    private Connection conn;

    private PreparedStatement upit,addUser,maxIdUser,checkIfUserExists, returnStatus,returnNameOfStatus,returnAllType,
    returnSubjectsWithSpecType,addNote,maxIdOfNote,allNotesForSchool,returnSubject,returnUser,returnType,returnStatus1,
    retrunAllNotes, searchNoteBySubject,searchNoteByTopic,searchNoteBySubjectAndTopic, addReference, maxIdOfReference,
    returnAllReferencesForSpecNote, returnNoteById, returnNotesByUser,deleteNote, editNote,deleteReferences,
    returnSubjectByName,allNotesForCollege,editUser, returnAllReferences,returnAllReferences2;

    public static NotesDAO getInstance() {
        if (instance == null) instance = new NotesDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private NotesDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            upit = conn.prepareStatement("SELECT * FROM users");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                upit = conn.prepareStatement("SELECT * FROM users");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            addUser = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?)");
            maxIdUser = conn.prepareStatement("SELECT MAX(id)+1 FROM users");
            checkIfUserExists = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            returnStatus = conn.prepareStatement("SELECT * FROM status WHERE name = ?");
            returnNameOfStatus = conn.prepareStatement("SELECT * FROM status WHERE id = ?");
            returnAllType = conn.prepareStatement("SELECT * FROM type");
            returnSubjectsWithSpecType = conn.prepareStatement("SELECT * FROM subjects where type = ?");
            addNote = conn.prepareStatement("INSERT INTO notes VALUES(?,?,?,?,?,?)");
            maxIdOfNote = conn.prepareStatement("SELECT MAX(id)+1 FROM notes");
            allNotesForSchool = conn.prepareStatement("SELECT * FROM (notes INNER JOIN subjects ON " +
                    "notes.subject = subjects.id ) INNER JOIN type ON subjects.type = type.id WHERE type.id = 1 AND notes.sort = 1");
            returnSubject = conn.prepareStatement("SELECT * FROM subjects WHERE id = ?");
            returnUser = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            returnType = conn.prepareStatement("SELECT * FROM type WHERE id = ?");
            returnStatus1 = conn.prepareStatement("SELECT * FROM status WHERE id = ?");
            retrunAllNotes  = conn.prepareStatement("SELECT * FROM notes");
            searchNoteBySubject = conn.prepareStatement("SELECT * FROM notes INNER JOIN subjects ON " +
                    "notes.subject = subjects.id WHERE subjects.id = ? AND notes.sort = 1");
            searchNoteByTopic = conn.prepareStatement("SELECT * FROM notes WHERE name = ? AND sort = 1");
            searchNoteBySubjectAndTopic = conn.prepareStatement("SELECT * FROM notes INNER JOIN subjects ON " +
                    "notes.subject = subjects.id WHERE subjects.id = ? AND notes.name = ? AND notes.sort = 1");
            addReference = conn.prepareStatement("INSERT INTO referencess VALUES(?,?,?,?)");
            maxIdOfReference = conn.prepareStatement("SELECT MAX(id)+1 FROM referencess");
            returnAllReferencesForSpecNote = conn.prepareStatement("SELECT * FROM referencess WHERE note = ?");
            returnNoteById = conn.prepareStatement("SELECT * FROM notes WHERE id = ?");
            returnNotesByUser = conn.prepareStatement("SELECT * FROM notes WHERE user = ?");
            deleteNote = conn.prepareStatement("DELETE FROM notes WHERE id = ?");
            editNote = conn.prepareStatement("UPDATE notes SET text = ?, name = ?, subject = ?, user = ?, sort = ? " +
                    "WHERE id = ? ");
            deleteReferences = conn.prepareStatement("DELETE FROM referencess WHERE note = ?");
            returnSubjectByName = conn.prepareStatement("SELECT * FROM subjects WHERE name = ?");
            allNotesForCollege = conn.prepareStatement("SELECT * FROM (notes INNER JOIN subjects ON " +
                    "notes.subject = subjects.id ) INNER JOIN type ON subjects.type = type.id WHERE type.id = 2 AND notes.sort = 1");
            editUser = conn.prepareStatement("UPDATE users SET name = ?, surname = ?, username = ?, email = ?, " +
                    "password = ?, status = ? WHERE id = ?");
            returnAllReferences = conn.prepareStatement("SELECT * FROM referencess INNER JOIN notes ON " +
                    "referencess.note = notes.id INNER JOIN subjects ON notes.subject = subjects.id WHERE type = 1");
            returnAllReferences2 = conn.prepareStatement("SELECT * FROM referencess INNER JOIN notes ON " +
                    "referencess.note = notes.id INNER JOIN subjects ON notes.subject = subjects.id WHERE type = 2");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int  addUser (Users user) {
        ResultSet rs = null;
        int id = 1;
        try {
            rs = maxIdUser.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                if (id == 0) {
                    id = 1;
                }
            }
            addUser.setInt(1,id);
            addUser.setString(2,user.getName());
            addUser.setString(3,user.getSurname());
            addUser.setString(4,user.getUsername());
            addUser.setString(5,user.getEmail());
            addUser.setString(6,user.getPassword());
            addUser.setInt(7,user.getStatus().getId());
            addUser.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public  boolean checkIfUserExists (String username, String password) {
        try {
            checkIfUserExists.setString(1,username);
            checkIfUserExists.setString(2,password);
            ResultSet rs = checkIfUserExists.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public Status returnStatus (String name) {
        Status s = null;
        try {
            returnStatus.setString(1,name);
            ResultSet rs = returnStatus.executeQuery();
            int  id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            if (id != 0) {
                s = new Status(id,name);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    public String returnNameOfStatus (int id) {
        String a = null;
        try {
            returnNameOfStatus.setInt(1,id);
            ResultSet rs = returnNameOfStatus.executeQuery();
            while (rs.next()) {
                a = rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
    public Users returnUser (String username, String password) {
        Users user = null;
        try {
            checkIfUserExists.setString(1,username);
            checkIfUserExists.setString(2,password);
            ResultSet rs = checkIfUserExists.executeQuery();
            int id = 0,status = 0;
            String name = null,surname =null,email = null;
            Status status1 = null;
            if (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                surname = rs.getString(3);
                email = rs.getString(5);
                status = rs.getInt(7);
            }
            status1 = new Status(status,returnNameOfStatus(status));
            user = new Users(id,name,surname,username,email,password,status1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public ObservableList<Type> returnAllType () {
        ObservableList<Type> list = FXCollections.observableArrayList();
        try {
            ResultSet rs = returnAllType.executeQuery();
            while (rs.next()) {
                Type a = new Type(rs.getInt(1),rs.getString(2));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  list;
    }
    public ObservableList<Subjects> returnSubjectsWithSpecType (Type type) {
        ObservableList<Subjects> list = FXCollections.observableArrayList();
        try {
            returnSubjectsWithSpecType.setInt(1, type.getId());
            ResultSet rs = returnSubjectsWithSpecType.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Subjects subjects = new Subjects(id,name,type);
                list.add(subjects);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void addNote (Notes notes) {
        ResultSet rs = null;
        try {
            rs = maxIdOfNote.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
                if (id == 0) {
                    id = 1;
                }
            }
            addNote.setInt(1,id);
            addNote.setString(2, notes.getText());
            addNote.setString(3,notes.getName());
            addNote.setInt(4,notes.getSubjects().getId());
            addNote.setInt(5,notes.getUsers().getId());
            addNote.setInt(6,notes.getSort());
            addNote.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  Subjects returnSubjects (int id) {
        Subjects subjects = null;
        try {
            returnSubject.setInt(1,id);
            ResultSet rs = returnSubject.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                int type = rs.getInt(3);
                returnType.setInt(1,type);
                ResultSet rs1 = returnType.executeQuery();
                Type type1 = null;
                while (rs1.next()) {
                    String name1 = rs1.getString(2);
                    type1 = new Type(type,name1);
                }
                subjects = new Subjects(id,name,type1);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }
    public Users returnUser (int id) {
        Users users = null;
        try {
            returnUser.setInt(1,id);
            ResultSet rs = returnUser.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String username = rs.getString(4);
                String email = rs.getString(5);
                String password = rs.getString(6);
                int status = rs.getInt(7);
                returnStatus1.setInt(1,status);
                ResultSet rs1 = returnStatus1.executeQuery();
                Status status1 = null;
                while (rs1.next()) {
                    String name1 = rs1.getString(2);
                    status1 = new Status(status,name1);
                }
                users = new Users(id,name,surname,username,email,password,status1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }


    public ObservableList<Notes>  allNotesForSchool () {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            ResultSet rs = allNotesForSchool.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                String name = rs.getString(3);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id,text,name,subjects,users,sort);
                a.add(notes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
    public ObservableList<Notes> returnAllNotes () {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            ResultSet rs = retrunAllNotes.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                String name = rs.getString(3);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id,text,name,subjects,users,sort);
                a.add(notes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
    public ObservableList<Notes> returnNotesBySubject (int id) {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            searchNoteBySubject.setInt(1,id);
            ResultSet rs = searchNoteBySubject.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt(1);
                String text = rs.getString(2);
                String name = rs.getString(3);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id1,text,name,subjects,users,sort);
                a.add(notes);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    public ObservableList<Notes> returnNotesByTopic (String name) {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            searchNoteByTopic.setString(1,name);
            ResultSet rs = searchNoteByTopic.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt(1);
                String text = rs.getString(2);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id1,text,name,subjects,users,sort);
                a.add(notes);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    public ObservableList<Notes> returnNotesBySubjectAndNote (int id, String name) {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            searchNoteBySubjectAndTopic.setInt(1,id);
            searchNoteBySubjectAndTopic.setString(2,name);
            ResultSet rs = searchNoteBySubjectAndTopic.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt(1);
                String text = rs.getString(2);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id1,text,name,subjects,users,sort);
                a.add(notes);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void addReference(References references) {
        ResultSet rs = null;
        try {
            rs = maxIdOfReference.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
                if (id == 0) {
                    id = 1;
                }
            }
            addReference.setInt(1,id);
            addReference.setString(2,references.getComment());
            addReference.setInt(3,references.getRate());
            addReference.setInt(4,references.getNotes().getId());
            addReference.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Notes returnNoteById (int id) {
        Notes notes = null;
        try {
            returnNoteById.setInt(1,id);
            ResultSet rs = returnNoteById.executeQuery();
            while (rs.next()) {
                String text = rs.getString(2);
                String name = rs.getString(3);
                int idS = rs.getInt(4);
                int idU = rs.getInt(5);
                int sort = rs.getInt(6);
                Subjects subjects = returnSubjects(idS);
                Users users = returnUser(idU);
                notes = new Notes(id,text,name,subjects,users,sort);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
    public ObservableList<References> returnAllReferences (int id) {
        ObservableList<References> a = FXCollections.observableArrayList();
        try {
            returnAllReferencesForSpecNote.setInt(1,id);
            ResultSet rs = returnAllReferencesForSpecNote.executeQuery();
            while (rs.next()) {
                int idR = rs.getInt(1);
                String text = rs.getString(2);
                int rate = rs.getInt(3);
                int idN = rs.getInt(4);
                Notes notes = returnNoteById(idN);
                References references = new References(idR,text,rate,notes);
                a.add(references);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
    public ObservableList<Notes> returnAllNotesByUser (int id) {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            returnNotesByUser.setInt(1,id);
            ResultSet rs = returnNotesByUser.executeQuery();
            while (rs.next()) {
                int id1 = rs.getInt(1);
                String text = rs.getString(2);
                String name = rs.getString(3);
                int sub = rs.getInt(4);
                int sort = rs.getInt(6);
                Subjects subjects = returnSubjects(sub);
                Users users = returnUser(id);
                Notes notes = new Notes(id1,text,name,subjects,users,sort);
                a.add(notes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
    public void deleteNote (int id) {
        try {
            deleteNote.setInt(1,id);
            deleteNote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void editNote (Notes notes) {
        try {
            editNote.setString(1,notes.getText());
            editNote.setString(2,notes.getName());
            editNote.setInt(3,notes.getSubjects().getId());
            editNote.setInt(4,notes.getUsers().getId());
            editNote.setInt(5,notes.getSort());
            editNote.setInt(6,notes.getId());
            editNote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteReferenes (int id) {
        try {
            deleteReferences.setInt(1,id);
            deleteReferences.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Type returnType (int id) {
        Type type = null;
        try {
            returnType.setInt(1,id);
            ResultSet rs = returnType.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                type = new Type(id,name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }
    public Subjects returnSubjectByName (String name) {
        Subjects subjects = null;
        try {
            returnSubjectByName.setString(1,name);
            ResultSet rs = returnSubjectByName.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int type = rs.getInt(3);
                Type type1 = returnType(type);
                subjects = new Subjects(id,name,type1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }
    public ObservableList<Notes>  allNotesForCollege () {
        ObservableList<Notes> a = FXCollections.observableArrayList();
        try {
            ResultSet rs = allNotesForCollege.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                String name = rs.getString(3);
                int subject = rs.getInt(4);
                int user = rs.getInt(5);
                int sort = rs.getInt(6);
                Users users = returnUser(user);
                Subjects subjects = returnSubjects(subject);
                Notes notes = new Notes(id,text,name,subjects,users,sort);
                a.add(notes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }

    public void editUser(Users users) {
        try {
            editUser.setString(1,users.getName());
            editUser.setString(2,users.getSurname());
            editUser.setString(3,users.getUsername());
            editUser.setString(4,users.getEmail());
            editUser.setString(5,users.getPassword());
            editUser.setInt(6,users.getStatus().getId());
            editUser.setInt(7,users.getId());
            editUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<References> returnAllReferences () {
        ArrayList<References> a = new ArrayList<>();
        try {
            ResultSet rs = returnAllReferences.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                int rate = rs.getInt(3);
                int note = rs.getInt(4);
                Notes notes = returnNoteById(note);
                References references = new References(id,text,rate,notes);
                a.add(references);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
    public ArrayList<References> returnAllReferences2 () {
        ArrayList<References> a = new ArrayList<>();
        try {
            ResultSet rs = returnAllReferences2.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String text = rs.getString(2);
                int rate = rs.getInt(3);
                int note = rs.getInt(4);
                Notes notes = returnNoteById(note);
                References references = new References(id,text,rate,notes);
                a.add(references);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }
}
