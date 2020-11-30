package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.AuthorizationInfo;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.TariffService;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class RegistrationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();

    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String ADDRESS = "email";
    private final static String TARIFFS = "tariffs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_LOGIN_MESSAGE = "errorLoginMessage";
    private final static String ERROR_PASSWORD_MESSAGE = "errorPasswordMessage";
    private final static String ERROR_MESSAGE_TEXT_EMPTY = "Some fields are empty!";
    private final static String ERROR_MESSAGE_TEXT = "Can't register a new user. Please, try again.";
    private final static String ERROR_LOGIN_MESSAGE_TEXT = "Login already exist!";
    private final static String ERROR_PASSWORD_MESSAGE_TEXT = "Passwords are not equal!!!";
//TODO send email to activate account
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String phone = request.getParameter(PHONE);
        String email = request.getParameter(EMAIL);
        String address = request.getParameter(ADDRESS);
        String login = request.getParameter(LOGIN);
        String password1 = request.getParameter(PASSWORD + 1);
        String password2 = request.getParameter(PASSWORD + 2);
        String goToPage;

        AuthorizationInfo info = new AuthorizationInfo(login, password1);
        User user = new User(name, surname, phone, email, address);

        if (!password1.equals("") && !password1.equals(" ") && password1.equals(password2)) {
            try {
                User userFromDB = userService.register(info, user);
                //TODO jsp pagination
                Pagination.makeTariffPage(request);
                session.setAttribute(USER, userFromDB);
                goToPage = JSPPageName.USER_AUTH_PAGE;
            } catch (ServiceException e) { //из сервисов приходят понятные сообщения
                logger.error(e);
                session.setAttribute(ERROR_MESSAGE, e); //сообщения на 1 раз записывать в request
                session.setAttribute(USER, user);
                goToPage = JSPPageName.USER_REG_PAGE;
            }
        } else {
            request.setAttribute(ERROR_PASSWORD_MESSAGE, ERROR_PASSWORD_MESSAGE_TEXT);
            session.setAttribute(USER, user);
            goToPage = JSPPageName.USER_REG_PAGE;
        }
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
