package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.PromotionDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLPromotionDAO implements PromotionDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_PROMO = "INSERT INTO promotions (description, date_start, date_end, discount) VALUE (?, ?, ?, ?);";
    private static final String UPDATE_PROMO = "UPDATE promotions SET description = ?, date_start = ?, date_end = ?, discount = ? WHERE = ?;";
    private static final String DEL_PROMO_BY_ID = "DELETE FROM promotions WHERE id = ?;";


    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLPromotionDAO() {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, ADD_PROMO);
            setPreparedStatement(connection, UPDATE_PROMO);
            setPreparedStatement(connection, DEL_PROMO_BY_ID);
//            setPreparedStatement(connection, "");
//            setPreparedStatement(connection, "");
//            setPreparedStatement(connection, "");
//            setPreparedStatement(connection, "");
//            setPreparedStatement(connection, "");

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
    public Promotion addPromotion(Promotion promotion) throws DAOException {
        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_PROMO);
            statement.setString(1, promotion.getDescription());
            statement.setDate(2, Date.valueOf(String.valueOf(promotion.getDateStart())));
            statement.setDate(3, Date.valueOf(String.valueOf(promotion.getDateEnd())));
            statement.setDouble(4, promotion.getDiscount());
            if (statement.executeUpdate() == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                promotion.setId(resultSet.getLong(1)); //это значение присвоится в параметр пользователю
            }
            return promotion;
        } catch (SQLException e) {
            logger.error("Can't add promotion");
            throw new DAOException(e);
        }
    }

    @Override
    public int updatePromotion(Promotion promotion) throws DAOException {
        //"UPDATE promotions SET description = ?, date_start = ?, date_end = ?, discount = ? WHERE = ?;"
        try {
            PreparedStatement statement = preparedStatementMap.get(UPDATE_PROMO);
            statement.setString(1, promotion.getDescription());
            statement.setDate(2, Date.valueOf(String.valueOf(promotion.getDateStart())));
            statement.setDate(3, Date.valueOf(String.valueOf(promotion.getDateEnd())));
            statement.setDouble(4, promotion.getDiscount());
            statement.setLong(5, promotion.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add promotion");
            throw new DAOException(e);
        }
    }

    @Override
    public int deletePromotionById(long id) throws DAOException {
        //DELETE FROM promotions WHERE id = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(DEL_PROMO_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("can't delete tariff");
            throw new DAOException(e);
        }
    }
}
