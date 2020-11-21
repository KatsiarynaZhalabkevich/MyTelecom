package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Tariff implements Serializable {
    private final static long serialVersionUID = 4L;
    private long id;
    private String name;
    private BigDecimal price;
    private int speed;
    private String description;


    public Tariff() {
    }

    public Tariff(String name, BigDecimal price, int speed, String description) {
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return getId() == tariff.getId() &&
                getSpeed() == tariff.getSpeed() &&
                getName().equals(tariff.getName()) &&
                getPrice().equals(tariff.getPrice()) &&
                getDescription().equals(tariff.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getSpeed(), getDescription());
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", speed=" + speed +
                ", description='" + description + '\'' +
                '}';
    }
}
