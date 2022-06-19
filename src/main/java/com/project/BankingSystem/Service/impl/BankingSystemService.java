package com.project.BankingSystem.Service.impl;

import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.controller.dto.AccountHolderDTO;
import com.project.BankingSystem.enums.Status;
import com.project.BankingSystem.models.*;
import com.project.BankingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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

    public Money getAccountBalance(Integer secretKey) {
        return findAccountBalance(secretKey);
    }


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

    public ThirdParty createThirdParty(String name, String password) {
        Role role = new Role("THIRD_PARTY");
        roleRepository.save(role);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ThirdParty thirdParty = new ThirdParty(name.toLowerCase() + "_tp", passwordEncoder.encode(password),
                role, name);
        thirdPartyRepository.save(thirdParty);
        role.setUser(thirdParty);
        roleRepository.save(role);
        return thirdParty;
    }

    //  This is the method that is used to create a checking account or a student checking account depending on the age of
//  the account holder.
    public Object validateAgeToCreateAccount(BigDecimal quantity, Long id) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);
        if (accountHolder.isPresent()) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            LocalDateTime ldt = ts.toLocalDateTime();
            int checker = ldt.getYear() - accountHolder.get().getBirthDate().getYear() - 1900;
            if (checker < 24) {
                // st checking
                return studentCheckingRepository.save(new StudentChecking(new Money(quantity), accountHolder.get()));
            } else if (checker == 24) {

                if (ldt.getMonthValue() >= accountHolder.get().getBirthDate().getMonth()+1
                        && ldt.getDayOfMonth() >= accountHolder.get().getBirthDate().getDate()) {
                    // st checking
                    return studentCheckingRepository.save(new StudentChecking(new Money(quantity), accountHolder.get()));
                } else {
                    // checking
                    return checkingRepository.save(new Checking(new Money(quantity), accountHolder.get()));
                }
            } else {
                // checking
                return checkingRepository.save(new Checking(new Money(quantity), accountHolder.get()));
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found.");
    }

    public void creditAccountBalance(Long id, BigDecimal quantity) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().increaseAmount(quantity),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
           // fraudDetector(id, quantity);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }

    public void debitAccountBalance(Long id, BigDecimal quantity) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().decreaseAmount(quantity),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
            //fraudDetector(id, quantity);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }


    public void transferMoney(Long id, Long targetId, BigDecimal amount) {
        Optional<Account> account = accountRepository.findById(id);
        Optional<Account> targetAccount = accountRepository.findById(targetId);
        if (account.isPresent()) {
            if (targetAccount.isPresent()) {
                if(account.get().getBalance().getAmount().doubleValue() >= amount.doubleValue()) {
                    account.get().setBalance(new Money(account.get().getBalance().decreaseAmount(amount)));
                    accountRepository.save(account.get());
                    targetAccount.get().setBalance(new Money(targetAccount.get().getBalance().increaseAmount(amount)));
                    accountRepository.save(targetAccount.get());
                   // fraudDetector(id, amount);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Target account not found.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    public List<Account> showAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public void creditAccountTP(BigDecimal amount, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().increaseAmount(amount),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
           // fraudDetector(id, amount);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }

    public void debitAccountTP(BigDecimal amount, Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            Money balance;
            balance = new Money(account.get().getBalance().decreaseAmount(amount),
                    account.get().getBalance().getCurrency());
            account.get().setBalance(balance);
            accountRepository.save(account.get());
           // fraudDetector(id, amount);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
    }

    public void changeStatusByAccountType(Long id) {
        Optional<Checking> checking = checkingRepository.findById(id);
        Optional<StudentChecking> studentChecking = studentCheckingRepository.findById(id);
        Optional<Savings> savings = savingsRepository.findById(id);
        if (checking.isPresent()) {
            checking.get().setStatus(Status.FROZEN);
            checkingRepository.save(checking.get());
        } else if (studentChecking.isPresent()) {
            studentChecking.get().setStatus(Status.FROZEN);
            studentCheckingRepository.save(studentChecking.get());
        } else if (savings.isPresent()) {
            savings.get().setStatus(Status.FROZEN);
            savingsRepository.save(savings.get());
        }
    }

    public Object findAccountType(Integer secretKey) {
        Optional<Checking> checking = checkingRepository.findBySecretKey(secretKey);
        Optional<StudentChecking> studentChecking = studentCheckingRepository.findBySecretKey(secretKey);
        Optional<Savings> savings = savingsRepository.findBySecretKey(secretKey);
        if (checking.isPresent()) {
            return checking.get();
        } else if (studentChecking.isPresent()) {
            return studentChecking.get();
        } else if (savings.isPresent()) {
            return savings.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    public Money findAccountBalance(Integer secretKey) {
        Optional<Checking> checking = checkingRepository.findBySecretKey(secretKey);
        Optional<StudentChecking> studentChecking = studentCheckingRepository.findBySecretKey(secretKey);
        Optional<Savings> savings = savingsRepository.findBySecretKey(secretKey);
        if (checking.isPresent()) {
            return checking.get().getBalance();
        } else if (studentChecking.isPresent()) {
            return studentChecking.get().getBalance();
        } else if (savings.isPresent()) {
            return savings.get().getSavingsBalance();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    public Object findAccountTypeId(Long id) {
        Optional<Checking> checking = checkingRepository.findById(id);
        Optional<StudentChecking> studentChecking = studentCheckingRepository.findById(id);
        Optional<Savings> savings = savingsRepository.findById(id);
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (checking.isPresent()) {
            return checking.get();
        } else if (studentChecking.isPresent()) {
            return studentChecking.get();
        } else if (savings.isPresent()) {
            return savings.get();
        } else if (creditCard.isPresent()) {
            return creditCard.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }
/*
    public void fraudDetector(Long id, BigDecimal amount) {
        Optional<Transactions> transactions = transactionsRepository.findById(id);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        if (transactions.isPresent()) {
            List<Transaction> transactionList = transactions.get().getTransactions();
            Transaction transaction = new Transaction(amount, ts);
            transaction.setTransactions(transactions.get());
            transactionRepository.save(transaction);
            transactionList.add(transaction);
            transactions.get().setTransactions(transactionList);
            transactionsRepository.save(transactions.get());
            if (transactionList.size() > 1) {
                       if (transactionList.get(transactionList.size()-2).getTimestamp().getHours()-ts.getHours() == 0 &&
                        transactionList.get(transactionList.size()-2).getTimestamp().getMinutes()-ts.getMinutes() == 0 &&
                        transactionList.get(transactionList.size()-2).getTimestamp().getSeconds()-ts.getSeconds() >= -1) {
                    changeStatusByAccountType(id);
                }
            }
        } else {
            List<Transaction> transactionList = new ArrayList<>();
            Transaction transaction = new Transaction(amount, ts);
            transactionList.add(transaction);
            transactionRepository.save(transaction);
            Transactions transactions1 = new Transactions(id, transactionList);
            transactionsRepository.save(transactions1);
            transaction.setTransactions(transactions1);
            transactionRepository.save(transaction);
        }
    }
*/
    public Object accessAccount(Integer secretKey) {
        return findAccountType(secretKey);
    }

    public Object accessAccountAdmin(Long id) {
        return findAccountTypeId(id);
    }


}