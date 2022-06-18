package com.project.BankingSystem.models;

import com.project.BankingSystem.classes.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @NotNull
    private Long id;
    @Embedded
    private Money balance;
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "secondary_owner_id")),
            @AttributeOverride(name = "name", column = @Column(name = "secondary_owner_name")),
            @AttributeOverride(name = "birthDate", column = @Column(name = "secondary_owner_name")),
            @AttributeOverride(name = "primaryAddress", column = @Column(name = "secondary_owner_primary_address")),
            @AttributeOverride(name = "mailingAddress", column = @Column(name = "secondary_owner_mailing_address"))
    })
    private AccountHolder secondaryOwner;
    private BigDecimal penaltyFee;


    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.id = (long) (Math.random() * 10000000000000000L);
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.penaltyFee = new BigDecimal(40);
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.id = (long) (Math.random() * 10000000000000000L);
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = new BigDecimal(40);
    }

    public Account(Long id, AccountHolder primaryOwner) {
        this.id = id;
        this.primaryOwner = primaryOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                ", penaltyFee=" + penaltyFee +
                '}';
    }
}
