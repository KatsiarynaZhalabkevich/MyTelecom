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

public class AddPromotionToTariffCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final PromotionService promotionService = provider.getPromotionService();
    private final AccountService accountService = provider.getAccountService();
    private final static String PROMO_ID = "promoId";
    private final static String TARIFF_ID = "tariffId";
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        long promotionId = (long) request.getAttribute(PROMO_ID);
        long tariffId = (long) request.getAttribute(TARIFF_ID);
        String goToPage = JSPPageName.ERROR_PAGE;

        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                if (promotionService.addPromotionToTariff(tariffId, promotionId) == 1) {
                    request.setAttribute("updMessage", "Promotion was added to tariff!");
                    goToPage = JSPPageName.TARIFF_ADMIN_PAGE;
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
