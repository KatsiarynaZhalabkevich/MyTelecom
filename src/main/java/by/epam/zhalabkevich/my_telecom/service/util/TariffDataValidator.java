package by.epam.zhalabkevich.my_telecom.service.util;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.math.BigDecimal;


public class TariffDataValidator {
    private static final TariffDataValidator instance = new TariffDataValidator();

    private TariffDataValidator() {
    }

    public static TariffDataValidator getInstance() {
        return instance;
    }

    public boolean checkDescription(String description) {
        return description != null && description.length() > 15;
    }

    public boolean checkName(String name) {
        return name != null && name.length() > 10;
    }

    public boolean checkPrice(BigDecimal price) {
        return price.doubleValue() > 10;
    }

    public boolean checkSpeed(int speed) {
        return speed >= 10 && speed < 500;
    }

    public boolean validateTariff(Tariff tariff) {

        System.out.println("1"+checkDescription(tariff.getDescription()));
        System.out.println("2"+checkSpeed(tariff.getSpeed()));
        System.out.println("3"+checkName(tariff.getName()));
        System.out.println("4"+checkPrice(tariff.getPrice()));
        return checkDescription(tariff.getDescription()) && checkSpeed(tariff.getSpeed())
                && checkName(tariff.getName()) && checkPrice(tariff.getPrice());

    }
}
