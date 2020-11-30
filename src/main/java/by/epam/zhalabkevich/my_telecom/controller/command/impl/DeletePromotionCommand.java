package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.PromotionService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeletePromotionCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final PromotionService promotionService = provider.getPromotionService();
    private final AccountService accountService = provider.getAccountService();
    private final static String ID = "id";
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        long promotionId = (long) request.getAttribute(ID);
        String goToPage = JSPPageName.ERROR_PAGE;

        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                if (promotionService.deletePromotionById(promotionId) == 1) {
                    request.setAttribute("updMessage", "Promotion was deleted!");
                    goToPage = JSPPageName.SHOW_PROMO;
                }
            } else {
                request.setAttribute(ERROR_MESSAGE, "You have no permission for this action!");
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
