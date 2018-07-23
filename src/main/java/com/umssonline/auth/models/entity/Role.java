package com.umssonline.auth.models.entity;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//Soft delete
@SQLDelete(sql = "update authorities set is_deleted=true where id=?")
//Conditions when retrieving data when it is not deleted
@Where(clause = "is_deleted=false")

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

    @NotNull
    @Column(nullable = false)
    private Boolean isDeleted;
}
