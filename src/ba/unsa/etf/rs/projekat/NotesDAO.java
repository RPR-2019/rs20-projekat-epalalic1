package ba.unsa.etf.rs.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class NotesDAO {
    private static NotesDAO instance;
    private Connection conn;

    private PreparedStatement upit,addUser,maxIdUser,checkIfUserExists, returnStatus;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addUser (Users user) {
        ResultSet rs = null;
        try {
            rs = maxIdUser.executeQuery();
            int id = 1;
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
}
