package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizationInfo implements Serializable {
    private final static long serialVersionUID = 1L;

    private String login;
    private String password;


    public AuthorizationInfo() {
    }

    public AuthorizationInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationInfo that = (AuthorizationInfo) o;
        return getLogin().equals(that.getLogin()) &&
                getPassword().equals(that.getPassword()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return "AuthorizationInfo{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
