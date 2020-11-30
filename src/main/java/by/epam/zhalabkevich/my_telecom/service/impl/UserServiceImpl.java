package by.epam.zhalabkevich.my_telecom.service.impl;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;
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

//TODO выбрасывать исключение с описанием ошибки или возвращать пустого пользователя?
    public User register(AuthorizationInfo info, User user) throws ServiceException {
        if (isLoginUniq(info.getLogin()) == 0) {
            if (validator.checkPassword(info.getPassword())) {
                String passHash = PasswordCreater.createPassword(info.getPassword());
                info.setPassword(passHash);
                if (validator.userValidate(user)) {
                    try {
                        user.setId(userDAO.addAuthInfo(info));
                        accountDAO.addAccount(user.getId());
                       return userDAO.addUser(user);
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

    @Override //может быть возвращать аккаунт?
    public User authorize(AuthorizationInfo info) throws ServiceException {
        try {
            AuthorizationInfo infoFromDB = userDAO.findUserAuthInfoByLogin(info.getLogin());
            System.out.println("user info"+info);
            System.out.println("info from DB"+infoFromDB);
            return PasswordCreater.verifyPassword(info.getPassword(), infoFromDB.getPassword())?
                    userDAO.findUserByLogin(infoFromDB.getLogin()): new User();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Impossible to authorize user");
        }
    }
    //чтобы зарегистрировать нового пользователя у него должен быть логин пароль + инфо с обязательным email
    //при сохранении логина и пароля создаем юзера и туда сеттаем id? на следующем этапе заполняем все другие поля
    //
//TODO всегда будет один и тот же сценарий, тк user регистрируется в другом месте
    @Override
    public User saveUpdateUser(User user) throws ServiceException {
        if(validator.userValidate(user)){
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
        }else{
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
        if(validator.checkPassword(newPassword)){
            try {
              return userDAO.updatePassword(newPassword, user);
            } catch (DAOException e) {
                logger.error(e);
                throw  new ServiceException("Impossible to update password");
            }
        }else{
            throw new ServiceException("New password is not valid");
        }

    }
}
