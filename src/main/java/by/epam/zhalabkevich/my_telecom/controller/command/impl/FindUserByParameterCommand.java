package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount;
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
import java.util.List;

public class FindUserByParameterCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String ADDRESS = "address";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "You have no permission for this action!";
    private final static String PAGE_NUM = "userNumPage";
    private final static String IS_LAST_PAGE = "isLastPageUser";
    private final static int LIMIT = 3;
    private static final String USERS_ACCOUNTS = "usersAccounts";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String name = request.getParameter(NAME).trim();
        String surname = request.getParameter(SURNAME).trim();
        String phone = request.getParameter(PHONE).trim();
        String email = request.getParameter(EMAIL).trim();
        String address = request.getParameter(ADDRESS).trim();
        User user = new User(name, surname, phone, email, address);
        String goToPage;
        logger.debug(user);
        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                List<UserAccount> users = userService.findUsersByParameters(user);
                if(users.size()<=0){
                    request.setAttribute(ERROR_MESSAGE, "No users matching parameters of searching!");
                }else {
                    request.setAttribute(USERS_ACCOUNTS, users);
                }
                request.setAttribute(PAGE_NUM, 0);
                request.setAttribute(IS_LAST_PAGE, false);

                goToPage=JSPPageName.SHOW_USERS_PAGE;
            } else {
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.ERROR_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            goToPage=JSPPageName.ERROR_PAGE;
        }
       request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
