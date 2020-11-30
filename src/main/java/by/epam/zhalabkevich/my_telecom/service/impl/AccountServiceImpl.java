package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;
import by.epam.zhalabkevich.my_telecom.dao.AccountDAO;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.UserDAO;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.util.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {
    private final static Logger logger = LogManager.getLogger();
    private final DAOProvider provider = DAOProvider.getInstance();
    private final UserDAO userDAO = provider.getUserDao();
    private final AccountDAO accountDAO = provider.getAccountDAO();
    private final UserDataValidator validator = UserDataValidator.getInstance();

    @Override
    public Role checkRoleByUserId(long id) throws ServiceException {
        try {
            return accountDAO.getRoleByUserId(id);
        } catch (DAOException e) {
           logger.error(e);
           throw new ServiceException("Impossible to get info about user role!");
        }
    }

    @Override
    public Status checkStatusByUserId(long id) throws ServiceException {
        try {
            return accountDAO.getStatusByUserId(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to get info about user status!");
        }
    }
//TODO write method if necessary
    @Override
    public BigDecimal getBalanceByUserId(long id) throws ServiceException {
        try {
           return accountDAO.getBalanceByUserId(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to get info about user balance!");
        }
    }

    @Override
    public Account getAccountByUserId(long id) throws ServiceException {
        try {
            return accountDAO.getAccountByUserId(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to get info about user account!");
        }
    }

    @Override
    public boolean updateBalanceByUserId(long id, BigDecimal balance) throws ServiceException {
        try {
            return accountDAO.updateBalanceByUserId(id, balance);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to update balance!");
        }
    }

    public boolean updateStatusByUserId(long id, Status status) throws ServiceException{
        try {
            return accountDAO.updateStatus(status, id);
        }catch (DAOException e){
            logger.error(e);
            throw new ServiceException("Impossible to update status!");
        }
    }
}
