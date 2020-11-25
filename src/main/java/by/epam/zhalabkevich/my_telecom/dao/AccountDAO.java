package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Status;
import by.epam.zhalabkevich.my_telecom.bean.User;

public interface AccountDAO {
    int addAccount(Account account) throws DAOException;
    int updateAccount(Account account) throws DAOException;
    int deleteAccountById(long id) throws DAOException;
    Account getAccountById(long id) throws DAOException;
    void updateUserBalanceById(int id, double balance) throws DAOException;
    boolean updateStatus(Status status, User user) throws DAOException;
}
