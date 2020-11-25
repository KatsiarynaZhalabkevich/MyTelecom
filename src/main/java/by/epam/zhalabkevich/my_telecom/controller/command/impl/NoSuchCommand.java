package by.epam.zhalabkevich.my_telecom.controller.command.impl;

import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoSuchCommand implements Command {
    private final static String ERROR_MESSAGE="errorMessage";
    private final static String ERROR_MESSAGE_TXT="Sorry, this action can't be done!";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String goToPage= JSPPageName.ERROR_PAGE;
        request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TXT);

        response.sendRedirect(goToPage);
    }
}
