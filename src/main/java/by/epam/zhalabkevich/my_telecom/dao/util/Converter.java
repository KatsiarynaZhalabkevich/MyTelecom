package by.epam.zhalabkevich.my_telecom.dao.util;

import by.epam.zhalabkevich.my_telecom.bean.*;
import by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Converter {
    public final static Converter instance = new Converter();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Converter() {
    }

    public static Converter getConverter() {
        return instance;
    }

    public Tariff convertTariffFromResultSet(ResultSet resultSet) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getLong(QueryParameter.ID));
        tariff.setName(resultSet.getString(QueryParameter.NAME));
        tariff.setDescription(resultSet.getString(QueryParameter.DESCRIPTION));
        tariff.setPrice(resultSet.getBigDecimal(QueryParameter.PRICE));
        tariff.setSpeed(resultSet.getInt(QueryParameter.SPEED));
        return tariff;
    }

    public User convertUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(QueryParameter.ID));
        user.setName(resultSet.getString(QueryParameter.NAME));
        user.setSurname(resultSet.getString(QueryParameter.SURNAME));
        user.setPhone(resultSet.getString(QueryParameter.PHONE));
        user.setEmail(resultSet.getString(QueryParameter.EMAIL));
        user.setAddress(resultSet.getString(QueryParameter.ADDRESS));
        return user;
    }

    public Account convertAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong(QueryParameter.ID));
        account.setBalance(resultSet.getBigDecimal(QueryParameter.BALANCE));
        account.setStatus(Status.valueOf(resultSet.getString(QueryParameter.STATUS)));
        account.setRole(Role.valueOf(resultSet.getString(QueryParameter.ROLE)));
        account.setRegistrationDate(LocalDate.parse(
                String.valueOf(resultSet.getDate(QueryParameter.REGISTRATION_DATE)), formatter
        ));
        return account;
    }

    public Promotion convertPromotionFromResultSet(ResultSet resultSet) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId(resultSet.getLong(QueryParameter.PROMOTION_ID));
        promotion.setDescription(resultSet.getString(QueryParameter.DESCRIPTION));
        promotion.setDateStart(LocalDate.parse(resultSet.getString(QueryParameter.DATE_START), formatter1));
        promotion.setDateEnd(LocalDate.parse(resultSet.getString(QueryParameter.DATE_END), formatter1));
        promotion.setDiscount(resultSet.getDouble(QueryParameter.DISCOUNT));
        return promotion;
    }

    public Note convertNoteFromResultSet(ResultSet resultSet) throws SQLException {
        Note note = new Note();
        note.setId(resultSet.getLong(QueryParameter.NOTE_ID));
        note.setConnectionDate(LocalDate.parse(resultSet.getString(QueryParameter.CONNECTION_DATE), formatter1));
        return note;
    }

    public UserAccount convertUserAccountFromResultSet(ResultSet resultSet) throws SQLException {
        User user = convertUserFromResultSet(resultSet);
        Account account = convertAccountFromResultSet(resultSet);
        return new UserAccount(user, account);
    }
}
