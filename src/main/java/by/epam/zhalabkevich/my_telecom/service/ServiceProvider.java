package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.service.impl.AccountServiceImpl;
import by.epam.zhalabkevich.my_telecom.service.impl.PromotionServiceImpl;
import by.epam.zhalabkevich.my_telecom.service.impl.TariffServiceImpl;
import by.epam.zhalabkevich.my_telecom.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {
    }

    private final UserService userService = new UserServiceImpl();
    private final TariffService tariffService = new TariffServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final PromotionService promotionService = new PromotionServiceImpl();
//    private NoteService noteService = new NoteServiceImpl();


    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public TariffService getTariffService() {
        return tariffService;
    }

    public AccountService getAccountService(){
        return accountService;
    }

    public PromotionService getPromotionService(){return promotionService;}
//    public NoteService getNoteService(){return noteService;}

}
