package com.project.BankingSystem.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private final Timestamp timestamp;

    @ManyToOne
    private Transactions transactions;

    public Transaction() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Transaction(BigDecimal amount, Timestamp timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }



}
