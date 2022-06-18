package com.project.BankingSystem.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class Account {
    @Id
    private Long id;
    private BigDecimal balance;
    @Embedded
    private AccountHolder primaryOwner;
    @Embedded
    private Optional<AccountHolder> secondaryOwner;
    private BigDecimal penaltyFee;


    public Account(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                   BigDecimal penaltyFee) {
        this.id = id;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryAccountHolder) {
        this.primaryOwner = primaryAccountHolder;
    }

    public Optional<AccountHolder> getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Optional<AccountHolder> secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

}
