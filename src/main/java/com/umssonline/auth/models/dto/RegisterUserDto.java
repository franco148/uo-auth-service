package com.umssonline.auth.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RegisterUserDto {

    @NotBlank(message = "Name can not be empty.")
    @Size(max = 20, message = "Name must have at most 20 characters.")
    private String name;

    @NotBlank(message = "Last Name can not be empty.")
    @Size(max = 30, message = "Last Name must have at most 30 characters.")
    private String lastName;

    @Size(max = 15, message = "Nickname must have at most 15 characters.")
    private String nickName;

    @PastOrPresent(message = "Your birth date can not be today or future.")
    private LocalDate birthDate;

    @Email(message = "Your account should be a valid e-mail address.")
    @Size(min = 5, max = 50, message = "Your account should have at least 5 characters and at most 50.")
    private String account;

    @Size(min = 6, max = 30, message = "Password should contain between 6 and 30 characters.")
    private String password;

    @NotEmpty(message="At least one role is required")
    private Set<RegisterRoleDto> userRoles = new HashSet<>();


    @JsonIgnore
    private Boolean isLogged = false;

    @JsonIgnore
    private Boolean isDeleted = false;

    @JsonIgnore
    private Boolean isEnabled = false;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime updatedAt = LocalDateTime.now();
}
