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

public class EditProfileCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage;

        if (user != null) {
            goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
        } else goToPage = JSPPageName.INDEX_PAGE;

        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}

