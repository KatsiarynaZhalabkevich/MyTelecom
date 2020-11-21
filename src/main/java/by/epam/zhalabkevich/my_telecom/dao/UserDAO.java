package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.Status;
import by.epam.zhalabkevich.my_telecom.bean.User;

import java.util.List;

public interface UserDAO {
    //число пользователей может быть надо

    boolean addUser(User user) throws DAOException;

    Long addAuthInfo(AuthorizationInfo info) throws DAOException;

    User findUserById(long id) throws DAOException; //нужна ли

    User findUserByLogin(String login) throws DAOException;

    User findUserByLoginAndPassword(String login, String password) throws DAOException;

    List<User> getUsersRange(int page, int limit) throws DAOException;

//    List<User> findUsersByName(String name) throws DAOException;
//
//    List<User> findUsersBySurname(String surname) throws DAOException;
//
//    List<User> findUsersByEmail(String email) throws DAOException;
//
//    List<User> findUsersByPhone(String phone) throws DAOException;

    //изменить имя на unique
    boolean isLoginExist(String login) throws DAOException; // аналогично findBy Login

    //  String getPasswordByLogin(String login) throws DAOException; //надо другой метод по логину и паролю


    boolean updateUserInfo(User user) throws DAOException;

    boolean updatePassword(String newPassword, User user) throws DAOException;

    boolean updateStatus(Status status, User user) throws DAOException;

 //   void updateUserBalanceById(int id, double balance) throws DAOException; //переннести в аккаунт

    boolean deleteUser(long id) throws DAOException;

}
