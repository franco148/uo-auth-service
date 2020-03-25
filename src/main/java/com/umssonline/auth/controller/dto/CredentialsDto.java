package com.umssonline.auth.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Credentials Model")
@Data
public class CredentialsDto {

    @ApiModelProperty
    (
        notes = "Account field can not be null or empty.",
        required = true,
        example = "youmail@domain.com"
    )
    @NotBlank
    private String account;

    @ApiModelProperty
    (
        notes = "Password field can not be null or empty.",
        required = true
    )
    @NotBlank
    private String password;
}
