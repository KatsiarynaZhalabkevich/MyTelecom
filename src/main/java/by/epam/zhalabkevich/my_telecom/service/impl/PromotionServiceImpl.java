package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.PromotionDAO;
import by.epam.zhalabkevich.my_telecom.service.PromotionService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.util.PromotionDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PromotionServiceImpl implements PromotionService {
    private final static Logger logger = LogManager.getLogger();
    private final DAOProvider provider = DAOProvider.getInstance();
    private final PromotionDAO dao = provider.getPromotionDAO();

    @Override
    public Promotion createPromotion(Promotion promotion) throws ServiceException {
        if (PromotionDataValidator.validatePromotion(promotion)) {
            try {
                return dao.addPromotion(promotion);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Impossible create promotion!");
            }
        } else {
            throw new ServiceException("Impossible create promotion! Some fields are invalid");
        }

    }

    @Override
    public int addPromotionToTariff(long tariffId, long promotionId) throws ServiceException {
        try {
            return dao.addPromotionToTariffById(tariffId, promotionId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible add promotion to tariff!");
        }
    }

    @Override
    public int updatePromotion(Promotion promotion) throws ServiceException {
        if (PromotionDataValidator.validatePromotion(promotion)) {
            try {
                return dao.updatePromotion(promotion);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Impossible update promotion!");
            }
        } else {
            throw new ServiceException("Impossible update promotion! Some fields are invalid");
        }
    }

    @Override
    public int deletePromotionById(long id) throws ServiceException {
        try {
            return dao.deletePromotionById(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible delete promotion!");
        }
    }

    @Override
    public List<Promotion> getPromotions(int offset, int limit) throws ServiceException {
        try {
            return dao.getPromotions(offset, limit);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible get promotion list!");
        }
    }

    @Override //много запросов в базу, так лучше не делать
    public int addPromotionToTariffsList(List<Tariff> tariffs, long promotionId) throws ServiceException {
        int i = 0;
        try {
            for (Tariff t : tariffs) {
                i += dao.addPromotionToTariffById(t.getId(), promotionId);

            }
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible add promotion tariffs list!");
        }
        return i;
    }
}
