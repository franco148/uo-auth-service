package com.umssonline.auth.models;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//Soft delete
@SQLDelete(sql = "update user set is_deleted=true where id=?")
//Conditions when retrieving data when it is not deleted
@Where(clause = "is_deleted=false")

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name can not be empty.")
    @Size(max = 20, message = "Name must have at most 20 characters.")
    @Column(nullable = false, length = 20)
    private String name;

    @NotBlank(message = "Last Name can not be empty.")
    @Size(max = 30, message = "Last Name must have at most 30 characters.")
    @Column(nullable = false, length = 30)
    private String lastName;

    @Size(max = 15, message = "Nickname must have at most 15 characters.")
    @Column(length = 15)
    private String nickName;

    @PastOrPresent(message = "Your birth date can not be today or future.")
    private LocalDate birthDate;

    @Email(message = "Your account should be a valid e-mail address.")
    @Size(min = 5, message = "Your account should have at least 5 characters.")
    @Column(nullable = false, unique = true, length = 20)
    private String account;

    @Size(min = 6, max = 30, message = "Password should contain between 6 and 30 characters.")
    @Column(nullable = false, length = 30)
    private String password;

    @NotNull
    @Column(nullable = false)
    private Boolean isLogged;

    @NotNull
    @Column(nullable = false)
    private Boolean isDeleted;

    @NotNull
    @Column(nullable = false)
    private Boolean isEnabled;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private Set<Role> userRoles = new HashSet<>();

    protected User() {

    }

    public User(String name, String lastName, String nickName, LocalDate birthDate, String account, String password, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.account = account;
        this.password = password;
        this.userRoles.add(role);
    }

    @PreRemove
    private void preRemove() {
        this.isDeleted = true;
    }
}
