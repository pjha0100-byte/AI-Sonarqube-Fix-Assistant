package org.ai.aimodule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiFixResponse {

    private String ruleKey;
    private String severity;
    private String rootCause;
    private String fixExplanation;
    private String fixedCode;
    private String codeSnippet;
}
