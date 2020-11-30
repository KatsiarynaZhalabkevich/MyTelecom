package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.controller.util.Pagination;
import by.epam.zhalabkevich.my_telecom.service.*;
import by.epam.zhalabkevich.my_telecom.service.util.Service;
import by.epam.zhalabkevich.my_telecom.tag.JSPListBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowTariffsCommand implements Command {

    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final TariffService tariffService = provider.getTariffService();
    private final UserService userService = provider.getUserService();
    private final AccountService accountService = provider.getAccountService();
    private final static String USER = "user";
    private final static String TARIFFS = "tariffs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM = "tariffNumPage";
    private final static String IS_LAST_PAGE = "isLastPageTariff";
    private final static int LIMIT = 3;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        int  page = request.getAttribute(PAGE_NUM) != null ? Integer.parseInt(request.getParameter(PAGE_NUM)) : 1;
        request.setAttribute(PAGE_NUM, page);
        String goToPage = JSPPageName.ERROR_PAGE;
        List<Tariff> tariffList;

        logger.info(request.getPathInfo());
        logger.info(request.getServletPath());
        logger.info(request.getRequestURL());

        try {
            tariffList = (List<Tariff>) Pagination.makePage(Service.TARIFF, page, LIMIT);
            request.setAttribute(IS_LAST_PAGE, tariffList.size() < LIMIT);

            if (admin == null || !Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {
                //это для собственного тега
                JSPListBean jspListBean = new JSPListBean(tariffList);
                session.setAttribute("userbean", jspListBean);
                goToPage = JSPPageName.TARIFF_PAGE;
            } else {
                goToPage = JSPPageName.TARIFF_ADMIN_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        request.getRequestDispatcher(goToPage);
    }

}
