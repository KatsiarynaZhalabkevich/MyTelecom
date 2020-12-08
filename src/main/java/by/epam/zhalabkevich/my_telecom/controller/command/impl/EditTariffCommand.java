package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.Tariff;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class EditTariffCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final ServiceProvider provider = ServiceProvider.getInstance();
    private final TariffService tariffService = provider.getTariffService();
    private final AccountService accountService = provider.getAccountService();
    private final static String USER = "user";
    private final static String TARIFF_ID = "tariff_id";
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String SPEED = "speed";
    private final static String PRICE = "price";
    private final static String EDIT_MESSAGE = "editMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String EDIT_MESSAGE_TEXT_OK = "Tariff updated!";
    private final static String EDIT_MESSAGE_TEXT_NOT = "Tariff not updated!";
    private final static String ERROR_MESSAGE_TEXT = "You haven't permission for this action!";
    private final static String TARIFF_NUM_PAGE = "tariffNumPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        long id = Long.parseLong(request.getParameter(TARIFF_ID));
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        int speed = Integer.parseInt(request.getParameter(SPEED).trim());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter(PRICE).trim()));
        int tariffPageNum = Integer.parseInt(request.getParameter(TARIFF_NUM_PAGE));
        String goToPage;
        Tariff tariff = new Tariff();
        tariff.setId(id);
        tariff.setSpeed(speed);
        tariff.setPrice(price);
        tariff.setDescription(description);
        tariff.setName(name);
        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {

                if (tariffService.changeTariff(tariff)) {
                    request.setAttribute(EDIT_MESSAGE, EDIT_MESSAGE_TEXT_OK);
                } else {
                    request.setAttribute(EDIT_MESSAGE, EDIT_MESSAGE_TEXT_NOT);
                }
                goToPage = "controller?command=show_tariffs&tariffNumPage=" + tariffPageNum;

            } else {
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.ERROR_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            goToPage = JSPPageName.ERROR_PAGE;
        }
        response.sendRedirect(goToPage);
    }
}
