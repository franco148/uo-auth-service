package com.umssonline.auth.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umssonline.auth.models.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRoleDto {

    @NotNull
    private RoleEnum authority;

    @JsonIgnore
    private Boolean isDeleted = false;
}
