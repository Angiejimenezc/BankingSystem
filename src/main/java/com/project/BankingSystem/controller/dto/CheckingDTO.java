package com.project.BankingSystem.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class CheckingDTO {
    @PositiveOrZero
    private BigDecimal quantity;
    @NotNull
    private Long id;

    public CheckingDTO() {
    }

    public CheckingDTO(BigDecimal quantity, Long id) {
        this.quantity = quantity;
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}