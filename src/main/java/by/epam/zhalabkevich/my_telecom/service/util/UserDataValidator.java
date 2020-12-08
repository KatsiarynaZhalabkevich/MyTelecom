package by.epam.zhalabkevich.my_telecom.service.util;

import by.epam.zhalabkevich.my_telecom.bean.User;

public class UserDataValidator {

    private static final UserDataValidator instance = new UserDataValidator();

    /**
     * Класс для проверки данных пользователя при регистрации
     */
    private UserDataValidator() {
    }

    public static UserDataValidator getInstance() {
        return instance;
    }

    public boolean checkLogin(String login) {
        String regLog = "[0-9a-zA-Z]{4,10}";
        return login != null && login.matches(regLog);
    }

    public boolean checkPassword(String password) {
        //  String regPas = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{6,}";
        String regPas = "[0-9a-zA-Z@#$%]{4,10}";
        return password != null && password.matches(regPas);
    }

    public boolean checkName(String name) {
        String regExp = "[а-яА-Я]|[a-zA-Z]{4,30}";
        return name != null && name.matches(regExp);
    }

    public boolean checkPhone(String phone) {
      //  String regExp = "^\\+?(\\d{1,3})?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$";
        String regExp = "^(\\+375|80)([- (]?(29|25|44|33)[- )]?)([- ]?(\\d{3}))([- ]?(\\d{2}))([- ]?(\\d{2}))$";
        return phone != null && phone.matches(regExp);
    }

    public boolean checkEmail(String email) {
        String regExp = "[a-zA-Z0-9_]+@[a-zA-Z0-9-]+.+.[a-zA-Z]{2,4}";
        return email != null && email.matches(regExp);
    }

    public boolean userValidate(User user) {
        boolean name = checkName(user.getName());
        boolean surname = checkName(user.getSurname());
        boolean email = checkEmail(user.getEmail());
        boolean phone = checkPhone(user.getPhone());

        return name && surname && email && phone;
    }


}
