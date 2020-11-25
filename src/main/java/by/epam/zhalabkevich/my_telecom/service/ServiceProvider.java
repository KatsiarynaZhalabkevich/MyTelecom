package by.epam.zhalabkevich.my_telecom.service;

import by.epam.zhalabkevich.my_telecom.service.impl.TariffServiceImpl;
import by.epam.zhalabkevich.my_telecom.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {
    }

    private final UserService userService = new UserServiceImpl();
    private final TariffService tariffService = new TariffServiceImpl();
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
//    public NoteService getNoteService(){return noteService;}

}
