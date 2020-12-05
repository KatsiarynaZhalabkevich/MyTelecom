package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.*;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.TariffService;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final static String ACCOUNT = "account";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_LOGIN_MESSAGE = "errorLoginMessage";
    private final static String ERROR_PASSWORD_MESSAGE = "errorPasswordMessage";
    private final static String ERROR_MESSAGE_TEXT_EMPTY = "Some fields are empty!";
    private final static String ERROR_MESSAGE_TEXT = "Can't register a new user. Please, try again.";
    private final static String ERROR_LOGIN_MESSAGE_TEXT = "Login already exist!";
    private final static String ERROR_PASSWORD_MESSAGE_TEXT = "Passwords are not equal!!!";
    private final static int LIMIT = 3;

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
        String goToPage = JSPPageName.ERROR_PAGE;

        User user = new User(name, surname, phone, email, address);

        if (!password1.equals("") && !password1.equals(" ") && password1.equals(password2)) {
            AuthorizationInfo info = new AuthorizationInfo(login, password1);
            try {
                User userFromDB = userService.register(info, user);
                if(userFromDB.getId()>0){
                    session.setAttribute(USER, userFromDB);
                    goToPage = "controller?command=show_user_info";
                }
                //send mail message in future
            } catch (ServiceException e) {
                logger.error(e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                request.setAttribute(USER, user);
                goToPage = JSPPageName.USER_REG_PAGE;
            }
        } else {
            request.setAttribute(ERROR_PASSWORD_MESSAGE, ERROR_PASSWORD_MESSAGE_TEXT);
            request.setAttribute(USER, user);
            goToPage = JSPPageName.USER_REG_PAGE;
        }
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
