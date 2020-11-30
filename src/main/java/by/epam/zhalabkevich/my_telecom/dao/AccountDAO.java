package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;

import java.math.BigDecimal;

public interface AccountDAO {

    boolean addAccount(long id) throws DAOException;

    int updateAccount(Account account) throws DAOException;

    int deleteAccountById(long id) throws DAOException;

    Account getAccountByUserId(long id) throws DAOException;

    boolean updateBalanceByUserId(long id, BigDecimal balance) throws DAOException;

    boolean updateStatus(Status status, long id) throws DAOException;

    boolean updateRole(Role role, long id) throws DAOException;

    Status getStatusByUserId(long id) throws DAOException;

    Role getRoleByUserId(long id) throws DAOException;

    BigDecimal getBalanceByUserId(long id) throws DAOException;
}
