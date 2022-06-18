package com.project.BankingSystem.models;

import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;
import java.util.Optional;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Checking extends Account{
    private Integer secretKey;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    @Enumerated(value = EnumType.STRING)
    private Status status;


    public Checking() {
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
    }

    public Checking(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.secretKey = (int) (Math.random()*1000000);
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        this.status = Status.ACTIVE;
    }

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = (int) (Math.random()*1000000);
        this.minimumBalance = new BigDecimal(250);
        this.monthlyMaintenanceFee = new BigDecimal(12);
        this.status = Status.ACTIVE;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Integer secretKey) {
        this.secretKey = secretKey;
    }

    public void setCheckingBalance(Money balance) {
        if (balance.getAmount().doubleValue() < minimumBalance.doubleValue()) {
            System.err.println("The balance for a checking account can not be less than the minimum balance: 250." +
                    "A penalty fee of 40 will be deducted from the current balance.");
            balance.decreaseAmount(super.getPenaltyFee());
        }
        super.setBalance(balance);
    }
}
