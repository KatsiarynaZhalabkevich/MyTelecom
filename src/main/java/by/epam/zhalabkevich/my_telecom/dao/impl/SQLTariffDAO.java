package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.TariffDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
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

    private final static String UPD_TARIFF = "UPDATE  tariffs SET name=?, description=?, price=?, speed=? WHERE id=?;";
    private final static String ADD_TARIFF = "INSERT INTO tariffs (name, description, price, speed, discount) values (?,?,?,?,?);";
    private final static String GET_TARIFF_BY_ID = "SELECT name, price, speed, description  FROM tariffs WHERE id=?;";
    private final static String DEL_TARIFF_BY_ID = "DELETE  FROM tariffs WHERE id=?;";
    private final static String GET_TARIFF_BY_USER_ID = "SELECT tariffs.id as id, name, description, speed, price FROM tariffs JOIN tariff_notes ON tariffs.id = tariff_notes.tariff_id WHERE tariff_notes.account_id = ?;";
    private final static String GET_TARIFF_FROM_TO = "SELECT name, price, speed, description FROM tariffs LIMIT ? OFFSET ?;";
    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();
//"SELECT tariffs.name, tariffs.price, tariffs.speed, tariffs.description, mytelecom.tarif_note.create_time,mytelecom.tarif_note.id AS 'noteId'
// FROM mytelecom.tarif, mytelecom.tarif_note WHERE mytelecom.tarif_note.tarif_id=mytelecom.tarif.id AND mytelecom.tarif_note.user_id=?;";
//
    public SQLTariffDAO() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, UPD_TARIFF);
            setPreparedStatement(connection, ADD_TARIFF);
            setPreparedStatement(connection, GET_TARIFF_BY_ID);
            setPreparedStatement(connection, DEL_TARIFF_BY_ID);
            setPreparedStatement(connection, GET_TARIFF_BY_USER_ID);
            setPreparedStatement(connection, GET_TARIFF_FROM_TO);

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

    private Tariff convertFromResultSet(ResultSet resultSet) throws DAOException {
        Tariff tariff = new Tariff();
        try {
            tariff.setId(resultSet.getLong(QueryParameter.ID));
            tariff.setName(resultSet.getString(QueryParameter.NAME));
            tariff.setDescription(resultSet.getString(QueryParameter.DESCRIPTION));
            tariff.setPrice(resultSet.getBigDecimal(QueryParameter.PRICE));
            tariff.setSpeed(resultSet.getInt(QueryParameter.SPEED));
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return tariff;
    }

    @Override
    public Tariff addTariff(Tariff tariff) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_TARIFF);
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
        try {
            PreparedStatement statement = preparedStatementMap.get(UPD_TARIFF);
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
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tariff = convertFromResultSet(resultSet);
            }
            return tariff;
        } catch (SQLException e) {
            logger.error("Can't get tariff by Id");
            throw new DAOException(e);
        }

    }

    @Override
    public boolean deleteTariffById(long id) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(DEL_TARIFF_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("can't delete tariff");
            throw new DAOException(e);
        }
    }

    @Override //Проблема не получим время подключения, но тогда надо возвращать DTO  или брать 2 запроса, что плохо
    public List<Tariff> getTariffsByUserId(long id) throws DAOException {
        //SELECT tariffs.id as id, name, description, speed, price
       // FROM tariffs JOIN tariff_notes ON tariffs.id = tariff_notes.tariff_id WHERE tariff_notes.account_id = ?;
        List<Tariff> tariffs = new ArrayList<>();
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_FROM_TO);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tariff tariff = convertFromResultSet(resultSet);
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
        try {
            PreparedStatement statement = preparedStatementMap.get(GET_TARIFF_FROM_TO);
            statement.setInt(1, limit);
            statement.setInt(2, firstPosition);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tariff tariff = convertFromResultSet(resultSet);
                tariffs.add(tariff);
            }
        } catch (SQLException e) {
            logger.error(" can't get all tariffs");
            throw new DAOException(e);
        }
        return tariffs;
    }
}
