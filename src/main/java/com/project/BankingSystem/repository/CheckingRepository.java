package com.project.BankingSystem.repository;

import com.project.BankingSystem.models.Checking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckingRepository extends JpaRepository<Checking,Long> {
    public Optional<Checking> findBySecretKey(Integer secretKey);

}
