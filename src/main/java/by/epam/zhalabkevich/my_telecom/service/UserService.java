package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.User;

import java.util.List;

public interface UserService {
    User authorize(String login, String password) throws ServiceException;

    int saveUpdateUser(User user) throws ServiceException;

    int isLoginUniq(String login) throws ServiceException;

    List<User> getUsersRange(long page) throws ServiceException;

    boolean deleteUser(long id) throws ServiceException;

    // перенести в аккаунт
//    boolean changeBalanceById(int id, double balance) throws ServiceException;
//    boolean changeStatusById(int id, boolean active) throws ServiceException;
    User getUserById(long id) throws ServiceException;

}
