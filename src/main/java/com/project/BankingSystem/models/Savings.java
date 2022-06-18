package com.project.BankingSystem.models;

import com.project.BankingSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class Savings extends Account {

    private String secretKey;
    private BigDecimal minimumBalance;
    private Double interestRate;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Savings(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                   BigDecimal penaltyFee) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        this.interestRate = 0.0025;
        this.minimumBalance = new BigDecimal(1000);
    }

    public Savings(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                   BigDecimal penaltyFee, String secretKey, BigDecimal minimumBalance, Double interestRate,
                   Status status) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        setStatus(status);
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        if (interestRate <= 0.5) {
            this.interestRate = interestRate;
        } else {
            this.interestRate = 0.0025;
            System.err.println("Interest rate can't be greater than 0.5. It will be set to its default value.");
        }
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
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
}