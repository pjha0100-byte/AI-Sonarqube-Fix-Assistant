package org.ai.reportmodule.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.reportmodule.service.AutomationService;
import org.ai.reportmodule.service.ReportService;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutomationServiceImpl
        implements AutomationService {

    private final SonarService sonarService;
    private final ReportService reportService;

    @Override
    public void runWeeklyAutomation(String projectKey) {

        try {

            log.info("Syncing Sonar issues");

            int imported =
                    sonarService.syncIssuesWithProjectKey(projectKey);

            log.info("{} issues imported {}", imported, projectKey);

            log.info("Generating PDF report {}", projectKey);

            reportService.generateWeeklyReport(projectKey);

            log.info("PDF generated for {}",projectKey);

        }
        catch (Exception ex) {

            log.error("Weekly automation failed", ex);
        }
    }
}
