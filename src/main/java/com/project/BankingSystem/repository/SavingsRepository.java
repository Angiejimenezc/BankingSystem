package com.project.BankingSystem.repository;

import com.project.BankingSystem.models.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
    public Optional<Savings> findBySecretKey(Integer secretKey);
}