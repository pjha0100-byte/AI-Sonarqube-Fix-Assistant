package org.ai.sonarmodule.dto;

import lombok.Data;

@Data
public class Component {
    private String key;
    private String name;
    private String qualifier;
    private String visibility;
    private String lastAnalysisDate;
    private Boolean managed;
    private String projectUuid;
}
