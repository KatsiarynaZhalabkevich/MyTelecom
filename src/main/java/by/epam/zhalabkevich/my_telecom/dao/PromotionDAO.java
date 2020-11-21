package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;

public interface PromotionDAO {
    Promotion addPromotion(Promotion promotion) throws DAOException;

    int updatePromotion(Promotion promotion) throws DAOException;

    int deletePromotionById(long id) throws DAOException;
}
