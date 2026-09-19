package org.ai.sonarmodule.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.common.dtos.SourceResponse;
import org.ai.sonarmodule.dto.SonarAPIResponse;
import org.ai.sonarmodule.dto.SonarIssueResponse;
import org.ai.sonarmodule.dto.SonarProjectResponse;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SonarClient {

    private final RestTemplate restTemplate;
    private final RestClient restClient;

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${sonar.token}")
    private String token;

    private HttpHeaders getHeaders() {

        String auth =
                Base64.getEncoder()
                        .encodeToString(
                                (token + ":")
                                        .getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();

        headers.set(
                HttpHeaders.AUTHORIZATION,
                "Basic " + auth
        );

        return headers;
    }

    public String getProjects() {

        HttpEntity<Void> entity =
                new HttpEntity<>(getHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange(
                        sonarUrl +
                                "/api/projects/search",
                        HttpMethod.GET,
                        entity,
                        String.class
                );

        return response.getBody();

       /* return """
                {
                  "components":[
                    {
                      "key":"demo-project",
                      "name":"Demo Project"
                    }
                  ]
                }
                """;*/
    }

    public String getIssues() {

//        HttpEntity<Void> entity =
//                new HttpEntity<>(getHeaders());
//
//        ResponseEntity<String> response =
//                restTemplate.exchange(
//                        sonarUrl +
//                                "/api/issues/search",
//                        HttpMethod.GET,
//                        entity,
//                        String.class
//                );
//
//        return response.getBody();

        return """
                {
                  "issues":[
                    {
                      "key":"ISSUE-1",
                      "rule":"java:S106",
                      "severity":"MAJOR",
                      "message":"Replace System.out.println"
                    }
                  ]
                }
                """;
    }

    public List<SonarProjectResponse> getSonarProjects() {

        SonarProjectResponse project =
                new SonarProjectResponse();

        project.setKey("project-1");
        project.setName("AI Sonar Assistant");
        project.setQualifier("TRK");

        return List.of(project);
    }

    public List<SonarIssueResponse> getSonarIssues() {

        SonarIssueResponse issue =
                new SonarIssueResponse();

        issue.setKey("ISSUE-1001");
        issue.setRule("java:S106");
        issue.setSeverity("MAJOR");
        issue.setType("CODE_SMELL");
        issue.setComponent("AuthService.java");
        issue.setMessage(
                "Replace System.out.println");


//        issue.setStatus("OPEN"),
//                issue.setCodeSnippet("System.out.println("Hello World")";

        return List.of(issue);
    }

    public SonarAPIResponse getIssues(String projectKey) {

        String endpoint =
                sonarUrl +
                        "/api/issues/search?projects=" +
                        projectKey;
        log.info("endpoint {}", endpoint);
        String auth =
                Base64.getEncoder()
                        .encodeToString(
                                (token + ":").getBytes()
                        );

        return restClient.get()
                .uri(endpoint)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Basic " + auth
                )
                .retrieve()
                .body(SonarAPIResponse.class);
    }

    public SourceResponse getSource(String componentKey) {
        String url =
                sonarUrl
                        + "/api/sources/lines?key="+componentKey;
//                        + URLEncoder.encode(
//                        componentKey,
//                        StandardCharsets.UTF_8);
        log.info("url {}", url);

        String auth =
                Base64.getEncoder()
                        .encodeToString(
                                (token + ":").getBytes()
                        );
        try {
            return restClient.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION,
                            "Basic " + auth)
                    .retrieve()
                    .body(SourceResponse.class);
        } catch (Exception e) {
            log.error("Source not found for component {}", componentKey, e);
            return null;
        }
    }
}
