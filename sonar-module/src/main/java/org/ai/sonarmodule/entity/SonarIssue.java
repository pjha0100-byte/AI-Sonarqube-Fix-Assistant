package org.ai.sonarmodule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ai.common.entities.BaseEntity;

@Entity
@Table(
        name = "sonar_issues",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "issue_key"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SonarIssue extends BaseEntity {

    @Column(name = "issue_key")
    private String issueKey;

    private String ruleKey;

    private String severity;

    private String type;

    private String component;

    @Column(length = 5000)
    private String message;
    private String status;

    private String projectKey;

    @Column(columnDefinition = "TEXT")
    private String codeSnippet;

    private int lineNumber;

    private String suggestedFix;
    private String explanation;
    private Double confidenceScore;

}
