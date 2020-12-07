package by.epam.zhalabkevich.my_telecom.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Account implements Serializable {
    private final static long serialVersionUID = 2L;
    private long id;
    private BigDecimal balance;
    private LocalDate registrationDate;
    private Status status = Status.BLOCKED;
    private Role role = Role.USER;


    public Account() {
    }

    public Account(LocalDate registrationDate, Status status) {
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getId() == account.getId() &&
                Objects.equals(getBalance(), account.getBalance()) &&
                Objects.equals(getRegistrationDate(), account.getRegistrationDate()) &&
                getStatus() == account.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBalance(), getRegistrationDate(), getStatus());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", registrationDate=" + registrationDate +
                ", status=" + status +
                '}';
    }
}
