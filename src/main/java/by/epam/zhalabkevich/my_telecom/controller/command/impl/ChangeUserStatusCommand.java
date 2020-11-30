package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;
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
import java.util.List;

/*
 *  admin
 */

public class ChangeUserStatusCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();

    private final static String ACTIVE = "active";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_STATUS = "updStatusMessage";
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USERS_LIST = "usersList";
    private final static String UPD_STATUS_OK = "Status updated!";
    private final static String UPD_STATUS_NOT = "Can't upd user status";
    private final static String ERROR_MESSAGE_TEXT = "You have no permission for this action! Please, log in! ";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int userId = Integer.parseInt(request.getParameter(ID));

        String status = request.getParameter(ACTIVE);
        Status statusToChange;

        String goToPage = JSPPageName.SHOW_USERS_PAGE;
        try {
            if (accountService.checkRoleByUserId(admin.getId()).equals(Role.ADMIN)) {
//TODO убедиться, что оправляется нужное значение
                if (Status.valueOf(status) == Status.ACTIVE) {
                    statusToChange = Status.BLOCKED;
                } else {
                    statusToChange = Status.ACTIVE;
                }

                if (accountService.updateStatusByUserId(userId, statusToChange)) {
                    session.setAttribute(UPD_STATUS, UPD_STATUS_OK);
                    List<User> users = Pagination.makeUserPage(request);
                    session.setAttribute(USERS_LIST, users);
                } else {
                    session.setAttribute(UPD_STATUS, UPD_STATUS_NOT);
                }

            } else {
                goToPage = JSPPageName.ERROR_PAGE;
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            }
        } catch (ServiceException e) {
            logger.error(e);
        }
        response.sendRedirect(goToPage); //нужен ли sendRedirect?
    }
}

