package com.umssonline.auth.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(length = 15)
    private String nickName;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(nullable = false, unique = true, length = 20)
    private String account;
    @Column(nullable = false, length = 30)
    private String password;
    @Column(nullable = false)
    private Boolean isLogged;
    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {

    }

    public User(String name, String lastName, String nickName, Date birthDate, String account, String password, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
