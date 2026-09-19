package org.ai.sonarmodule.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectResponse {
    private Paging paging;
    private List<Component> components;
}
