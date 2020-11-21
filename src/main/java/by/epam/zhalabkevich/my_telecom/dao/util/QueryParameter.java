package by.epam.zhalabkevich.my_telecom.dao.util;

public final class QueryParameter {
    /* common */
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String DESCRIPTION = "description";

    /* user*/

    public final static String SURNAME = "surname";
    public final static String PHONE = "phone";
    public final static String EMAIL = "email";
    public final static String ADDRESS = "address";

    /*authInfo*/

    public final static String LOGIN = "login";
    public final static String PASSWORD = "password";
    public final static String ROLE = "role";

    /* account */

    public final static String BALANCE = "balance";
    public final static String REGISTRATION_DATE = "registration_date";
    public final static String STATUS = "status";

    /* note*/
    public final static String CONNECTION_DATE = "connection_date";

    /*tariff*/
    public final static String PRICE = "price";
    public final static String SPEED = "speed";

    /*promotion*/
    public final static String DATE_START = "date_start";
    public final static String DATE_END = "date_end";
    public final static String DISCOUNT = "discount";

    private QueryParameter(){}

}
