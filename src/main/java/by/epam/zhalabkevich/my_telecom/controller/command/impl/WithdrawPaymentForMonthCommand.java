package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
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

public class WithdrawPaymentForMonthCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final TariffService tariffService = provider.getTariffService();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String PAYMENT_MESSAGE = "paymentMessage";
    private final static String USERS_LIST = "usersList";
    private final static String PAYMENT_MESSAGE_TEXT = "Payment OK";
    private final static String ERROR_MESSAGE_TEXT_PAY = "Can't get users for payment!";
    private final static String ERROR_MESSAGE_TEXT = "Session is not valid!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.SHOW_USERS_PAGE;
        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                accountService.withdrawPaymentForMonth();
                goToPage = "controller?command=show_users";
                request.setAttribute(PAYMENT_MESSAGE, "Payment was successfully withdraw!");
            } else {
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action!");
                goToPage = JSPPageName.ERROR_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }

        request.getRequestDispatcher(goToPage).forward(request, response);
    }

}



