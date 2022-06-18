package com.project.BankingSystem.models;

import com.project.BankingSystem.classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {
    private BigDecimal creditLimit;
    private Double interestRate;

    private LocalDateTime ldt;


    public CreditCard() {
        this.creditLimit = new BigDecimal(100);
        this.interestRate = 0.2;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.creditLimit = new BigDecimal(100);
        setCreditCardBalance(balance);
        this.interestRate = 0.2;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }


    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.doubleValue() > 100 && creditLimit.doubleValue() < 100000) {
            this.creditLimit = creditLimit;
        } else {
            System.err.println("Credit limit must be an amount between 100 and 100000. It will be set to its default " +
                    "value.");
            this.creditLimit = new BigDecimal(100);
        }
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public void setInterestRate(Double interestRate) {
        if (interestRate <= 0.2 && interestRate >= 0.1) {
            this.interestRate = interestRate;
        } else {
            System.err.println("Interest rate may be less than 0.2 but no lower than 0.1. It will be set to its " +
                    "default value.");
            this.interestRate = 0.2;
        }
    }

    public void setCreditCardBalance(Money balance) {
        if (balance.getAmount().doubleValue() >= getCreditLimit().doubleValue()) {
            super.setBalance(new Money(getCreditLimit(), balance.getCurrency()));
        } else {
            super.setBalance(balance);
        }
    }

    public Money getCreditCardBalance() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LocalDateTime newLdt = ts.toLocalDateTime();
        if (newLdt.getMonthValue() > getLdt().getMonthValue()) {
            if (newLdt.getDayOfMonth() >= getLdt().getDayOfMonth()) {
                Money balance = new Money(super.getBalance()
                        .increaseAmount(super.getBalance()
                                .getAmount()
                                .multiply(BigDecimal.valueOf(getInterestRate() / 12))),
                        super.getBalance().getCurrency());
                super.setBalance(balance);
                setLdt(newLdt);
                return balance;
            }
        }
        return super.getBalance();
    }
}