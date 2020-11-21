package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.dao.AccountDAO;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import by.epam.zhalabkevich.my_telecom.dao.util.QueryParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SQLAccountDAO implements AccountDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();


    private static final String ADD_ACCOUNT = "INSERT INTO accounts (balance, registration_date, id) VALUE (?, ?, ?);";
    private static final String UPD_ACCOUNT = "UPDATE accounts SET balance = ?, registration_date = ? WHERE id = ?;";
    private static final String DEL_ACCOUNT = "DELETE FROM accounts WHERE id = ?;";
    private static final String GET_ACCOUNT_BY_ID = "SELECT balance, registration_date FROM accounts WHERE id = ?;";

    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLAccountDAO() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, ADD_ACCOUNT);
            setPreparedStatement(connection, UPD_ACCOUNT);
            setPreparedStatement(connection, "");
            setPreparedStatement(connection, "");
            setPreparedStatement(connection, "");
            setPreparedStatement(connection, "");

        } catch (ConnectionPoolException | DAOException e) {
            logger.error(e);
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
    //объединить в один метод??? add&update

    @Override //id уже есть из user
    public int addAccount(Account account) throws DAOException {
        //INSERT INTO accounts (balance, registration_date, id) VALUE (?, ?, ?);
        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_ACCOUNT);
            statement.setBigDecimal(1, account.getBalance());
            statement.setDate(2, Date.valueOf(String.valueOf(account.getRegistrationDate())));
            statement.setLong(3, account.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
    }

    @Override
    public int updateAccount(Account account) throws DAOException {
        //UPDATE accounts SET balance = ?, registration_date = ? WHERE id = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_ACCOUNT);
            statement.setBigDecimal(1, account.getBalance());
            statement.setDate(2, Date.valueOf(String.valueOf(account.getRegistrationDate())));
            statement.setLong(3, account.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
    }

    @Override
    public int deleteAccountById(long id) throws DAOException {
        //DELETE FROM accounts WHERE id = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(DEL_ACCOUNT);
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
    }

    @Override
    public Account getAccountById(long id) throws DAOException {
        //SELECT balance, registration_date FROM accounts WHERE id = ?;
        Account account = new Account();
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                account.setId(id);
                account.setBalance(resultSet.getBigDecimal(QueryParameter.BALANCE));
                account.setRegistrationDate(LocalDateTime.parse(String.valueOf(resultSet.getDate(QueryParameter.REGISTRATION_DATE))));
            }
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
        return account;
    }
}
