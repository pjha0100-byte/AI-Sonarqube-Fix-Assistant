package org.ai.sonarmodule.dto;

import lombok.Data;

@Data
public class SonarProjectResponse {

    private String key;
    private String name;
    private String qualifier;
}
