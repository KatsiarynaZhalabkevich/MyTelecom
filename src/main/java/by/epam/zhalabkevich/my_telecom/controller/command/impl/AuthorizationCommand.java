package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.*;
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
import java.util.List;
//TODO у авторизованного пользователя могут быть тарифа, Это у зарегистрированного не могут быть!!!
public class AuthorizationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
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


    /**
     * Метод для авторизации пользователя. Включает в себя получение списка тарифов пользователя
     * и всех тарифов для отображения на странице пользователя
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String goToPage = JSPPageName.INDEX_PAGE;
        AuthorizationInfo info = new AuthorizationInfo(login, password);
        HttpSession session = request.getSession(true);

        try {
            User user = userService.authorize(info);
            if (user.getId() != 0) {
                session.setAttribute(USER, user);
                Account account = accountService.getAccountByUserId(user.getId());
                request.setAttribute(ACCOUNT, account);
//                List<Tariff> tariffList = (List<Tariff>) Pagination.makePage(Service.TARIFF, 1, LIMIT);
//                request.setAttribute(TARIFF_PAGE_NUM, 1);
//                request.setAttribute(TARIFFS, tariffList);
                goToPage = Role.ADMIN.equals(account.getRole())
                        ? JSPPageName.ADMIN_PAGE : "controller?command=show_user_info";
            } else {
                request.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.INDEX_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        logger.debug(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);
       // response.sendRedirect();
    }

}
