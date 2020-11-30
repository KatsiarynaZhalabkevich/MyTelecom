package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;

import java.math.BigDecimal;

public interface AccountService {

    Role checkRoleByUserId(long id) throws ServiceException;

    Status checkStatusByUserId(long id) throws ServiceException;

    BigDecimal getBalanceByUserId(long id) throws ServiceException;

    Account getAccountByUserId(long id) throws ServiceException;

    boolean updateBalanceByUserId(long id, BigDecimal balance) throws ServiceException;

    boolean updateStatusByUserId(long id, Status status) throws ServiceException;


}
