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
/**
 * сделать удаление общее для админа и юзера????
 * */
public class DeleteUserCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();

    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String DEL_USER = "deleteUserMessage";
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String PAGE = "userNumPage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int userId = Integer.parseInt(request.getParameter(ID));
        int userNumPage = Integer.parseInt(request.getParameter(PAGE));
        String goToPage;

        try {
            if (admin != null && accountService.checkRoleByUserId(admin.getId()).equals(Role.ADMIN)) {
                if (userService.deleteUser(userId)) {
                    request.setAttribute(DEL_USER, "User was deleted!");
                } else {
                    request.setAttribute(DEL_USER, "User was not deleted!");
                }
                goToPage = "controller?command=show_users&userNumPage=" + userNumPage;
            } else {
                goToPage = JSPPageName.ERROR_PAGE;
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action!");
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            goToPage = JSPPageName.ERROR_PAGE;
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }

        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
