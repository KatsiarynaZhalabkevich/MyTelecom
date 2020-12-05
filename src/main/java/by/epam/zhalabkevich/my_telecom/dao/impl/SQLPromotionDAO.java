package by.epam.zhalabkevich.my_telecom.dao.impl;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.dao.DAOException;
import by.epam.zhalabkevich.my_telecom.dao.PromotionDAO;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPoolException;
import by.epam.zhalabkevich.my_telecom.dao.util.QueryParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLPromotionDAO implements PromotionDAO {
    private final static Logger logger = LogManager.getLogger();
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_PROMO = "INSERT INTO promotions (description, date_start, date_end, discount) VALUE (?, ?, ?, ?);";
    private static final String UPDATE_PROMO = "UPDATE promotions SET description = ?, date_start = ?, date_end = ?, discount = ? WHERE id = ?;";
    private static final String DEL_PROMO_BY_ID = "DELETE FROM promotions WHERE id = ?;";
    private static final String ADD_PROMO_TO_TARIFF ="UPDATE tariffs SET promotion_id = ? WHERE id = ?;" ;
    private static final String GET_PROMOTIONS_FROM_TO = "SELECT id, description, date_start, date_end, discount  FROM promotions LIMIT ? OFFSET ?;";


    private final Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();

    public SQLPromotionDAO() {
        Connection connection;
        try {
            connection = connectionPool.takeConnection();
            setPreparedStatement(connection, ADD_PROMO);
            setPreparedStatement(connection, UPDATE_PROMO);
            setPreparedStatement(connection, DEL_PROMO_BY_ID);
            setPreparedStatement(connection, ADD_PROMO_TO_TARIFF);
            setPreparedStatement(connection, GET_PROMOTIONS_FROM_TO);

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
            logger.error("Impossible add promotion");
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
            logger.error("Impossible update promotion");
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
            logger.error("Impossible delete promotion");
            throw new DAOException(e);
        }
    }

    @Override
    public int addPromotionToTariffById(long tariffId, long promotionId) throws DAOException {
        //UPDATE tariffs SET promotion_id = ? WHERE id = ?;
        try {
            PreparedStatement statement = preparedStatementMap.get(ADD_PROMO_TO_TARIFF);
            statement.setLong(1, promotionId);
            statement.setLong(2, tariffId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Impossible set promotion to tariff");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Promotion> getPromotions(int offset, int limit) throws DAOException {
        //SELECT id, description, date_start, date_end, discount  FROM promotions LIMIT ? OFFSET ?;
        List<Promotion> promotions = new ArrayList<>();
        PreparedStatement statement = preparedStatementMap.get(GET_PROMOTIONS_FROM_TO);
        try {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(QueryParameter.ID);
                String description = resultSet.getString(QueryParameter.DESCRIPTION);
                LocalDate dateStart = LocalDate.parse(resultSet.getString(QueryParameter.DATE_START));
                LocalDate dateEnd = LocalDate.parse(resultSet.getString(QueryParameter.DATE_END));
                double discount = resultSet.getDouble(QueryParameter.DISCOUNT);

                promotions.add(new Promotion(id, description, dateStart, dateEnd, discount));
            }
        } catch (SQLException e) {
            logger.error("Impossible get promotions list");
            throw new DAOException(e);
        }
        return promotions;
    }
}
