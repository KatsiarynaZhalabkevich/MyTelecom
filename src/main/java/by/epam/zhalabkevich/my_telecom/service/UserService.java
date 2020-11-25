package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;

import java.util.List;

public interface UserService {
    User register(AuthorizationInfo info, User user) throws ServiceException;

    User authorize(AuthorizationInfo info) throws ServiceException;

    User saveUpdateUser(User user) throws ServiceException;

    int isLoginUniq(String login) throws ServiceException;

    List<User> getUsersRange(int page) throws ServiceException;

    boolean deleteUser(long id) throws ServiceException;
    // перенести в аккаунт
//    boolean changeBalanceById(int id, double balance) throws ServiceException;
//    boolean changeStatusById(int id, boolean active) throws ServiceException;
    User getUserById(long id) throws ServiceException;
    boolean updatePassword (String newPassword, User user) throws ServiceException;

}
