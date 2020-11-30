package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import by.epam.zhalabkevich.my_telecom.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
// лишний написала, но оставлю вдруг в 1-ом оштбка
public class UpdatePasswordCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private static final String ERROR_MESSAGE ="errorMessage" ;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage;

        if (user != null) {
            String password1 = request.getParameter(PASSWORD+1);
            String password2 = request.getParameter(PASSWORD+2);

            if(!password1.equals("") && !password1.equals(" ") && password1.equals(password2)){
                try {
                    userService.updatePassword(password1, user);
                    goToPage = JSPPageName.USER_AUTH_PAGE;
                } catch (ServiceException e) {
                    logger.error(e);
                    session.setAttribute(ERROR_MESSAGE, e); //сообщения на 1 раз записывать в request
                    goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
                }
            }else{
                session.setAttribute(ERROR_MESSAGE, "Passwords are invalid! "); //сообщения на 1 раз записывать в request
                goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
            }
        }else{
            goToPage = JSPPageName.INDEX_PAGE;
        }
        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}
