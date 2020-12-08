package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount;

import java.util.List;

public interface UserService {
    int getUsersNumber() throws ServiceException;

    User register(AuthorizationInfo info, User user) throws ServiceException;

    User authorize(AuthorizationInfo info) throws ServiceException;

    User saveUpdateUser(User user) throws ServiceException;

    int isLoginUniq(String login) throws ServiceException;

    List<User> getUsersRange(int page, int limit) throws ServiceException;

    List<UserAccount> getUsersAccounts(int page, int limit) throws ServiceException;

    boolean deleteUser(long id) throws ServiceException;

    User getUserById(long id) throws ServiceException;

    boolean updatePassword(String newPassword, User user) throws ServiceException;

    List<UserAccount> findUsersByParameters(User user) throws ServiceException;

}
