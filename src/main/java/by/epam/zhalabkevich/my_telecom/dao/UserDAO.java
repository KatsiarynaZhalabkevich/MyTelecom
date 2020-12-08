package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount;

import java.util.List;

public interface UserDAO {
    int getUsersNumber() throws DAOException;

    User addUser(User user) throws DAOException;

    Long addAuthInfo(AuthorizationInfo info) throws DAOException;

    User findUserById(long id) throws DAOException; //нужна ли

    User findUserByLogin(String login) throws DAOException;

    AuthorizationInfo findUserAuthInfoByLogin(String login) throws DAOException;

    User findUserByLoginAndPassword(AuthorizationInfo info) throws DAOException;

    List<User> getUsersRange(int page, int limit) throws DAOException;

    List<UserAccount> getUsersWithAccount(int offset, int limit) throws DAOException;

    List<UserAccount> findUsersByParameters(User user) throws DAOException;

    int isLoginUnique(String login) throws DAOException; // аналогично findBy Login

    User updateUserInfo(User user) throws DAOException;

    boolean updatePassword(String newPassword, User user) throws DAOException;

    boolean deleteUser(long id) throws DAOException;

}
