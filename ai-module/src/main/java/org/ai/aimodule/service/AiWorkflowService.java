package org.ai.aimodule.service;

import lombok.RequiredArgsConstructor;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiWorkflowService {

    private final SonarService sonarService;
    private final SonarIssueRepository sonarIssueRepository;
    private final AiFixService aiFixService;

    public int syncIssuesAndGenerateFixes(String projectKey) {

        int imported = sonarService.syncIssuesWithProjectKey(projectKey);

        List<SonarIssue> issues = sonarIssueRepository.findByProjectKey(projectKey);

        for (SonarIssue issue : issues) {

            try {
                aiFixService.generateFix(issue.getId());
            } catch (Exception ex) {
                // log error
            }
        }

        return imported;
    }
}
