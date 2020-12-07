package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Note;
import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.TariffDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import by.epam.zhalabkevich.my_telecom.dao.util.Converter;
import by.epam.zhalabkevich.my_telecom.dao.util.QueryParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLTariffDAO implements TariffDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();
    private final Converter converter = Converter.getConverter();

    private final static String UPD_TARIFF = "UPDATE  tariffs SET name=?, description=?, price=?, speed=? WHERE id=?;";
    private final static String ADD_TARIFF = "INSERT INTO tariffs (name, description, price, speed, discount) values (?,?,?,?,?);";
    private final static String GET_TARIFF_BY_ID = "SELECT name, price, speed, description  FROM tariffs WHERE id=?;";
    private final static String DEL_TARIFF_BY_ID = "DELETE  FROM tariffs WHERE id=?;";
    private final static String GET_TARIFF_BY_USER_ID = "SELECT tariffs.id as id, name, description, speed, price FROM tariffs JOIN tariff_notes ON tariffs.id = tariff_notes.tariff_id WHERE tariff_notes.account_id = ?;";
    private final static String GET_TARIFF_FROM_TO = "SELECT id, name, price, speed, description FROM tariffs LIMIT ? OFFSET ?;";
    private static final String GET_USERS_TARIFFS_WITH_INFO = "SELECT tariffs.id, tariff_notes.id speed, name, price, promotions.description, discount, date_start, date_end, connection_date FROM tariffs JOIN tariff_notes ON tariff_notes.tariff_id=tariffs.id JOIN promotions ON tariffs.id = promotions.id WHERE tariff_notes.account_id=?;";
    private static final String GET_TARIFFS_NUMBER = "SELECT COUNT(*) FROM tariffs";
    private static final String GET_TARIFF_NOTES_NUMBER = "SELECT COUNT(*) FROM tariff_notes";
    private static final String GET_TARIFF_NOTES_NUMBER_BY_TARIFF_ID = "SELECT COUNT(*) FROM tariff_notes WHERE tariff_id = ?";

    public SQLTariffDAO() {
        Connection connection;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, UPD_TARIFF);
            setPreparedStatement(connection, ADD_TARIFF);
            setPreparedStatement(connection, GET_TARIFF_BY_ID);
            setPreparedStatement(connection, DEL_TARIFF_BY_ID);
            setPreparedStatement(connection, GET_TARIFF_BY_USER_ID);
            setPreparedStatement(connection, GET_TARIFF_FROM_TO);
            setPreparedStatement(connection, GET_USERS_TARIFFS_WITH_INFO);
            setPreparedStatement(connection, GET_TARIFFS_NUMBER);
            setPreparedStatement(connection, GET_TARIFF_NOTES_NUMBER);
            setPreparedStatement(connection, GET_TARIFF_NOTES_NUMBER_BY_TARIFF_ID);
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
    public Tariff addTariff(Tariff tariff) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(ADD_TARIFF);
        try {
            statement.setString(1, tariff.getName());
            statement.setString(2, tariff.getDescription());
            statement.setBigDecimal(3, tariff.getPrice());
            statement.setInt(4, tariff.getSpeed());
            if (statement.executeUpdate() == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                tariff.setId(resultSet.getLong(1)); //это значение присвоится в параметр пользователю
            }
            return tariff;
        } catch (SQLException e) {
            logger.error("Can't add tariff");
            throw new DAOException(e);
        }

    }

    @Override
    public boolean editTariff(Tariff tariff) throws DAOException {
        //"UPDATE  tariffs SET name=?, description=?, price=?, speed=? WHERE id=?;";
        PreparedStatement statement = preparedStatementMap.get(UPD_TARIFF);
        try {
            statement.setString(1, tariff.getName());
            statement.setString(2, tariff.getDescription());
            statement.setBigDecimal(3, tariff.getPrice());
            statement.setInt(4, tariff.getSpeed());
            statement.setLong(6, tariff.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't add tariff");
            throw new DAOException(e);
        }
    }

    @Override
    public Tariff getTariffById(long id) throws DAOException {
        Tariff tariff = null;
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tariff = converter.convertTariffFromResultSet(resultSet);
            }
            return tariff;
        } catch (SQLException e) {
            logger.error("Can't get tariff by Id");
            throw new DAOException(e);
        }

    }

    @Override
    public boolean deleteTariffById(long id) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(DEL_TARIFF_BY_ID);
        try {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("can't delete tariff");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Tariff> getTariffsByAccountId(long id) throws DAOException {
        //SELECT tariffs.id as id, name, description, speed, price
        // FROM tariffs JOIN tariff_notes ON tariffs.id = tariff_notes.tariff_id
        // WHERE tariff_notes.account_id = ?;
        List<Tariff> tariffs = new ArrayList<>();
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_FROM_TO);
        try {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tariff tariff = converter.convertTariffFromResultSet(resultSet);
                tariffs.add(tariff);
            }
        } catch (SQLException e) {
            logger.error(" can't get all tariffs");
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public List<Tariff> getTariffRange(int firstPosition, int limit) throws DAOException {
        List<Tariff> tariffs = new ArrayList<>();
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_FROM_TO);
        try {
            statement.setInt(1, limit);
            statement.setInt(2, firstPosition);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tariff tariff = converter.convertTariffFromResultSet(resultSet);
                tariffs.add(tariff);
            }
        } catch (SQLException e) {
            logger.error(" can't get all tariffs");
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public List<TariffNote> getUsersTariffsWithAdditionalInfoByAccountId(long accountId)
            throws DAOException {
        //SELECT name, price, discount, date_start, date_end, connection_date, FROM tariffs
// JOIN tariff_notes ON tariff_notes.tariff_id=tariffs.id
//JOIN promotions ON tariffs.id = promotions.id WHERE tariff_notes.account_id=?;
        List<TariffNote> tariffs = new ArrayList<>();
        PreparedStatement statement = preparedStatementMap.get(GET_USERS_TARIFFS_WITH_INFO);
        try {
            statement.setLong(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tariff tariff = converter.convertTariffFromResultSet(resultSet);
                Promotion promotion = converter.convertPromotionFromResultSet(resultSet);
                Note note = converter.convertNoteFromResultSet(resultSet);
                TariffNote tariffNote = new TariffNote(tariff, promotion, note);
                tariffs.add(tariffNote);
            }
        } catch (SQLException e) {
            logger.error(e);
            logger.error("Impossible to get data about users tariffs");
            throw new DAOException(e);
        }
        return tariffs;
    }

    @Override
    public int getTariffsQuantity() throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFFS_NUMBER);
        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }


    }

    @Override
    public int getTariffNotesQuantity() throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_NOTES_NUMBER);
        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public int getTariffNotesQuantityByTariffId(long id) throws DAOException {
        PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_NOTES_NUMBER_BY_TARIFF_ID);
        try {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }

    }
}