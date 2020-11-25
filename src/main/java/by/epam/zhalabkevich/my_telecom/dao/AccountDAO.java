package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Status;

public interface AccountDAO {
    boolean addAccount(long id) throws DAOException;

    int updateAccount(Account account) throws DAOException;

    int deleteAccountById(long id) throws DAOException;

    Account getAccountByUserId(long id) throws DAOException;

    boolean updateBalanceByUserId(long id, double balance) throws DAOException;

    boolean updateStatus(Status status, long id) throws DAOException;
}
