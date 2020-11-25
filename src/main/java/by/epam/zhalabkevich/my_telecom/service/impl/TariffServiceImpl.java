package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.TariffDAO;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.TariffService;

import java.util.List;

public class TariffServiceImpl implements TariffService {
    private final TariffDAO dao = DAOProvider.getInstance().getTariffDAO();

    @Override
    public long getTariffQuantity() throws ServiceException {
        return 0;
    }

    @Override
    public Tariff showTariffById(long id) throws ServiceException {
        try {
            return dao.getTariffById(id);
        } catch (DAOException e) {
          throw new ServiceException("Impossible to get information about tariff!");
        }
    }

    @Override
    public Tariff addTariff(Tariff tariff) throws ServiceException {
        try {
            return dao.addTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Impossible create a new tariff!");
        }
    }

    @Override
    public boolean deleteTariff(long id) throws ServiceException {
        try {
            return dao.deleteTariffById(id);
        } catch (DAOException e) {
           throw new ServiceException("Impossible delete tariff");
        }
    }

    @Override
    public boolean changeTariff(Tariff tariff) throws ServiceException {
        try {
            return dao.editTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to update tariff info");
        }
    }

    @Override
    public List<Tariff> showTariffRange(int page, int limit) throws ServiceException {
        try {
            return dao.getTariffRange(page, limit);
        } catch (DAOException e) {
           throw new ServiceException("Impossible to show tariffs");
        }
    }
}
