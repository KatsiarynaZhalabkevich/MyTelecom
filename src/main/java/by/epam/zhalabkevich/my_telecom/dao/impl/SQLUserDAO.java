package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.UserDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import by.epam.zhalabkevich.my_telecom.dao.util.QueryParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private final static Logger logger = LogManager.getLogger();

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    //+
    private final static String FIND_USER_BY_LOGIN = "SELECT auth_info_id, name, surname, address, phone, email, role, status FROM auth_info LEFT JOIN users ON auth_info.id = users.auth_info_id WHERE login = ?;";
    //+
    private final static String ADD_USER_AUTH_INFO = "INSERT INTO auth_info (login, password) value (?, ?)";
    //+
    private final static String ADD_USER = "INSERT INTO users (name, surname, phone, email, address, id) value (?,?,?,?,?,?);";
    private final static String FIND_USER_BY_ID = "SELECT * FROM  users WHERE user.id=?;";
    private final static String IS_LOGIN_EXIST = "SELECT COUNT(login) FROM auth_info WHERE login=?;";
    private final static String UPD_USER_INFO = "UPDATE users SET name=?, surname=?, phone=?, email=?, address=?, status = ?, role = ? WHERE id = ?;";
    private final static String UPD_PASS_BY_ID = "UPDATE auth_info SET password=? WHERE id=?;"; //+
    private final static String GET_PASS_BY_ID = "SELECT password FROM auth_info WHERE id=?";
    private final static String DELETE_USER_BY_ID = "DELETE  FROM auth_info WHERE id=?;";

    private final static String UPD_USER_BALANCE_BY_ID = "UPDATE accounts SET balance=? WHERE id=?";
    private final static String UPD_USER_STATUS_BY_ID = "UPDATE users SET status=? WHERE id=?";
    private static final String GET_USERS_FROM_TO = "SELECT * FROM users LIMIT ? OFFSET ?;";
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT auth_info_id, name, surname, address, phone, email, role, status FROM auth_info LEFT JOIN users ON auth_info.id = users.auth_info_id WHERE login = ? AND password = ?";

    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLUserDAO() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, FIND_USER_BY_LOGIN);
            setPreparedStatement(connection, ADD_USER);
            setPreparedStatement(connection, ADD_USER_AUTH_INFO);
            setPreparedStatement(connection, FIND_USER_BY_ID);
            setPreparedStatement(connection, IS_LOGIN_EXIST);
            setPreparedStatement(connection, UPD_USER_INFO);
            setPreparedStatement(connection, GET_PASS_BY_ID);
            setPreparedStatement(connection, DELETE_USER_BY_ID);
            setPreparedStatement(connection, UPD_PASS_BY_ID);
            setPreparedStatement(connection, UPD_USER_BALANCE_BY_ID);
            setPreparedStatement(connection, UPD_USER_STATUS_BY_ID);
            setPreparedStatement(connection, GET_USERS_FROM_TO);
            setPreparedStatement(connection, FIND_USER_BY_LOGIN_AND_PASSWORD);
        } catch (ConnectionPoolException | DAOException e) {
            logger.error(e);
        }
    }

    private User convertResultSet(ResultSet resultSet) throws DAOException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(QueryParameter.ID));
            user.setName(resultSet.getString(QueryParameter.NAME));
            user.setSurname(resultSet.getString(QueryParameter.SURNAME));
            user.setPhone(resultSet.getString(QueryParameter.PHONE));
            user.setEmail(resultSet.getString(QueryParameter.EMAIL));
            user.setAddress(resultSet.getString(QueryParameter.ADDRESS));
            user.setRole(Role.valueOf(resultSet.getString(QueryParameter.ROLE)));
            user.setStatus(Status.valueOf(resultSet.getString(QueryParameter.STATUS)));
