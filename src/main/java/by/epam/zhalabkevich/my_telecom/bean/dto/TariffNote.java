package by.epam.zhalabkevich.my_telecom.bean.dto;

import by.epam.zhalabkevich.my_telecom.bean.Note;
import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.io.Serializable;
import java.util.Objects;

public class TariffNote implements Serializable {
    private final static long serialVersionUID = 3L;
    private Tariff tariff;
    private Promotion promotion;
    private Note note;

    public TariffNote() {
    }

    public TariffNote(Tariff tariff, Promotion promotion, Note note) {
        this.tariff = tariff;
        this.promotion = promotion;
        this.note = note;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffNote that = (TariffNote) o;
        return Objects.equals(getTariff(), that.getTariff()) && Objects.equals(getPromotion(), that.getPromotion()) && Objects.equals(getNote(), that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTariff(), getPromotion(), getNote());
    }

    @Override
    public String toString() {
        return "TariffNote{" +
                "tariff=" + tariff +
                ", promotion=" + promotion +
                ", note=" + note +
                '}';
    }
}
