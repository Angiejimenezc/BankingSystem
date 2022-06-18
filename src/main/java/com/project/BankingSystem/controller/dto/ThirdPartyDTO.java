package com.project.BankingSystem.controller.dto;

import javax.validation.constraints.NotEmpty;

public class ThirdPartyDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    public ThirdPartyDTO() {
    }

    public ThirdPartyDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}