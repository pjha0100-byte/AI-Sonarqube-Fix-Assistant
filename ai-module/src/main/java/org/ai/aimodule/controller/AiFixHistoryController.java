package org.ai.aimodule.controller;

import lombok.RequiredArgsConstructor;
import org.ai.aimodule.dto.AiFixHistoryResponse;
import org.ai.aimodule.service.AiFixHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/fixes")
@RequiredArgsConstructor
public class AiFixHistoryController {

    private final AiFixHistoryService aiFixHistoryService;

    @GetMapping
    public Page<AiFixHistoryResponse> getAllFixes(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return aiFixHistoryService.getAllFixes(
                page,
                size
        );
    }

    @GetMapping("/{id}")
    public AiFixHistoryResponse getFixById(
            @PathVariable Long id
    ) {

        return aiFixHistoryService.getFixById(id);
    }

    @GetMapping("/issue/{issueId}")
    public List<AiFixHistoryResponse> getFixesByIssueId(
            @PathVariable Long issueId
    ) {

        return aiFixHistoryService.getFixesByIssueId(issueId);
    }
}
