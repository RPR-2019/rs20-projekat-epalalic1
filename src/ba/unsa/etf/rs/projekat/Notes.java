package ba.unsa.etf.rs.projekat;

public class Notes {
    private int id;
    private String name;
    private Subjects subjects;
    private Users users;


    public  Notes (int id, String name, Subjects subjects, Users users) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

