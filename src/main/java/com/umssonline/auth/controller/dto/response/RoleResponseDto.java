package com.umssonline.auth.controller.dto.response;

import com.umssonline.auth.repository.domain.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {

    private Long id;

    private RoleEnum authority;

    private Boolean isDeleted;
}
