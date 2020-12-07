package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
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

public class BlockUsersWithNegativeBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final  AccountService accountService = provider.getAccountService();
    private final  UserService userService = provider.getUserService();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String BLOCK_MESSAGE = "blockMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                int numberOfBlockedUsers = accountService.blockUsersWithNegativeBalance();
                request.setAttribute(BLOCK_MESSAGE, numberOfBlockedUsers + " users were blocked!");
            }else {
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action!");
                goToPage = JSPPageName.ERROR_PAGE;
            }

        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        response.sendRedirect(goToPage);
    }
}
