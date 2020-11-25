package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;

import java.util.List;

public interface PromotionDAO {
    Promotion addPromotion(Promotion promotion) throws DAOException;

    int updatePromotion(Promotion promotion) throws DAOException;

    int deletePromotionById(long id) throws DAOException;

    int addPromotionToTariffById(long tariffId, long promotionId) throws DAOException;

    List<Promotion> getPromotions (int offset, int limit) throws DAOException;

    //int addPromotionToTariffsList(List<Tariff> tariffList) throws DAOException;
}
