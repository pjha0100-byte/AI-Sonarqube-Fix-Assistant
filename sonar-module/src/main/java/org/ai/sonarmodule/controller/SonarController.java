package org.ai.sonarmodule.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.ai.sonarmodule.dto.IssueResponse;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sonar")
@RequiredArgsConstructor
public class SonarController {

    private final SonarService sonarService;

    @GetMapping("/projects")
    public String projects() throws JsonProcessingException {

        return sonarService.syncProjects();
    }

    @GetMapping("/issues")
    public String issues() {

        return sonarService.syncIssues();
    }

    @PostMapping("/sync/projects")
    public String syncProjects() throws JsonProcessingException {

        sonarService.syncProjects();

        return "Projects Synced";
    }

    @PostMapping("/sync/issues")
    public String syncIssues() {

        sonarService.syncIssues();

        return "Issues Synced";
    }

    @GetMapping("/all/issues")
    public ResponseEntity<List<IssueResponse>> getAllIssues() {

        return ResponseEntity.ok(sonarService.getAllIssues());
    }

    @GetMapping("/all/issues/project/{projectKey}")
    public ResponseEntity<Page<IssueResponse>> getIssues(@PathVariable String projectKey, @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        Page<IssueResponse> issues = sonarService.getProjectIssues(projectKey, page, size);

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<IssueResponse> getIssue(@PathVariable Long id) {

        return ResponseEntity.ok(sonarService.getIssue(id));
    }
}
