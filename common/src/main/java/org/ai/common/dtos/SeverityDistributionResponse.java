package org.ai.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeverityDistributionResponse {

    private String projectKey;
    private String severity;
    private Long count;

    public SeverityDistributionResponse(String severity,
                                        Long count) {
        this.severity = severity;
        this.count = count;
    }

    public SeverityDistributionResponse(String projectKey,
                                        String severity,
                                        Long count) {
        this.projectKey = projectKey;
        this.severity = severity;
        this.count = count;
    }
}
