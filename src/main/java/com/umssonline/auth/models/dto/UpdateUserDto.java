package com.umssonline.auth.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "User Model")
@Getter
@Setter
public class UpdateUserDto {

    @ApiModelProperty( notes = "Name can not be empty. It must have at most 20 characters.")
    @NotBlank(message = "Name can not be empty.")
    @Size(max = 20, message = "Name must have at most 20 characters.")
    private String name;

    @ApiModelProperty ( notes = "Last Name can not be empty. It must have at most 30 characters.")
    @NotBlank(message = "Last Name can not be empty.")
    @Size(max = 30, message = "Last Name must have at most 30 characters.")
    private String lastName;

    @ApiModelProperty ( notes = "Nickname must have at most 15 characters.")
    @Size(max = 15, message = "Nickname must have at most 15 characters.")
    private String nickName;

    @ApiModelProperty ( notes = "Your birth date can not be today or future.")
    @PastOrPresent(message = "Your birth date can not be today or future.")
    private LocalDate birthDate;

    @ApiModelProperty ( notes = "Your account should be a valid e-mail address. It should have at least 5 characters and at most 50.")
    @Email(message = "Your account should be a valid e-mail address.")
    @Size(min = 5, max = 50, message = "Your account should have at least 5 characters and at most 50.")
    private String account;

    @ApiModelProperty ( notes = "Password should contain between 6 and 30 characters.")
    @Size(min = 6, max = 30, message = "Password should contain between 6 and 30 characters.")
    private String password;

    @ApiModelProperty ( notes = "At least one role is required.")
    @NotEmpty(message="At least one role is required")
    private Set<UpdateRoleDto> userRoles = new HashSet<>();


    @JsonIgnore
    private LocalDateTime updatedAt = LocalDateTime.now();
}
