package by.epam.zhalabkevich.my_telecom.filter;

import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(urlPatterns = {"users/"})
public class AuthenticationFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="No permission for this action! Please, log in!";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        logger.info("фильтр аутентификации");

        if (user == null) {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }

        filterChain.doFilter(request, response);
    }
}
