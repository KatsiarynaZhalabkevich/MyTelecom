package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Note implements Serializable {
    private final static long serialVersionUID = 3L;
    private long id;
    private LocalDate connectionDate;


    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(LocalDate connectionDate) {
        this.connectionDate = connectionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return getId() == note.getId() &&
                Objects.equals(getConnectionDate(), note.getConnectionDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getConnectionDate());
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", connectionDate=" + connectionDate +'}';
    }
}
