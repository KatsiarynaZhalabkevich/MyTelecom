package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
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
import java.time.LocalDateTime;

public class CreatePromotionCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final PromotionService promotionService = provider.getPromotionService();
    private final AccountService accountService = provider.getAccountService();

    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private static final String DESCRIPTION = "description";
    private static final String DATE_START = "dateStart";
    private static final String DATE_END = "dateEnd";
    private static final String DISCOUNT = "discount";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String description = request.getAttribute(DESCRIPTION).toString();
        LocalDateTime dateStart = (LocalDateTime) request.getAttribute(DATE_START);
        LocalDateTime dateEnd = (LocalDateTime) request.getAttribute(DATE_END);
        double discount = (double) request.getAttribute(DISCOUNT);
        Promotion promotion = new Promotion(-1, description, dateStart, dateEnd, discount);
        String goToPage = JSPPageName.ERROR_PAGE;

        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                promotion = promotionService.createPromotion(promotion);
                if (!promotion.equals(new Promotion())) {
                    request.setAttribute("updMessage", "Promotion was created!");
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
