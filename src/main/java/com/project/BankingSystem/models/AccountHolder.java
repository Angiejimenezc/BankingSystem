package com.project.BankingSystem.models;

import com.project.BankingSystem.classes.Address;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{
    private String name;
    private Date birthDate;
    @Embedded
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code"))
    })
    private Address mailingAddress;

    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Account> accounts;


    public AccountHolder() {
    }

    public AccountHolder(String username, String password, Role role, String name, Date birthDate,
                         Address primaryAddress) {
        super(username, password, role);
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, Date birthDate, Address primaryAddress, List<Account> accounts) {
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.accounts = accounts;
    }

    public AccountHolder(String name, Date birthDate, Address primaryAddress, Address mailingAddress, List<Account> accounts) {
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accounts = accounts;
    }

    public AccountHolder(String username, String password, Role role, String name, Date birthDate,
                         Address primaryAddress, Address mailingAddress, List<Account> accounts) {
        super(username, password, role);
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}
