package com.umssonline.auth.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CredentialsDto {

    @NotBlank
    private String account;

    @NotBlank
    private String password;
}
