package by.epam.zhalabkevich.my_telecom.dao;

import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class UserDAOTest {
    public static final Logger logger = LogManager.getLogger();
    @BeforeAll
    void initConnectionPool(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        try (Connection con = connectionPool.takeConnection(); PreparedStatement ps = con.prepareStatement(TestDB.RECREATE_DB)) {
//            ps.executeUpdate();
//            logger.debug("Test data base recreated.");
//        } catch (SQLException e) {
//            logger.debug("Failed to recreate test data base.", e);
//        }
    }

    @AfterAll
    void destroyConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
        logger.debug("Test pool destroyed");
    }

}
