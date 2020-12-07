package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;

import java.util.List;

public interface TariffService {
    int getTariffQuantity() throws ServiceException;

    Tariff showTariffById(long id) throws ServiceException;

    Tariff addTariff(Tariff tariff) throws ServiceException;

    boolean deleteTariff(long id) throws ServiceException;

    boolean changeTariff(Tariff tariff) throws ServiceException;

    List<TariffNote> showUserTariffsByAccountId(long id) throws ServiceException;

    List<Tariff> showTariffRange(int page, int limit) throws ServiceException;

    int getTariffNotesQuantity() throws ServiceException;

    int getTariffNotesQuantityByTariffId(long id) throws ServiceException;

}
