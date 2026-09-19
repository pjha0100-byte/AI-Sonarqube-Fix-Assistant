package org.ai.sonarmodule.controller;

import lombok.RequiredArgsConstructor;
import org.ai.sonarmodule.service.SonarSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sonar")
@RequiredArgsConstructor
public class SonarSyncController {

    private final SonarSyncService sonarSyncService;

    @PostMapping("/sync/{projectKey}")
    public ResponseEntity<?> sync(
            @PathVariable String projectKey) {

        int total =
                sonarSyncService.syncIssues(projectKey);

        Map<String,Object> response =
                new HashMap<>();

        response.put("importedIssues", total);

        response.put(
                "message",
                "Issues imported and AI fixes generated"
        );

        return ResponseEntity.ok(response);
    }
}
