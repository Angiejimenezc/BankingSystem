package com.project.BankingSystem.models;

import com.project.BankingSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
public class StudentChecking extends Account {

    private String secretKey;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public StudentChecking(Long id, BigDecimal balance, AccountHolder primaryOwner,
                           Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
    }

    public StudentChecking(Long id, BigDecimal balance, AccountHolder primaryOwner,
                           Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee, String secretKey,
                           Status status) {
        super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        this.secretKey = secretKey;
        this.status = status;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
