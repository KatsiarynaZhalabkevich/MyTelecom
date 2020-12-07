package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;

import java.util.List;

public interface TariffDAO {
    Tariff addTariff(Tariff tariff) throws DAOException;

    boolean editTariff(Tariff tariff) throws DAOException;

    Tariff getTariffById(long id) throws DAOException;

    boolean deleteTariffById(long id) throws DAOException;

    //сделать dto тоже пагинация нужна? хотя в теории может быть 2-3 таких тарифа, максимум 5
    List<Tariff> getTariffsByAccountId(long id) throws DAOException;

    // List<UserTarif>getTariffsByUserId(long id) throws DAOException;
    //переименовать потом
    List<Tariff> getTariffRange(int firstPosition, int limit) throws DAOException;

    List<TariffNote> getUsersTariffsWithAdditionalInfoByAccountId(long id) throws DAOException;

    int getTariffsQuantity() throws DAOException;

    int getTariffNotesQuantity() throws DAOException;

    int getTariffNotesQuantityByTariffId(long id) throws DAOException;
}
