package org.ai.sonarmodule.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SonarAPIResponse {

    private List<SonarIssueResponse> issues;
}