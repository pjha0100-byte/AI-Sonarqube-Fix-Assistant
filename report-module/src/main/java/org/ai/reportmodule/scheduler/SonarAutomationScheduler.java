package org.ai.reportmodule.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.reportmodule.service.AutomationService;
import org.ai.sonarmodule.entity.SonarProject;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class SonarAutomationScheduler {

    private final AutomationService automationService;
    private final SonarService sonarService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Scheduled(cron = "0 0 16 ? * FRI")
    public void executeWeeklyScan() {

        if (!running.compareAndSet(false, true)) {
            log.warn("Automation already running");
            return;
        }
        log.info("Weekly Sonar automation started");
        try {
            List<SonarProject> projects = sonarService.findActiveProjects();

            for (SonarProject project : projects) {

                automationService.runWeeklyAutomation(project.getProjectKey());
            }
        } finally {
            running.set(false);
        }

        log.info("Weekly Sonar automation completed");
    }
}
