package com.umssonline.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umssonline.auth.repository.domain.RoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Role Model")
@Getter
@Setter
public class RegisterRoleDto {

    @ApiModelProperty
    (
        notes = "Authority field can not be null or empty.",
        required = true,
        allowableValues = "ADMIN, TRIBUNAL, DIRECTOR, PROFESSOR, TUTOR, STUDENT"
    )
    @NotNull
    private RoleEnum authority;

    @JsonIgnore
    private Boolean isDeleted = false;
}
