package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.NoteDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SQLNoteDAO implements NoteDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();
    private final static String DELETE_NOTE = "DELETE FROM tariff_notes WHERE id=?;";
    private final static String ADD_NOTE = "INSERT INTO tariff_notes (connection_date, tariff_id, account_id) value (?, ?, ?)";

    public SQLNoteDAO() {
        Connection connection;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, ADD_NOTE);
            setPreparedStatement(connection, DELETE_NOTE);
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
    public boolean addNoteToAccount(long tariffId, long accountId) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(ADD_NOTE);
        try {
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setLong(2, tariffId);
            statement.setLong(3, accountId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public boolean deleteNoteFromAccount(long noteId) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(DELETE_NOTE);
        try {
            statement.setLong(1, noteId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }
}
