package org.ai.reportmodule.service.impl;

import lombok.RequiredArgsConstructor;
import org.ai.aimodule.repository.AiFixRepository;
import org.ai.common.dtos.SeverityDistributionResponse;
import org.ai.reportmodule.dto.DashboardSummaryResponse;
import org.ai.reportmodule.service.DashboardService;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final SonarIssueRepository sonarIssueRepository;
    private final AiFixRepository aiFixRepository;

    @Override
    public DashboardSummaryResponse getSummary() {

        return DashboardSummaryResponse.builder()
                .totalProjects(
                        sonarIssueRepository.countDistinctByProjectKeyIsNotNull()
                )
                .totalIssues(
                        sonarIssueRepository.count()
                )
                .criticalIssues(
                        sonarIssueRepository.countBySeverity("CRITICAL")
                )
                .majorIssues(
                        sonarIssueRepository.countBySeverity("MAJOR")
                )
                .minorIssues(
                        sonarIssueRepository.countBySeverity("MINOR")
                )
                .blockerIssues(
                        sonarIssueRepository.countBySeverity("BLOCKER")
                )
                .infoIssues(
                        sonarIssueRepository.countBySeverity("INFO")
                )
                .aiFixGenerated(
                        aiFixRepository.count()
                )
                .build();
    }

    @Override
    public List<SeverityDistributionResponse> getSeverityDistribution() {

        return sonarIssueRepository.getSeverityDistribution();
    }

    @Override
    public List<SeverityDistributionResponse> getSeverityDistributionByProjectKey(String key) {
        return sonarIssueRepository.getProjectSeverityDistribution();

    }
}
