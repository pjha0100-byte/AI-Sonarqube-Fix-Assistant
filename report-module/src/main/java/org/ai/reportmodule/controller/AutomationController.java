package org.ai.reportmodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.reportmodule.service.AutomationService;
import org.ai.sonarmodule.entity.SonarProject;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;
    private final SonarService sonarService;

    @PostMapping("/run")
    public ResponseEntity<String> runAutomation() {

        List<SonarProject> projects = sonarService.findActiveProjects();
        log.info("projects size {}", projects.size());
        for (SonarProject project : projects) {
            try {
                automationService.runWeeklyAutomation(project.getProjectKey());
            } catch (Exception ex) {

                log.error(
                        "Automation failed for project {}",
                        project.getProjectKey(),
                        ex
                );
            }
        }

        return ResponseEntity.ok(
                "Automation executed successfully"
        );
    }
}
