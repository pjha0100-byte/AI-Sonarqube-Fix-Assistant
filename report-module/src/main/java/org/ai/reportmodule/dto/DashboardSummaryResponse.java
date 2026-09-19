package org.ai.reportmodule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryResponse {

    private long totalProjects;
    private long totalIssues;

    private long criticalIssues;
    private long majorIssues;
    private long minorIssues;
    private long blockerIssues;
    private long infoIssues;
    private long aiFixGenerated;

}
