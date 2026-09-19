package org.ai.sonarmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ai.sonarmodule.entity.SonarIssue;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponse {

    private Long id;

    private String issueKey;

    private String ruleKey;

    private String severity;

    private String type;

    private String component;

    private String message;

    private String status;

    private String projectKey;

    private Integer lineNumber;

    private String codeSnippet;

    private String suggestedFix;
    private String explanation;
    private Double confidenceScore;
}
