package com.project.BankingSystem.models;

import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account{
    private Integer secretKey;
    private BigDecimal minimumBalance;
    private Double interestRate;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private LocalDateTime ldt;


    public Savings() {
        this.interestRate = 0.0025;
        this.minimumBalance = new BigDecimal(1000);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }

    public Savings(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.secretKey = (int) (Math.random()*1000000);
        this.interestRate = 0.0025;
        this.minimumBalance = new BigDecimal(1000);
        this.status = Status.ACTIVE;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = (int) (Math.random()*1000000);;
        this.interestRate = 0.0025;
        this.minimumBalance = new BigDecimal(1000);
        this.status = Status.ACTIVE;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.ldt = ts.toLocalDateTime();
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        if (interestRate <= 0.5 && interestRate >= 0.0025) {
            this.interestRate = interestRate;
        } else {
            this.interestRate = 0.0025;
            System.err.println("Interest rate can't be greater than 0.5. It will be set to its default value.");
        }
    }

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if ( minimumBalance.doubleValue() >= 100 && minimumBalance.doubleValue() <= 1000) {
            this.minimumBalance = minimumBalance;
        } else {
            this.minimumBalance = new BigDecimal(1000);
            System.err.println("Minimum balance may be less than 1000 but no lower than 100. It will be set to its " +
                    "default value.");
        }

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public Money getSavingsBalance() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        LocalDateTime newLdt = ts.toLocalDateTime();
        if (newLdt.getYear() > getLdt().getYear()) {
            if (newLdt.getDayOfYear() >= getLdt().getDayOfYear()) {
                Money balance = new Money(super.getBalance()
                        .increaseAmount(super.getBalance()
                                .getAmount()
                                .multiply(BigDecimal.valueOf(getInterestRate()))),
                        super.getBalance().getCurrency()

                );
                super.setBalance(balance);
                setLdt(newLdt);
                return balance;
            }
        }
        return super.getBalance();
    }


}