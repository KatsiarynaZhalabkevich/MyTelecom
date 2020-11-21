package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Note implements Serializable {
    private final static long serialVersionUID = 3L;
    private long id;
    private LocalDateTime connectionDate;
    private Tariff tariff;
    private User user;

    public Note() {
    }

    public Note(Tariff tariff, User user) {
        this.tariff = tariff;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(LocalDateTime connectionDate) {
        this.connectionDate = connectionDate;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return getId() == note.getId() &&
                Objects.equals(getConnectionDate(), note.getConnectionDate()) &&
                getTariff().equals(note.getTariff()) &&
                getUser().equals(note.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getConnectionDate(), getTariff(), getUser());
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", connectionDate=" + connectionDate +
                ", tariff=" + tariff +
                ", user=" + user +
                '}';
    }
}
