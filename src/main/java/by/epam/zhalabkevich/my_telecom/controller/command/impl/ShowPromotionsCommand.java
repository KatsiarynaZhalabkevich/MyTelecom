package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.PromotionService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowPromotionsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final PromotionService promotionService = provider.getPromotionService();
    private final AccountService accountService = provider.getAccountService();
    private final static String PROMO_ID = "promoId";
    private final static String TARIFF_ID = "tariffId";
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
//переделать на новый лад!!!!
    //кто угодно может посмотреть  промоакции
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) request.getAttribute(USER);
        String goToPage;

        try {
            List<Promotion> promotions = (List<Promotion>) Pagination.makePage(Service.PROMOTION,1, 3);
            request.setAttribute("promotionsList", promotions);
            if (Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                goToPage = JSPPageName.SHOW_PROMO;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}
