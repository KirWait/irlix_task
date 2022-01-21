package com.irlix.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.irlix.task.enumeration.Active;
import com.irlix.task.enumeration.Roles;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Active active;

    @Column(name = "balance")
    @Min(value = 0)
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

    public void setId(Long user_id) {
        this.id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
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

    public void setActive(Active status) {
        this.active = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && roles == that.roles && active == that.active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, active);
    }
}
