package by.epam.zhalabkevich.my_telecom.controller;

import by.epam.zhalabkevich.my_telecom.controller.command.CommandProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class JSPPageName {
    private static final JSPPageName instance = new JSPPageName();
    private Set<String> pagesList = new HashSet<>();
    public static final String USER_AUTH_PAGE = "users/user";
    public static final String USER_EDIT_PROFILE_PAGE = "users/user/edit";
    public static final String USER_REG_PAGE = "users/new";
    public static final String INDEX_PAGE = "main";
    public static final String ERROR_PAGE = "error";
    public static final String TARIFF_PAGE = "tariffs";
    public static final String ADMIN_PAGE = "users/admin";
    public static final String SHOW_USERS_PAGE = "users";
    public static final String TARIFF_ADMIN_PAGE = "admin/tariffs";
    public static final String SHOW_USERS_TARIFFS_PAGE = "users/user/tariffs";
    public static final String STATISTIC_PAGE = "users/admin/statistic";
    public static final String SHOW_PROMO = "promotions";

    private JSPPageName() {
        pagesList.add(USER_AUTH_PAGE);
        pagesList.add(USER_EDIT_PROFILE_PAGE);
        pagesList.add(INDEX_PAGE);
        pagesList.add(ERROR_PAGE);
        pagesList.add(TARIFF_PAGE);
        pagesList.add(ADMIN_PAGE);
        pagesList.add(SHOW_USERS_PAGE);
        pagesList.add(TARIFF_ADMIN_PAGE);
        pagesList.add(SHOW_USERS_TARIFFS_PAGE);
        pagesList.add(STATISTIC_PAGE);
        pagesList.add(SHOW_PROMO);
        //pagesList.add();
    }

    public static JSPPageName getInstance() {
        return instance;
    }

    public boolean ifPageExist(String pageName) {
        return pagesList.contains(pageName);
    }
}
