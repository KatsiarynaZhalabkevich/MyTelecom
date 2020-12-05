package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.*;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import by.epam.zhalabkevich.my_telecom.tag.JSPListBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUsersCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final TariffService tariffService = provider.getTariffService();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final static String USER = "user";
    private static final String USERS = "users";
    private final static String TARIFFS = "tariffs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM = "userNumPage";
    private final static String IS_LAST_PAGE = "isLastPageUser";
    private final static int LIMIT = 3;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int page = request.getParameter(PAGE_NUM) != null ? Integer.parseInt(request.getParameter(PAGE_NUM)) : 1;
        request.setAttribute(PAGE_NUM, page);
        String goToPage = JSPPageName.ERROR_PAGE;
        List<User> usersList;


        try {
            usersList = (List<User>) Pagination.makePage(Service.USER, page, LIMIT);
            request.setAttribute(IS_LAST_PAGE, usersList.size() < LIMIT);
           logger.debug(usersList.size());

            request.setAttribute(USERS, usersList);
            if (Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                goToPage = JSPPageName.SHOW_USERS_PAGE;
            } else {
                goToPage = JSPPageName.ERROR_PAGE;
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action!");
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        logger.debug(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
