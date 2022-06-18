package com.project.BankingSystem.controller.interfaces;

import com.project.BankingSystem.classes.Money;
import com.project.BankingSystem.controller.dto.*;
import com.project.BankingSystem.models.Account;
import com.project.BankingSystem.models.AccountHolder;
import com.project.BankingSystem.models.ThirdParty;

import java.util.List;

public interface IBankingSystemController {
    Object accessAccount(Integer secretKey);
    Object accessAccountAdmin(Long id);
    AccountHolder getAccountHolder(Long id);
    Money getAccountBalance(Integer secretKey);
    Money getCreditCardBalance(Long id);
    void transferMoney(Long id, Long targetId, QuantityDTO quantityDTO);
    List<Account> accounts();
    void creditAccountBalance(Long id, QuantityDTO quantity);
    void debitAccountBalance(Long id, QuantityDTO quantity);
    void creditAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
    void debitAccountTP(Integer hashedKey, TransferThirdPartyDTO transferThirdPartyDTO);
    Object createChecking(CheckingDTO checkingDTO);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
    ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);

}