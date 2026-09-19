package org.ai.aimodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.aimodule.dto.AiFixResponse;
import org.ai.aimodule.entity.AiFix;
import org.ai.aimodule.service.AiFixService;
import org.ai.aimodule.service.AiWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiFixController {
    private final AiFixService aiFixService;
    private final AiWorkflowService aiWorkflowService;

    @PostMapping("/fix")
    public String generateFix(
            @RequestBody String prompt) {

        return aiFixService.generateFix(prompt);
    }

    @PostMapping("/fix/{issueId}")
    public ResponseEntity<AiFixResponse> generateFix(
            @PathVariable Long issueId) {

        return ResponseEntity.ok(
                aiFixService.generateFix(issueId)
        );
    }

    @GetMapping("/history/{issueId}")
    public ResponseEntity<List<AiFix>> getHistory(
            @PathVariable Long issueId) {

        return ResponseEntity.ok(
                aiFixService.getFixHistory(issueId)
        );
    }

    @GetMapping("/latest/{issueId}")
    public ResponseEntity<AiFix> getLatest(
            @PathVariable Long issueId) {

        return ResponseEntity.ok(
                aiFixService.getLatestFix(issueId)
        );
    }

    @PostMapping("/sync-and-fix/{projectKey}")
    public ResponseEntity<String> syncAndFix(
            @PathVariable String projectKey) {

        int count = aiWorkflowService
                .syncIssuesAndGenerateFixes(projectKey);

        return ResponseEntity.ok(
                count + " issues processed");
    }
}
