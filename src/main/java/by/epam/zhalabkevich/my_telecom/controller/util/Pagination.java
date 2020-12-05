package by.epam.zhalabkevich.my_telecom.controller.util;

import by.epam.zhalabkevich.my_telecom.bean.Promotion;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.dao.DAOProvider;
import by.epam.zhalabkevich.my_telecom.service.*;
import by.epam.zhalabkevich.my_telecom.service.impl.TariffServiceImpl;
import by.epam.zhalabkevich.my_telecom.service.util.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private final static ServiceProvider provider = ServiceProvider.getInstance();
    private final static String USER = "user";
    private final static String TARIFFS = "tariffs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM = "pageNum";
    private final static String IS_LAST_PAGE = "isLastPage";
    private final static int LIMIT = 3;
    private static final String USERS = "users";

    //TODO переписать методы в один через <>
    public static List<?> makePage(Service service, int page, int limit) throws ServiceException {
        List<?> list = new ArrayList<>();
        int offset = page*limit - limit;

        if (service.equals(Service.TARIFF)) {
            TariffService tariffService = provider.getTariffService();
            list = tariffService.showTariffRange(offset, limit);
        } else if (service.equals(Service.PROMOTION)) {
            PromotionService promotionService = provider.getPromotionService();
            list = promotionService.getPromotions(offset, limit);
        } else if (service.equals(Service.USER)) {
            UserService userService = provider.getUserService();
            list = userService.getUsersRange(offset, limit);
        }
        return list;
    }

    public static List<Tariff> makeTariffPage(HttpServletRequest request) throws ServiceException {
        TariffService service = provider.getTariffService();
        List<Tariff> tariffList;
        HttpSession session = request.getSession();

        int page;
        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
        } else {
            page = 1;
        }
        session.setAttribute(PAGE_NUM, page);
        tariffList = service.showTariffRange(page, LIMIT);
        if (tariffList != null) {
            //session.setAttribute(TARIFFS, tariffList);
            if (tariffList.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return tariffList;
    }

    //что показывать про User? Может ифа по аккаунту больше интересует?
    public static List<User> makeUserPage(HttpServletRequest request) throws ServiceException {
        UserService service = provider.getUserService();
        List<User> users;
        HttpSession session = request.getSession();
        int page;
        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
        } else {
            page = 1;
        }
        session.setAttribute(PAGE_NUM, page);
        users = service.getUsersRange(page, LIMIT);
        if (users != null) {
            // session.setAttribute(USERS, users);
            if (users.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return users;
    }

    public static List<Promotion> makePromotionPage(HttpServletRequest request) throws ServiceException {
        PromotionService service = provider.getPromotionService();
        List<Promotion> promotions;
        HttpSession session = request.getSession();
        int page;
        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
        } else {
            page = 1;
        }
        session.setAttribute(PAGE_NUM, page);
        promotions = service.getPromotions(page, LIMIT);
        if (promotions != null) {
            // session.setAttribute(USERS, users);
            if (promotions.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return promotions;
    }

}
