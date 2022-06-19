package com.project.BankingSystem.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Transactions {
    @Id
    private Long id;
    @OneToMany(mappedBy = "transactions")
    private List<Transaction> transactions = new ArrayList<>();

    public Transactions() {
    }

    public Transactions(Long id, List<Transaction> transactions) {
        this.id = id;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
