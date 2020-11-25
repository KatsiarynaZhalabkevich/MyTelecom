package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.util.List;

public interface TariffService {
    long getTariffQuantity() throws ServiceException;

    Tariff showTariffById(long id) throws ServiceException;

    Tariff addTariff(Tariff tariff) throws ServiceException;

    boolean deleteTariff(long id) throws ServiceException;

    boolean changeTariff(Tariff tariff) throws ServiceException;

    // List<UserTariff> showTariffsByUserId(int id) throws ServiceException;

    List<Tariff> showTariffRange(int page, int limit) throws ServiceException;

}
