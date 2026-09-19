package org.ai.sonarmodule.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.common.dtos.SourceLine;
import org.ai.common.dtos.SourceResponse;
import org.ai.sonarmodule.client.SonarClient;
import org.ai.sonarmodule.dto.SonarAPIResponse;
import org.ai.sonarmodule.dto.SonarIssueResponse;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.ai.sonarmodule.service.SonarSyncService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarSyncServiceImpl
        implements SonarSyncService {

    private final SonarClient sonarApiClient;
    private final SonarIssueRepository sonarIssueRepository;


    @Override
    public int syncIssues(String projectKey) {

        SonarAPIResponse response =
                sonarApiClient.getIssues(projectKey);
        log.info("response : {}", response);
        int count = 0;

        for (SonarIssueResponse dto : response.getIssues()) {

            boolean exists =
                    sonarIssueRepository
                            .findByIssueKey(dto.getKey())
                            .isPresent();

            if (exists) {
                continue;
            }

            SourceResponse sourceResponse =  sonarApiClient.getSource(
                    dto.getComponent());

            log.info("sourceResponse : {}", sourceResponse);

            String codeSnippet = extractSnippet(sourceResponse, dto);

            SonarIssue issue = SonarIssue.builder()
                    .issueKey(dto.getKey())
                    .ruleKey(dto.getRule())
                    .severity(dto.getSeverity())
                    .type(dto.getType())
                    .component(dto.getComponent())
                    .message(dto.getMessage())
                    .status(dto.getStatus())
                    .projectKey(projectKey)
                    .lineNumber(dto.getLine())
                    .codeSnippet(codeSnippet)
                    .build();

            SonarIssue savedIssue =
                    sonarIssueRepository.save(issue);
            log.info("Imported issue {}", savedIssue.getIssueKey());

            count++;
        }

        return count;
    }

    private String extractSnippet(SourceResponse sourceResponse, SonarIssueResponse dto) {
        if (sourceResponse == null ||
                sourceResponse.getSources() == null ||
                sourceResponse.getSources().isEmpty()) {

            log.warn(
                    "No source found for component {}",
                    dto.getComponent()
            );

            return "";
        }
        return sourceResponse.getSources()
                .stream()
                .filter(s -> Math.abs(s.getLine() - dto.getLine()) <= 2)
                .map(SourceLine::getCode)
                .collect(Collectors.joining("\n"));

    }
}
