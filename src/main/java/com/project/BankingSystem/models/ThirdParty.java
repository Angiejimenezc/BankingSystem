package com.project.BankingSystem.models;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User{
    private String name;
    private Integer hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name) {
        setName(name);
        this.hashedKey = name.hashCode();
    }

    public ThirdParty(String username, String password, Role role, String name) {
        super(username, password, role);
        this.name = name;
        this.hashedKey = name.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHashedKey() {
        return hashedKey;
    }

}