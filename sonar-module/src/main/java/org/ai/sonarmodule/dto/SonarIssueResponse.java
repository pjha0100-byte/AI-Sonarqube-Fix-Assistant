package org.ai.sonarmodule.dto;

import lombok.Data;

@Data
public class SonarIssueResponse {

    private String key;
    private String rule;
    private String severity;
    private String type;
    private String component;
    private String message;
    private String status;
    private int line;
}
