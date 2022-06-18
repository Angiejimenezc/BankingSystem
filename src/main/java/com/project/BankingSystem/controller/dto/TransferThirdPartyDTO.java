package com.project.BankingSystem.controller.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferThirdPartyDTO {
    @Digits(integer = 6, fraction = 2, message= "Amount format is nota valid")
    private BigDecimal amount;
    @NotNull
    @Positive
    private Long id;

    public TransferThirdPartyDTO() {
    }

    public TransferThirdPartyDTO(BigDecimal amount, Long id) {
        this.amount = amount;
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
