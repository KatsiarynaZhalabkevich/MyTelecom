package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;

import java.util.List;

public interface TariffDAO {
    Tariff addTariff(Tariff tariff) throws DAOException;
    boolean editTariff(Tariff tariff) throws DAOException;
    Tariff getTariffById(long id) throws DAOException;
    boolean deleteTariffById(long id) throws DAOException;
    //сделать dto
   // List<UserTarif>getTariffsByUserId(long id) throws DAOException;
    //переименовать потом
    List<Tariff> getTariffRange(int firstPosition, int limit) throws DAOException;
}
