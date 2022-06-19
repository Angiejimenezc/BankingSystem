package com.project.BankingSystem.controller.dto;

import com.project.BankingSystem.classes.Address;
import com.project.BankingSystem.models.Role;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class AccountHolderDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private Role role;
    @NotEmpty
    private String name;
    @Past
    private Date birthDate;
    @NotNull
    private Address primaryAddress;

    public AccountHolderDTO() {
    }

    public AccountHolderDTO(String username, String password, Role role, String name, Date birthDate, Address primaryAddress) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthDate = birthDate;
        this.primaryAddress = primaryAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
}