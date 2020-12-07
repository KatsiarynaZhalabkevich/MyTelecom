package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Status;
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
import java.math.BigDecimal;

/**
 * как обновлять баланс? зачислять ту сумму, которая вписана или как?
 */
public class ChangeUserBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();

    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_BALANCE = "updateBalanceMessage";
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String PAGE = "userNumPage";
    private final static String PREV_BALANCE = "old_balance";
    private final static String SUM_TO_ADD = "balance";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int userId = Integer.parseInt(request.getParameter(ID));
        int userNumPage = Integer.parseInt(request.getParameter(PAGE));
        BigDecimal prevBalance = BigDecimal.valueOf(Double.parseDouble(request.getParameter(PREV_BALANCE)));
        BigDecimal sumToAdd = BigDecimal.valueOf(Double.parseDouble(request.getParameter(SUM_TO_ADD)));

        String goToPage;
        try {
            if (admin != null && accountService.checkRoleByUserId(admin.getId()).equals(Role.ADMIN)) {
                if (accountService.updateBalanceByUserId(userId, prevBalance.add(sumToAdd))) {
                    request.setAttribute(UPD_BALANCE, "Balance was updated!");
                } else {
                    request.setAttribute(UPD_BALANCE, "Balance was not updated");
                }
                goToPage = "controller?command=show_users&userNumPage=" + userNumPage;

            } else {
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action");
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
