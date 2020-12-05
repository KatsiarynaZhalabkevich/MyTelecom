package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountServiceTest {
    private final static Logger logger = LogManager.getLogger();
    private final AccountService service = ServiceProvider.getInstance().getAccountService();

    @Test
    public void getAccountInfoByUserId() throws ServiceException {
        long id = 1;
        Account account = service.getAccountByUserId(id);
        System.out.println(account);
    }

    @Test
    public void updateBalanceByUserId() throws ServiceException {
        BigDecimal updateValue = BigDecimal.valueOf(200);
        long id = 1;
        Account account = service.getAccountByUserId(id);
        BigDecimal accountBalance = account.getBalance();
        BigDecimal newBalance = accountBalance.add(updateValue);
        if (service.updateBalanceByUserId(id, updateValue)) {
           Account account1 = service.getAccountByUserId(id);
           Assertions.assertEquals(newBalance, account1.getBalance());
        }


    }
}
