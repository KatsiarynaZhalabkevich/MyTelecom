package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToPageCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static JSPPageName page = JSPPageName.getInstance();
    private final static String USER = "user";
    private static final String GO_TO_PAGE = "go_to_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage = request.getParameter(GO_TO_PAGE);
        logger.debug(goToPage);
        logger.debug(page.ifPageExist(goToPage));
        if(page.ifPageExist(goToPage)){
            goToPage = JSPPageName.INDEX_PAGE;
        }
        logger.debug(goToPage);
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
