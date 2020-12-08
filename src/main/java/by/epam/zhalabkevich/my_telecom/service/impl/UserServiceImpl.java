package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount;
import by.epam.zhalabkevich.my_telecom.dao.AccountDAO;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.dao.UserDAO;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import by.epam.zhalabkevich.my_telecom.service.util.PasswordCreater;
import by.epam.zhalabkevich.my_telecom.service.util.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final static Logger logger = LogManager.getLogger();
    private final DAOProvider provider = DAOProvider.getInstance();
    private final UserDAO userDAO = provider.getUserDao();
    private final AccountDAO accountDAO = provider.getAccountDAO();
    private final UserDataValidator validator = UserDataValidator.getInstance();

    @Override
    public int getUsersNumber() throws ServiceException {
        try {
            return userDAO.getUsersNumber();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public User register(AuthorizationInfo info, User user) throws ServiceException {
        if (isLoginUniq(info.getLogin()) == 0) {
            logger.debug("login uniq");
            if (validator.checkPassword(info.getPassword())) {
                String passHash = PasswordCreater.createPassword(info.getPassword());
                info.setPassword(passHash);
                if (validator.userValidate(user)) {
                    try {
                        user.setId(userDAO.addAuthInfo(info));
                        logger.debug("auth info created");
                        User userFromDB = userDAO.addUser(user);
                        logger.debug("user created");
                        accountDAO.addAccount(user.getId());
                        logger.debug("account created");
                        return userFromDB;
                    } catch (DAOException e) {
                        logger.error(e);
                        throw new ServiceException("Impossible register a new user");
                    }
                } else {
                    throw new ServiceException("Some fields are invalid");
                }

            } else {
                throw new ServiceException("Password is not valid");
            }
        } else {
            throw new ServiceException("Login already exists");
        }
    }

    @Override
    public User authorize(AuthorizationInfo info) throws ServiceException {
        try {
            AuthorizationInfo infoFromDB = userDAO.findUserAuthInfoByLogin(info.getLogin());
            return PasswordCreater.verifyPassword(info.getPassword(), infoFromDB.getPassword()) ?
                    userDAO.findUserByLogin(infoFromDB.getLogin()) : new User();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to authorize user");
        }
    }

    @Override
    public User saveUpdateUser(User user) throws ServiceException {
        if (validator.userValidate(user)) {
            try {
                if (user.getId() > 0) {
                    return userDAO.updateUserInfo(user);
                } else {
                    return userDAO.addUser(user);
                }
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Impossible to save or update user");
            }
        } else {
            throw new ServiceException("Some fields are not valid");
        }
    }

    @Override
    public int isLoginUniq(String login) throws ServiceException {
        if (validator.checkLogin(login)) {
            try {
                return userDAO.isLoginUnique(login);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Impossible to get data about login");
            }
        } else {
            throw new ServiceException("Login is not valid!");
        }
    }

    @Override
    public List<User> getUsersRange(int page, int limit) throws ServiceException {
        try {
            return userDAO.getUsersRange(page, limit);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to show users");
        }
    }

    @Override
    public List<UserAccount> getUsersAccounts(int page, int limit) throws ServiceException {
        try {
            return userDAO.getUsersWithAccount(page, limit);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to show users and accounts");
        }
    }

    @Override
    public boolean deleteUser(long id) throws ServiceException {
        try {
            return userDAO.deleteUser(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to delete user");
        }
    }

    @Override
    public User getUserById(long id) throws ServiceException {
        try {
            return userDAO.findUserById(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to get User");
        }
    }

    @Override
    public boolean updatePassword(String newPassword, User user) throws ServiceException {
        if (validator.checkPassword(newPassword)) {
            String cryptPass = PasswordCreater.createPassword(newPassword);
            try {
                return userDAO.updatePassword(cryptPass, user);
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException("Impossible to update password");
            }
        } else {
            throw new ServiceException("New password is not valid");
        }

    }

    @Override //данные для поиска не валидируем, их все равно в БД не записываем
    public List<UserAccount> findUsersByParameters(User user) throws ServiceException {
//       User userWithFlexibleParameters = new User(
//               convertParameter(user.getName()),
//               convertParameter(user.getSurname()),
//               convertParameter(user.getPhone()),
//               convertParameter(user.getEmail()),
//               convertParameter(user.getAddress())
//       );

        try {
            return userDAO.findUsersByParameters(user);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to find users with such parameters! Try change criteria!");
        }
    }

    private String convertParameter(String parameter){
        return "%"+parameter+"%";
    }
}
