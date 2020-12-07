package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.bean.dto.TariffNote;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowUserTariffsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final TariffService tariffService = provider.getTariffService();

    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER = "user";
    private final static String ID = "account_id";
    private static final String USER_TARIFFS = "userTariffs";
    private final static String ERROR_MESSAGE_TEXT = "You have no permission for this action!";
    private final static String PAGE = "userNumPage";
    private final static String USER_ID = "user_id";
    private final static String USER_NAME = "userName";

    /**
     * account id может быть неправильный!
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int accountId = Integer.parseInt(request.getParameter(ID));
        int userNumPage = Integer.parseInt(request.getParameter(PAGE));
        int userId = Integer.parseInt(request.getParameter(USER_ID));
        String goToPage;

        try {
            if (admin != null && accountService.checkRoleByUserId(admin.getId()).equals(Role.ADMIN)) {
                TariffNote notes = (TariffNote) tariffService.showUserTariffsByAccountId(accountId);
                User user = userService.getUserById(userId);
                //notes не много, так что пагинации нету
                request.setAttribute(USER_TARIFFS, notes);
                request.setAttribute(PAGE, userNumPage);
                request.setAttribute(USER_NAME, user);
                goToPage = JSPPageName.SHOW_USERS_TARIFFS_PAGE;
            } else {
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.ERROR_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            goToPage = JSPPageName.ERROR_PAGE;
        }
        request.getRequestDispatcher(goToPage).forward(request, response);

    }
}
