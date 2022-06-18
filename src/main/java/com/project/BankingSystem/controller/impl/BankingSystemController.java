package com.project.BankingSystem.controller.impl;


import com.project.BankingSystem.Service.impl.BankingSystemService;
import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.controller.dto.*;
import com.project.BankingSystem.controller.interfaces.IBankingSystemController;
import com.project.BankingSystem.models.Account;
import com.project.BankingSystem.models.AccountHolder;
import com.project.BankingSystem.models.ThirdParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankingSystemController implements IBankingSystemController {

    @Autowired
    private BankingSystemService bankingSystemService;

    //  Allows account holders to access to their accounts by typing their secret key in the path
    @GetMapping("/access-account/{secretKey}")
    @ResponseStatus(HttpStatus.OK)
    public Object accessAccount(@PathVariable Integer secretKey) {
        return bankingSystemService.accessAccount(secretKey);
    }

    //  Allows admins to access any account they want by typing the id of the account
    @GetMapping("/access-account/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object accessAccountAdmin(@PathVariable Long id) {
        return bankingSystemService.accessAccountAdmin(id);
    }

    //  Allows admins to find the information of any account holder by typing its id
    @GetMapping("/account-holder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolder(@PathVariable Long id) {
        return bankingSystemService.findAccountHolder(id);
    }

    @Override
    public Money getAccountBalance(Integer secretKey) {
        return null;
    }

    @Override
    public Money getCreditCardBalance(Long id) {
        return null;
    }

    @Override
    public void transferMoney(Long id, Long targetId, QuantityDTO quantityDTO) {

    }

    @Override
    public List<Account> accounts() {
        return null;
    }

    @Override
    public void creditAccountBalance(Long id, QuantityDTO quantity) {

    }

    @Override
    public void debitAccountBalance(Long id, QuantityDTO quantity) {

    }

    @Override
    public void creditAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {

    }

    @Override
    public void debitAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {

    }

    @Override
    public Object createChecking(CheckingDTO checkingDTO) {
        return null;
    }

    @Override
    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        return null;
    }

    @Override
    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO) {
        return null;
    }
}
