package com.irlix.task.dto;


import com.irlix.task.entity.ProductEntity;
import com.irlix.task.enumeration.Active;
import com.irlix.task.enumeration.Roles;

import javax.persistence.Transient;
import java.util.List;

public class UserRequestDto {


    private Long id;
    private String username;
    private String password;
    private Roles roles;
    private Active active;
    private Long balance;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }
}
