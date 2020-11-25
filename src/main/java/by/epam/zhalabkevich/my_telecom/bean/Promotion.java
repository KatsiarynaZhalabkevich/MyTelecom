package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Promotion implements Serializable {
    private final static long serialVersionUID = 6L;
    private long id;
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private double discount;

    public Promotion() {
    }

    public Promotion(long id, String description, LocalDateTime dateStart, LocalDateTime dateEnd, double discount) {
        this.id = id;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return getId() == promotion.getId() &&
                Double.compare(promotion.getDiscount(), getDiscount()) == 0 &&
                getDescription().equals(promotion.getDescription()) &&
                getDateStart().equals(promotion.getDateStart()) &&
                getDateEnd().equals(promotion.getDateEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getDateStart(), getDateEnd(), getDiscount());
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", discount=" + discount +
                '}';
    }
}
