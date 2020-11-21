package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.UserDAO;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import by.epam.zhalabkevich.my_telecom.service.util.PasswordCreater;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final DAOProvider provider = DAOProvider.getInstance();
    private final UserDAO userDAO = provider.getUserDao();

    @Override
    public User authorize(String login, String password) throws ServiceException {
        //сравнить зашифрованные или расшифрованные?
       password = PasswordCreater.createPassword(password);

        return null;
    }

    @Override
    public int saveUpdateUser(User user) throws ServiceException {
        return 0;
    }

    @Override
    public int isLoginUniq(String login) throws ServiceException {
        return 0;
    }

    @Override
    public List<User> getUsersRange(long page) throws ServiceException {
        return null;
    }

    @Override
    public boolean deleteUser(long id) throws ServiceException {
        return false;
    }

    @Override
    public User getUserById(long id) throws ServiceException {
        return null;
    }
}
