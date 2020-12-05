package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;
import by.epam.zhalabkevich.my_telecom.dao.pool.ConnectionPool;
import by.epam.zhalabkevich.my_telecom.service.util.TestDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TariffServiceTest {
    private final static Logger logger = LogManager.getLogger();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private static Tariff tariff;

    @BeforeAll
    static void createTariffForTest() {
        String name = "Test";
        BigDecimal price = BigDecimal.valueOf(60);
        int speed = 300;
        String description = "Tariff for test run";
        tariff = new Tariff(name, price, speed, description);
    }

    //    @BeforeAll
//    static void initDB(){
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        try (Connection con = connectionPool.takeConnection();
//             PreparedStatement ps = con.prepareStatement(TestDB.RECREATE_DB)) {
//            ps.executeUpdate();
//            logger.debug("Test data base recreated.");
//        } catch (SQLException e) {
//            logger.debug("Failed to recreate test data base.", e);
//        }
//    }
    static void destroyDB() {

    }

    @Test
    public void showTariffRangeTest() throws ServiceException {
        int limit = 5;
        int page = 0;
        List<Tariff> tariffs = tariffService.showTariffRange(page, limit);
        for (Tariff t : tariffs) {
            System.out.println(t);
        }
        Assertions.assertEquals(5, tariffs.size());

    }

    @Test
    public void getUserTariffsWithAdditionalInfoTest() throws ServiceException {
        long id = 6;
        List<TariffNote> notes = tariffService.showUserTariffsByAccountId(id);
        System.out.println(notes.get(0));
        Assertions.assertEquals(1, notes.size());
    }

}
