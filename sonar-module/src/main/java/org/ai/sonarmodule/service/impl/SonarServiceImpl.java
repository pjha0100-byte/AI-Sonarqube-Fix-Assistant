package org.ai.sonarmodule.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.sonarmodule.client.SonarClient;
import org.ai.sonarmodule.dto.*;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.entity.SonarProject;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.ai.sonarmodule.repository.SonarProjectRepository;
import org.ai.sonarmodule.service.SonarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarServiceImpl implements SonarService {
    private final SonarClient sonarClient;
    private final SonarProjectRepository projectRepository;

    private final SonarIssueRepository issueRepository;

    @Override
    public String syncProjects() throws JsonProcessingException {

        String response = sonarClient.getProjects();

        ObjectMapper objectMapper = new ObjectMapper();
        ProjectResponse projectResponse = objectMapper.readValue(response, ProjectResponse.class);

        for (Component component : projectResponse.getComponents()) {


        SonarProject project = SonarProject.builder()
                .projectName(component.getName())
                .projectKey(component.getKey())
                .qualifier(component.getQualifier())
                .active(true)
                .build();

        projectRepository.save(project);
        }
        return projectResponse.toString();

    }

    @Override
    public String syncIssues() {

        return sonarClient.getIssues();
    }

    public void syncSonarProjects() {

        List<SonarProjectResponse> projects =
                sonarClient.getSonarProjects();

        projects.forEach(project -> {

            if (projectRepository
                    .findByProjectKey(
                            project.getKey())
                    .isEmpty()) {

                SonarProject entity =
                        SonarProject.builder()
                                .projectKey(
                                        project.getKey())
                                .projectName(
                                        project.getName())
                                .qualifier(
                                        project.getQualifier())
                                .build();

                projectRepository.save(entity);
            }
        });
    }

    @Override
    public void syncSonarIssues() {

        List<SonarIssueResponse> issues =
                sonarClient.getSonarIssues();

        issues.forEach(issue -> {

            if (issueRepository
                    .findByIssueKey(
                            issue.getKey())
                    .isEmpty()) {

                SonarIssue entity =
                        SonarIssue.builder()
                                .issueKey(
                                        issue.getKey())
                                .ruleKey(
                                        issue.getRule())
                                .severity(
                                        issue.getSeverity())
                                .type(
                                        issue.getType())
                                .component(
                                        issue.getComponent())
                                .message(
                                        issue.getMessage())
                                .status("OPEN")
                                .projectKey("project-1")
                                .build();

                issueRepository.save(entity);
            }
        });
    }

    @Override
    public int syncIssuesWithProjectKey(String projectKey) {
        SonarAPIResponse response = sonarClient.getIssues(projectKey);

        int count = 0;

        for (SonarIssueResponse dto : response.getIssues()) {

            boolean exists = issueRepository
                            .findByIssueKey(dto.getKey())
                            .isPresent();

            if (exists) {
                continue;
            }

            SonarIssue issue = SonarIssue.builder()
                    .issueKey(dto.getKey())
                    .ruleKey(dto.getRule())
                    .severity(dto.getSeverity())
                    .type(dto.getType())
                    .component(dto.getComponent())
                    .message(dto.getMessage())
                    .status(dto.getStatus())
                    .projectKey(projectKey)
                    .build();

            SonarIssue savedIssue =
                    issueRepository.save(issue);
            log.info("Imported issue {}", savedIssue.getIssueKey());

            count++;
        }

        return count;
    }

    @Override
    public List<SonarProject> findActiveProjects() {
        return projectRepository.findByActiveTrue();
    }

    @Override
    public List<IssueResponse> getAllIssues() {
        List<SonarIssue> sonarIssueList = issueRepository.findAll();
       return sonarIssueList.stream().map(this::map).toList();
    }

    private IssueResponse map(SonarIssue issue) {

        return IssueResponse.builder()
                .id(issue.getId())
                .issueKey(issue.getIssueKey())
                .ruleKey(issue.getRuleKey())
                .severity(issue.getSeverity())
                .type(issue.getType())
                .component(issue.getComponent())
                .message(issue.getMessage())
                .status(issue.getStatus())
                .projectKey(issue.getProjectKey())
                .lineNumber(issue.getLineNumber())
                .codeSnippet(issue.getCodeSnippet())
                .build();
    }

    @Override
    public Page<IssueResponse> getProjectIssues(String projectKey, int page,
                                                int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<SonarIssue> issuePage = issueRepository.findByProjectKey(projectKey, pageable);

        if (issuePage.isEmpty()) {
            return Page.empty(pageable);
        }
        return issuePage.map(this::map);
    }

    @Override
    public IssueResponse getIssue(Long id) {
        Optional<SonarIssue> issueResponseOptional = issueRepository.findById(id);
        if (issueResponseOptional.isPresent()) {
            SonarIssue sonarIssue = issueResponseOptional.get();
            return map(sonarIssue);
        }
        return null;
    }
}
