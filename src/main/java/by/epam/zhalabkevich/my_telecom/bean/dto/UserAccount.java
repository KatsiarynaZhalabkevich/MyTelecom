package by.epam.zhalabkevich.my_telecom.bean.dto;

import by.epam.zhalabkevich.my_telecom.bean.Account;
import by.epam.zhalabkevich.my_telecom.bean.User;

import java.util.Objects;

public class UserAccount {
    private final static long serialVersionUID = 3L;
    private User user;
    private Account account;

    public UserAccount(User user, Account account) {
        this.user = user;
        this.account = account;
    }

    public UserAccount() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getAccount(), that.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getAccount());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "user=" + user +
                ", account=" + account +
                '}';
    }
}
