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

public class EditProfileCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final UserService userService = ServiceProvider.getInstance().getUserService();
    private final static String USER = "user";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String PASSWORD = "password";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String ADDRESS = "address";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_PASSWORD_MESSAGE = "errorPasswordMessage";
    private final static String UPD_MESSAGE_TEXT = "User's info updated successfully!";
    private final static String ERROR_PASSWORD_MESSAGE_TEXT = "Passwords are not equals";
    private final static String ERROR_MESSAGE_TEXT = "Can't update user's information.";
    private final static String ERROR_MESSAGE_TEXT_NOT_USER = "Session is finished. You have no permission for this action. Please, log in!";
//TODO сделать отдельный метод про смену password?
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage;

        if (user != null) {
            String name = request.getParameter(NAME);
            String surname = request.getParameter(SURNAME);
            String phone = request.getParameter(PHONE);
            String email = request.getParameter(EMAIL);
            String address = request.getParameter(ADDRESS);

            User userForUpdate = new User(name, surname, phone, email, address);
            userForUpdate.setId(user.getId());
            try {
               User userFromDB =userService.saveUpdateUser(userForUpdate);
               if(!userFromDB.equals(new User())){
                    session.setAttribute(USER, userFromDB);
                    goToPage = JSPPageName.USER_AUTH_PAGE;
                    session.setAttribute(UPD_MESSAGE, UPD_MESSAGE_TEXT); //request
                } else {
                    session.setAttribute(UPD_MESSAGE, ERROR_MESSAGE_TEXT);
                    goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
                }
            } catch (ServiceException e) {
                logger.error(e);
                goToPage = JSPPageName.USER_AUTH_PAGE;
                session.setAttribute(UPD_MESSAGE, e); //request
            }
        } else{
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT_NOT_USER);
                goToPage = JSPPageName.ERROR_PAGE;
        }

        request.getRequestDispatcher(goToPage).forward(request, response);
    }
}

