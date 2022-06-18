package com.project.BankingSystem.Service.impl;

import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.controller.dto.AccountHolderDTO;
import com.project.BankingSystem.models.AccountHolder;
import com.project.BankingSystem.models.CreditCard;
import com.project.BankingSystem.models.Role;
import com.project.BankingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BankingSystemService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public AccountHolder findAccountHolder(Long id) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);
        if (accountHolder.isPresent()) {
            return accountHolder.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Holder not found");
    }
/*
  public Money getAccountBalance(Integer secretKey) {
       return findAccountBalance(secretKey);

    }
*/

    public Money getCreditCardBalance(Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isPresent()) {
            return creditCard.get().getCreditCardBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found.");
    }


    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (!accountHolderDTO.getRole().getName().equals("USER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account holders can only be USER");
        } else {
            Role role = new Role("USER");
            roleRepository.save(role);
            AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getUsername(), accountHolderDTO.getPassword(),
                    role, accountHolderDTO.getName(), accountHolderDTO.getBirthDate(),
                    accountHolderDTO.getPrimaryAddress());
            accountHolderRepository.save(accountHolder);
            role.setUser(accountHolder);
            roleRepository.save(role);
            return accountHolder;
        }
    }
    }