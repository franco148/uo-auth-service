package com.umssonline.auth.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String name;

    private String lastName;

    private String nickName;

    private LocalDate birthDate;

    private String account;

    private Boolean isLogged;

    private Boolean isDeleted;

    private Boolean isEnabled;

    private Set<RoleResponseDto> userRoles = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
