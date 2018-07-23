package com.umssonline.auth.models.dto;

import com.umssonline.auth.models.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateRoleDto {

    @NotNull
    private Long id;

    @NotNull
    private RoleEnum authority;
}
