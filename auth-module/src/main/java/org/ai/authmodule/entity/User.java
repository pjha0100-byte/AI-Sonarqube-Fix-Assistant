package org.ai.authmodule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ai.common.entities.BaseEntity;
import org.ai.common.enums.Roles;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private boolean active;
    private boolean enabled;
}
