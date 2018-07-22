package com.umssonline.auth.models.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
(
    name = "authorities",
    uniqueConstraints =
    {
        @UniqueConstraint(columnNames = { "user_id", "authority" })
    }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum authority;

}