//            user.setBalance(resultSet.getInt(QueryParameter.BALANCE));
//            user.setTime(resultSet.getTimestamp(QueryParameter.REGISTRATION_DATE));
//            user.setLogin(resultSet.getString(QueryParameter.LOGIN));
//            user.setPassword(resultSet.getString(QueryParameter.PASSWORD));
            return user;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }

    }


    private void setPreparedStatement(Connection connection, String sql) throws DAOException {
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatementMap.put(sql, preparedStatement);
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException(e);
            }
        }
    }

    //нужна ли транзакция на уровне серисов для создания нового юзера пароль + данные
    @Override
    public User addUser(User user) throws DAOException {

        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getAddress());
            statement.setLong(6, user.getId());
            return statement.executeUpdate() == 1 ? user : new User();

        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with new user");
            throw new DAOException(e);
        }

    }

    @Override //записали логин и пароль
    public Long addAuthInfo(AuthorizationInfo info) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get("INSERT INTO auth_info (login, password) value (?, ?);");
            statement.setString(1, info.getLogin());
            statement.setString(2, info.getPassword());
            if (statement.executeUpdate() == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getLong(1); //это значение присвоится в параметр пользователю
            } else return -1L;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL problem with auth user info");
        }
    }


    @Override
    public User findUserById(long id) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(FIND_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? convertResultSet(resultSet) : new User();
        } catch (SQLException e) {
            logger.error("cant get data by id");
            throw new DAOException(e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws DAOException {
        //"SELECT auth_info_id, name, surname, address, phone, email, role, status FROM auth_info LEFT JOIN users ON auth_info.id = users.auth_info_id WHERE login = ?";
        try {
            PreparedStatement statement = preparedStatementMap.get(FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? convertResultSet(resultSet) : new User();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }
//TODO
    @Override
    public AuthorizationInfo findUserAuthInfoByLogin(String login) throws DAOException {
        return null;
    }

    @Override
    public User findUserByLoginAndPassword(AuthorizationInfo info) throws DAOException {
        //"SELECT auth_info_id, name, surname, address, phone, email, role, status FROM auth_info LEFT JOIN users ON auth_info.id = users.auth_info_id WHERE login = ? AND password = ?";
        PreparedStatement statement = preparedStatementMap.get(FIND_USER_BY_LOGIN_AND_PASSWORD);
        try {
            statement.setString(1, info.getLogin());
            statement.setString(2, info.getPassword());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? convertResultSet(resultSet) : new User();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override //исправить имя метода в самом конце, чтобы сразу все
    public List<User> getUsersRange(int firstPosition, int limit) throws DAOException {
        List<User> users = new ArrayList<>();
        PreparedStatement statement = preparedStatementMap.get(GET_USERS_FROM_TO);
        try {
            statement.setInt(1, limit);
            statement.setInt(2, firstPosition);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(convertResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error(" can't get all users");
            throw new DAOException(e);
        }
        return users;
    }
//пока не будем реализовывать
//    @Override
//    public List<User> findUsersByName(String name) throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<User> findUsersBySurname(String surname) throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<User> findUsersByEmail(String email) throws DAOException {
//        return null;
//    }
//
//    @Override
//    public List<User> findUsersByPhone(String phone) throws DAOException {
//        return null;
//    }

    @Override //возвращает количество таких логинов
    public int isLoginExist(String login) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(IS_LOGIN_EXIST);
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public User updateUserInfo(User user) throws DAOException {
        //"UPDATE user SET name=?, surname=?, phone=?, email=?, address=?, status = ?, role = ?   WHERE id = ?;";
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_INFO);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getAddress());
            statement.setString(6, String.valueOf(user.getStatus()));
            statement.setString(7, String.valueOf(user.getRole()));
            statement.setLong(8, user.getId());
            return statement.executeUpdate() == 1 ? user : new User();
        } catch (SQLException e) {
            logger.error("sql cant upd info");
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override //передавать всего юзера или id
    public boolean updatePassword(String newPassword, User user) throws DAOException {
        //  UPDATE auth_info SET password=? WHERE id=?;
        try { //password уже зашифрован
            PreparedStatement statement = preparedStatementMap.get(UPD_PASS_BY_ID);
            statement.setString(1, newPassword);
            statement.setLong(2, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("sql cant upd info");
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override //передавать id или всего юзера
    public boolean updateStatus(Status status, User user) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_STATUS_BY_ID);
            statement.setString(1, String.valueOf(status));
            statement.setLong(2, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("can't upd active field");
        }
    }

//    @Override //баланс теперь на аккунте
//    public void updateUserBalanceById(int id, double balance) throws DAOException {
//
//    }

    @Override
    public boolean deleteUser(long id) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(DELETE_USER_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }
}
