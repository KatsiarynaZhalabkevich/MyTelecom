package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.NoteService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteTariffNoteFromAccountCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static ServiceProvider provider = ServiceProvider.getInstance();
    private final NoteService noteService = provider.getNoteService();
    private final static String USER = "user";
    private final static String NOTE_ID = "note_id";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_MESSAGE = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long noteId = Long.parseLong(request.getParameter(NOTE_ID));
        String goToPage;

        if (user != null) {
            logger.debug(noteId);
            try {
                if (noteService.deleteTariffNote(noteId)) {
                    request.setAttribute(UPD_MESSAGE, "Tariff was deleted fro, your account");
                } else {
                    request.setAttribute(ERROR_MESSAGE, "Tariff wasn't delete from account. Please, contact support department!");
                }
                goToPage = "controller?command=show_user_info";
            } catch (ServiceException e) {
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
                goToPage = JSPPageName.ERROR_PAGE;
            }

        } else {
            request.setAttribute(ERROR_MESSAGE, "Session is expired. Please, sign in!");
            goToPage = JSPPageName.ERROR_PAGE;
        }

        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
