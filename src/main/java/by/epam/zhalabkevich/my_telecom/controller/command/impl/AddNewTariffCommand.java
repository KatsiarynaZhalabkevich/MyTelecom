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

public class AddNewTariffCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final TariffService tariffService = ServiceProvider.getInstance().getTariffService();
    private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String SPEED = "speed";
    private final static String PRICE = "price";
    private final static String ADD_MESSAGE = "addMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "You haven't permission for this action!";
    private final static String ADD_MESSAGE_OK = "New Tariff added!";
    private final static String ADD_MESSAGE_NOT = "Tariff not added!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage;

        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        int speed = Integer.parseInt(request.getParameter(SPEED));
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(request.getParameter(PRICE)));


        Tariff tariff = new Tariff();
        tariff.setSpeed(speed);
        tariff.setPrice(price);
        tariff.setDescription(description);
        tariff.setName(name);
        try {
            if (admin != null && Role.ADMIN.equals(accountService.checkRoleByUserId(admin.getId()))) {

                Tariff tariffFromDB = tariffService.addTariff(tariff);
                if (tariffFromDB.getId() > 0) {
                    request.setAttribute(ADD_MESSAGE, ADD_MESSAGE_OK);
                } else {
                    request.setAttribute(ADD_MESSAGE, ADD_MESSAGE_NOT);
                }
                //+номер страницы тот же оставить
                goToPage = "controller?command=show_tariffs";
            } else {
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.ERROR_PAGE;
            }

        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            goToPage = JSPPageName.ERROR_PAGE;
        }

     //  request.getRequestDispatcher(goToPage).forward(request, response);
        response.sendRedirect(goToPage);
    }
}
