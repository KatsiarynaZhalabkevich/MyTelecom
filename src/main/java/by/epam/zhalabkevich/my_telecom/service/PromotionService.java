package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.util.List;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion) throws ServiceException;

    int addPromotionToTariff(long tariffId, long promotionId) throws ServiceException;

    int updatePromotion(Promotion promotion) throws ServiceException;

    int deletePromotionById(long id) throws ServiceException;

    List<Promotion> getPromotions(int offset, int limit) throws ServiceException;

    int addPromotionToTariffsList(List<Tariff> tariffs, long promotionId) throws ServiceException;

}
