package com.project.BankingSystem.models;

import com.project.BankingSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Optional;


    @Entity
    public class Checking extends Account {

        private String secretKey;
        private BigDecimal minimumBalance;
        private BigDecimal monthlyMaintenanceFee;
        @Enumerated(value = EnumType.STRING)
        private Status status;

        public Checking(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner, BigDecimal penaltyFee) {
            super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
        }

        public Checking(Long id, BigDecimal balance, AccountHolder primaryOwner, Optional<AccountHolder> secondaryOwner,
                        BigDecimal penaltyFee, String secretKey, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee,
                        Status status) {
            super(id, balance, primaryOwner, secondaryOwner, penaltyFee);
            this.secretKey = secretKey;
            this.minimumBalance = minimumBalance;
            this.monthlyMaintenanceFee = monthlyMaintenanceFee;
            this.status = status;
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

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }
