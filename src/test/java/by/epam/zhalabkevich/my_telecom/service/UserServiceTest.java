package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private final static Logger logger = LogManager.getLogger();
    private final UserService service = ServiceProvider.getInstance().getUserService();
    private final static User user = new User();
    private final static AuthorizationInfo info = new AuthorizationInfo();

    @BeforeAll
    public static void init() {
        user.setName("Ivan");
        user.setSurname("Ivanov");
        user.setAddress("Minsk 15-50");
        user.setEmail("kate_0510@tut.by");
        user.setPhone("+375(29)690-26-20");
        info.setLogin("kate05100");
        info.setPassword("123456");
    }

    @Test
    public void registerTest() throws ServiceException {
        User userFromDB = service.register(info, user);
        Assertions.assertEquals(user.getName(), userFromDB.getName());
    }

}
