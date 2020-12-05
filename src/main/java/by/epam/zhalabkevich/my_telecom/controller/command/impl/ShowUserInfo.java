package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.*;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.*;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUserInfo implements Command {
    private final static Logger logger = LogManager.getLogger();
    private static final String IS_LAST_TARIFF_PAGE = "isLastPageTariff";
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    /*перенести все в один класс???*/
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String USER = "user";
    private final static String TARIFFS = "tariffs";
    private static final String ACCOUNT = "account";
    private final static String TARIFF_PAGE_NUM = "tariffNumPage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTariffs";
    private final static String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
    private final static String LOGIN_ERROR_MESSAGE_TEXT = "Wrong login and/or password!";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about tariffs!";
    private final static int LIMIT = 3;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String goToPage = JSPPageName.INDEX_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {

            if (user != null) {
                Account account = accountService.getAccountByUserId(user.getId());
                request.setAttribute(ACCOUNT, account);
                int tariffPage;
                if (request.getParameter(TARIFF_PAGE_NUM) != null) {
                    logger.debug(request.getParameter(TARIFF_PAGE_NUM));
                    tariffPage = Integer.parseInt(request.getParameter(TARIFF_PAGE_NUM));
                } else {
                    tariffPage = 1;
                }
                List<Tariff> tariffList = (List<Tariff>) Pagination.makePage(Service.TARIFF, tariffPage, LIMIT);
                request.setAttribute(TARIFF_PAGE_NUM, tariffPage);
                request.setAttribute(IS_LAST_TARIFF_PAGE, tariffList.size() < LIMIT);
                request.setAttribute(TARIFFS, tariffList);
                request.setAttribute(USER_TARIFFS, tariffService.showUserTariffsByAccountId(account.getId()));
                goToPage = JSPPageName.USER_AUTH_PAGE;
            } else {
                request.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.INDEX_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}

