package ba.unsa.etf.rs.projekat;

public class References implements Comparable{
    private int id,rate;
    private String comment;
    private Notes notes;


    public References (int id, String comment, int rate, Notes notes) {
        this.id = id;
        this.comment = comment;
        this.rate = rate;
        this.notes = notes;
    }

    public References (String comment, int rate, Notes notes) {
        this.comment = comment;
        this.rate = rate;
        this.notes = notes;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    @Override
    public String toString () {
        return comment;
    }


    @Override
    public int compareTo(Object o) {
      return Integer.compare(rate, ((References) o).getRate());
    }
}
