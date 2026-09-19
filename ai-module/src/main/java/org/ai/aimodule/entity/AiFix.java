package org.ai.aimodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.ai.common.entities.BaseEntity;

@Entity
@Table(name = "ai_fixes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiFix extends BaseEntity {
    private Long issueId;

    @Column(columnDefinition = "TEXT")
    private String suggestedFix;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    private String modelName;

    private Double confidenceScore;
}
