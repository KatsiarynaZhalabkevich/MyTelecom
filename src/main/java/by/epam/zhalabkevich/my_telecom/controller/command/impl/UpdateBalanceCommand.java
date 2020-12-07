package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
/*
* Method for user
* */
public class UpdateBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final static String USER = "user";
    private static final String ACCOUNT = "account";
    private final static String BALANCE = "balance";
    private final static String UPD_BALANCE_MESS = "updBalanceMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_BALANCE_MESS_OK = "Balance updated!";
    private final static String UPD_BALANCE_MESS_NOT = "Can't upd user balance";
    private final static String ERROR_MESSAGE_TEXT = "Can't upd user balance. Please, sign in and try again";
    private static final String TARIFFS = "tariffs";
    private static final int LIMIT = 3;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.USER_AUTH_PAGE;
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(request.getParameter(BALANCE)));
        System.out.println(BALANCE+balance);
        if (user != null) {
            try {
                Account account = accountService.getAccountByUserId(user.getId());
                account.setBalance(account.getBalance().add(balance));
                if (accountService.updateBalanceByUserId(user.getId(), account.getBalance())) {
                    request.setAttribute(ACCOUNT, accountService.getAccountByUserId(user.getId()));
                    List<Tariff> tariffList = (List<Tariff>) Pagination.makePage(Service.TARIFF, 1, LIMIT);
                    request.setAttribute(TARIFFS, tariffList);
                    request.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_OK);
                } else {
                    request.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_NOT);
                }
                request.setAttribute(TARIFFS, Pagination.makePage(Service.TARIFF, 1, LIMIT));
            } catch (ServiceException e) {
                logger.error(e);
                session.setAttribute(ERROR_MESSAGE, e.getMessage());
            }
        } else {
            goToPage = JSPPageName.ERROR_PAGE;
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        //добвить message
        response.sendRedirect("controller?command=show_user_info");
    }
}