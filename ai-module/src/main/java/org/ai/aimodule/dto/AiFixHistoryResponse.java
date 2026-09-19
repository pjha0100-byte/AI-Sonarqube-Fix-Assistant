package org.ai.aimodule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AiFixHistoryResponse {

    private Long id;

    private Long issueId;

    private String suggestedFix;

    private String explanation;

    private String modelName;

    private Double confidenceScore;

    private LocalDateTime createdAt;
}
