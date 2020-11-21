package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Account;

public interface AccountDAO {
    int addAccount(Account account) throws DAOException;
    int updateAccount(Account account) throws DAOException;
    int deleteAccountById(long id) throws DAOException;
    Account getAccountById(long id) throws DAOException;

}
