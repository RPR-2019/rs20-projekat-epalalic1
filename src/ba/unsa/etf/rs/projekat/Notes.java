package ba.unsa.etf.rs.projekat;

import java.util.Objects;

public class Notes {
    private int id,sort;
    private String name,text;
    private Subjects subjects;
    private Users users;
    private References references;


    public  Notes (int id, String text, String name, Subjects subjects, Users users,int sort) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.subjects = subjects;
        this.users = users;
        this.sort = sort;
    }
    public  Notes ( String text, String name, Subjects subjects, Users users,int sort) {
        this.text = text;
        this.name = name;
        this.subjects = subjects;
        this.users = users;
        this.sort = sort;
    }
    public  Notes ( String text, String name, Subjects subjects, Users users,int sort,References references) {
        this.text = text;
        this.name = name;
        this.subjects = subjects;
        this.users = users;
        this.sort = sort;
        this.references = references;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString () {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return id == notes.id;
    }


}

