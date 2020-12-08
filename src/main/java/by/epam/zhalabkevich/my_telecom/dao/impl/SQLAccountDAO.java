package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;
import by.epam.zhalabkevich.my_telecom.dao.AccountDAO;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import by.epam.zhalabkevich.my_telecom.dao.util.Converter;
import by.epam.zhalabkevich.my_telecom.dao.util.QueryParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SQLAccountDAO implements AccountDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_ACCOUNT = "INSERT INTO accounts SET user_id = ?, registration_date = ?;";
    private static final String UPD_ACCOUNT = "UPDATE accounts SET balance = ?, registration_date = ? WHERE user_id = ?;";
    private static final String DEL_ACCOUNT = "DELETE FROM accounts WHERE user_id = ?;";
    private static final String GET_ACCOUNT_BY_ID = "SELECT id, balance, registration_date, role, status FROM accounts WHERE user_id = ?;";
    private final static String UPD_USER_STATUS_BY_ID = "UPDATE accounts SET status=? WHERE user_id=?";
    private static final String UPD_BALANCE_BY_USER_ID = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
    private static final String UPD_USER_ROLE_BY_ID = "UPDATE accounts SET role=? WHERE user_id=?";
    private static final String GET_STATUS_BY_USER_ID = "SELECT  status FROM accounts WHERE user_id = ?;";
    private static final String GET_ROLE_BY_USER_ID = "SELECT  role FROM accounts WHERE user_id = ?;";
    private static final String BLOCK_ACCOUNT_WITH_NEGATIVE_BALANCE = "UPDATE accounts SET status='BLOCKED' WHERE balance < 0";
    private static final String WITHDRAW_PAYMENT_FOR_MONTH = "UPDATE accounts as a JOIN tariff_notes as n ON a.id = n.account_id JOIN tariffs as t ON t.id = n.tariff_id JOIN promotions as p ON p.id = t.promotion_id SET balance = balance - t.price*(1-p.discount);";

    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLAccountDAO() {
        Connection connection;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, ADD_ACCOUNT);
            setPreparedStatement(connection, UPD_ACCOUNT);
            setPreparedStatement(connection, DEL_ACCOUNT);
            setPreparedStatement(connection, GET_ACCOUNT_BY_ID);
            setPreparedStatement(connection, UPD_BALANCE_BY_USER_ID);
            setPreparedStatement(connection, UPD_USER_STATUS_BY_ID);
            setPreparedStatement(connection, GET_ROLE_BY_USER_ID);
            setPreparedStatement(connection, GET_STATUS_BY_USER_ID);
            setPreparedStatement(connection, UPD_USER_ROLE_BY_ID);
            setPreparedStatement(connection, BLOCK_ACCOUNT_WITH_NEGATIVE_BALANCE);
            setPreparedStatement(connection, WITHDRAW_PAYMENT_FOR_MONTH);
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

    @Override
    public boolean addAccount(long id) throws DAOException {
        //INSERT INTO accounts SET user_id = ?, registration_date = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_ACCOUNT);
            statement.setLong(1, id);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
    }

    @Override //user_id может не совпадать, хотя хотелось бы. Подстроить id или убрать авто генерацию
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

    @Override //удалять по user_id???
    public int deleteAccountByUserId(long id) throws DAOException {
        //DELETE FROM accounts WHERE user_id = ?;
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
    public Account getAccountByUserId(long id) throws DAOException {
        //SELECT id, balance, status, registration_date FROM accounts WHERE user_id = ?;
        Account account = new Account();
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                account = Converter.getConverter().convertAccountFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with account");
            throw new DAOException(e);
        }
        return account;
    }

    @Override
    public boolean updateBalanceByUserId(long id, BigDecimal balance) throws DAOException {
        //UPDATE accounts SET balance = ? WHERE user_id = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_BALANCE_BY_USER_ID);
            statement.setString(1, String.valueOf(balance));
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }

    }


    @Override
    public boolean updateStatus(Status status, long id) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_STATUS_BY_ID);
            statement.setString(1, String.valueOf(status));
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Impossible to update user's status");
        }
    }

    @Override
    public boolean updateRole(Role role, long id) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_USER_ROLE_BY_ID);
            statement.setString(1, String.valueOf(role));
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Impossible to update user's role");
        }
    }

    @Override
    public Status getStatusByUserId(long id) throws DAOException {
        //SELECT  status FROM accounts WHERE user_id = ?;
        Status status = null;
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_STATUS_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                status = Status.valueOf(resultSet.getString(QueryParameter.STATUS));
            }
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with getting status");
            throw new DAOException(e);
        }
        return status;
    }

    @Override
    public Role getRoleByUserId(long id) throws DAOException {
        Role role = null;
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_ROLE_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = Role.valueOf(resultSet.getString(QueryParameter.ROLE));
            }
        } catch (SQLException e) {
            logger.error(e);
            logger.error("SQL problem with getting role");
            throw new DAOException(e);
        }
        return role;
    }

    //TODO implement method!!!
    @Override
    public BigDecimal getBalanceByUserId(long id) throws DAOException {
        return BigDecimal.valueOf(10000);
    }

    @Override
    public int blockAccountsWithNegativeBalance() throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(BLOCK_ACCOUNT_WITH_NEGATIVE_BALANCE);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Impossible to update user's status");
        }
    }

    @Override
    public boolean withdrawPaymentForMonth() throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(WITHDRAW_PAYMENT_FOR_MONTH);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("Impossible to withdraw payment");
        }
    }
}
