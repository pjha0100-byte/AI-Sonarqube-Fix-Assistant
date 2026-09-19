package org.ai.reportmodule.service;


import org.ai.common.dtos.SeverityDistributionResponse;
import org.ai.reportmodule.dto.DashboardSummaryResponse;

import java.util.List;

public interface DashboardService {

    DashboardSummaryResponse getSummary();
    List<SeverityDistributionResponse> getSeverityDistribution();

    List<SeverityDistributionResponse> getSeverityDistributionByProjectKey(String key);
}
