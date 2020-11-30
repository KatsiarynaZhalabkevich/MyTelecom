package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
    /*перенести все в один класс???*/
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String USER = "user";
    private final static String TARIFFS = "tariffs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTariffs";
    private final static String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
    private final static String LOGIN_ERROR_MESSAGE_TEXT = "Wrong login and/or password!";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about tariffs!";


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
            System.out.println("user service " + userService);
            User user = userService.authorize(info);
            if (!user.equals(new User())) {
                session.setAttribute(USER, user);
                //TODO добавить тарифы и продумать пагинацию
                Pagination.makeTariffPage(request);
                goToPage = Role.ADMIN.equals(accountService.checkRoleByUserId(user.getId())) ? JSPPageName.ADMIN_PAGE : JSPPageName.USER_AUTH_PAGE;
            } else {
                session.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT); //request
                goToPage = JSPPageName.INDEX_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        request.getRequestDispatcher(goToPage).forward(request, response);

    }

}
