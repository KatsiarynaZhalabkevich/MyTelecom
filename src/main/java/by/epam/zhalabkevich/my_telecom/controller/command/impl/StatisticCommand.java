package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.*;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final TariffService tariffService = provider.getTariffService();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String PAGE_NUM = "tariffNumPage";
    private final static String IS_LAST_PAGE = "isLastPageTariff";
    private final static String ERROR_MESSAGE_TEXT2 = "You have no permission for this action! Please, log in! ";
    private final static String TARIFFS = "tariffs";
    private final static int LIMIT = 3;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int page = request.getParameter(PAGE_NUM) != null ? Integer.parseInt(request.getParameter(PAGE_NUM)) : 1;
        String goToPage;
        Map<Long, Integer> tariffsCounter = new HashMap<>();

        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {

                int usersCount = userService.getUsersNumber();
                int tariffCount = tariffService.getTariffQuantity();
                int notesCount = tariffService.getTariffNotesQuantity();

                List<Tariff> tariffList = (List<Tariff>) Pagination.makePage(Service.TARIFF, page, LIMIT);

                for (Tariff t : tariffList) {
                    int count = tariffService.getTariffNotesQuantityByTariffId(t.getId());
                    tariffsCounter.put(t.getId(), count);
                }
                request.setAttribute("user_number", usersCount);
                request.setAttribute("tariff_number", tariffCount);
                request.setAttribute("connectionsCount", notesCount);
                request.setAttribute(TARIFFS, tariffList);
                request.setAttribute("tariff_count", tariffsCounter);
                request.setAttribute(IS_LAST_PAGE, tariffList.size() < LIMIT);
                request.setAttribute(PAGE_NUM, page);
                goToPage = JSPPageName.STATISTIC_PAGE;

            } else {
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT2);
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
