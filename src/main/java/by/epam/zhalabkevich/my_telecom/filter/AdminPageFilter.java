package by.epam.zhalabkevich.my_telecom.filter;

import by.epam.zhalabkevich.my_telecom.bean.Role;
import by.epam.zhalabkevich.my_telecom.bean.User;
import by.epam.zhalabkevich.my_telecom.controller.JSPPageName;
import by.epam.zhalabkevich.my_telecom.service.AccountService;
import by.epam.zhalabkevich.my_telecom.service.ServiceException;
import by.epam.zhalabkevich.my_telecom.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(filterName = "adminFilter", urlPatterns = {"users/admin/*"})
public class AdminPageFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final AccountService accountService = ServiceProvider.getInstance().getAccountService();
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="You need to have administrator rights to get to this page! ";
    private final static String USER = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        logger.info("admin page filter");
        try {
            if (user == null|| !Role.ADMIN.equals(accountService.checkRoleByUserId(user.getId()))) {
                request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                response.sendRedirect(JSPPageName.INDEX_PAGE);
            }
        } catch (ServiceException e) {
            logger.error(e);
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }

        filterChain.doFilter(request, response);
    }
}
