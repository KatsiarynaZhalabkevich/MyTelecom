package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.TariffDAO;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.TariffService;
import by.epam.zhalabkevich.my_telecom.service.util.TariffDataValidator;

import java.util.List;

public class TariffServiceImpl implements TariffService {
    private final TariffDAO dao = DAOProvider.getInstance().getTariffDAO();
    private final TariffDataValidator validator = TariffDataValidator.getInstance();

    @Override
    public int getTariffQuantity() throws ServiceException {
        try {
            return dao.getTariffsQuantity();
        } catch (DAOException e) {
            throw new ServiceException("Impossible to get number of tariffs!");
        }
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
        if (validator.validateTariff(tariff)) {
            try {
                if (dao.getNumberTariffsWithName(tariff.getName()) == 0) {
                    return dao.addTariff(tariff);
                }
                return new Tariff();
            } catch (DAOException e) {
                throw new ServiceException("Impossible create a new tariff!");
            }
        } else {
            throw new ServiceException("Some field of new tariff are invalid!");
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
        if (validator.validateTariff(tariff)) {
            try {
                return dao.editTariff(tariff);
            } catch (DAOException e) {
                throw new ServiceException("Impossible to update tariff info");
            }
        } else {
            throw new ServiceException("Some field of new tariff are invalid!");
        }

    }

    @Override
    public List<TariffNote> showUserTariffsByAccountId(long id) throws ServiceException {
        try {
            return dao.getUsersTariffsWithAdditionalInfoByAccountId(id);
        } catch (DAOException e) {
            throw new ServiceException("Impossible to get tariffs for user");
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

    @Override
    public int getTariffNotesQuantity() throws ServiceException {
        try {
            return dao.getTariffNotesQuantity();
        } catch (DAOException e) {
            throw new ServiceException("impossible to get number of tariff notes!");
        }
    }

    @Override
    public int getTariffNotesQuantityByTariffId(long id) throws ServiceException {
        try {
            return dao.getTariffNotesQuantityByTariffId(id);
        } catch (DAOException e) {
            throw new ServiceException("impossible to get number of tariff notes by tariff id!");
        }
    }
}
