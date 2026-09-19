package org.ai.sonarmodule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ai.common.entities.BaseEntity;

@Entity
@Table(name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "project_key"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SonarProject extends BaseEntity {

    @Column(name = "project_key")
    private String projectKey;

    @Column(name = "project_name")
    private String projectName;

    private String qualifier;
    private Boolean active=true;
}
