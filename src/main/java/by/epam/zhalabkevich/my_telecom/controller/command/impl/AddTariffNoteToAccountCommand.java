package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.NoteService;
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

public class AddTariffNoteToAccountCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static ServiceProvider provider = ServiceProvider.getInstance();
    private final NoteService noteService = provider.getNoteService();
    private final static String USER = "user";
    private final static String TARIFF_ID = "tariff_id";
    private final static String ACCOUNT_ID = "account_id";
    private final static String TARIFF_PAGE_NUM = "tariffNumPage";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_MESSAGE = "errorMessage";

    //страницу сохранить про тарифы!!!
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long tariffId = Long.parseLong(request.getParameter(TARIFF_ID));
        long accountId = Long.parseLong(request.getParameter(ACCOUNT_ID));
        String goToPage = JSPPageName.USER_AUTH_PAGE;
        int tariffPage = Integer.parseInt(request.getParameter(TARIFF_PAGE_NUM));
        if (user != null) {
            try {
                if (noteService.addTariffNote(tariffId, accountId)) {
                    request.setAttribute(UPD_MESSAGE, "New tariff is added!");
                } else {
                    request.setAttribute(UPD_MESSAGE, "Can't add tariff to account!");
                }
                goToPage = "controller?command=show_user_info&&" + TARIFF_PAGE_NUM + "=" + tariffPage;
            } catch (ServiceException e) {
                logger.error(e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
            }

        } else {
            request.setAttribute(ERROR_MESSAGE, "Session is expired. Please, sign in!");
            goToPage = JSPPageName.ERROR_PAGE;
        }
        request.getRequestDispatcher(goToPage).forward(request, response);

    }
}
