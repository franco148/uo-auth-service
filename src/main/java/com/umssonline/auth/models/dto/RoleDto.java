package com.umssonline.auth.models.dto;

import com.umssonline.auth.models.entity.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleDto {

    @NotNull
    private RoleEnum authority;
}
