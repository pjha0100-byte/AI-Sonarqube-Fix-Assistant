package org.ai.sonarmodule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ai.sonarmodule.dto.IssueResponse;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.entity.SonarProject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SonarService {

    String syncProjects() throws JsonProcessingException;

    String syncIssues();

    void syncSonarProjects();

    void syncSonarIssues();
    int syncIssuesWithProjectKey(String projectKey);

    List<SonarProject> findActiveProjects();

    List<IssueResponse> getAllIssues();

    Page<IssueResponse> getProjectIssues(String projectKey, int page, int size);

    IssueResponse getIssue(Long id);
}
