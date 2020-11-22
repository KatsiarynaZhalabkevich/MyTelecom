package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.dao.impl.SQLAccountDAO;
import by.epam.zhalabkevich.my_telecom.dao.impl.SQLPromotionDAO;
import by.epam.zhalabkevich.my_telecom.dao.impl.SQLTariffDAO;
import by.epam.zhalabkevich.my_telecom.dao.impl.SQLUserDAO;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDao = new SQLUserDAO();
    private final TariffDAO tariffDAO = new SQLTariffDAO();
    private final AccountDAO accountDAO = new SQLAccountDAO();
    private final PromotionDAO promotionDAO = new SQLPromotionDAO();
   // private final NoteDAO noteDAO = new SQLNoteDAO();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDao() {
        return userDao;
    }

    public TariffDAO getTariffDAO() {return tariffDAO;}
    public PromotionDAO getPromotionDAO(){return promotionDAO;}
    public AccountDAO getAccountDAO() {return accountDAO;}

    //public NoteDAO getNoteDAO(){return noteDAO;}

}
