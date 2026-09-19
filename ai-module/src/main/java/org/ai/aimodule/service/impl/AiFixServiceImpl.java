package org.ai.aimodule.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.aimodule.dto.AiFixResponse;
import org.ai.aimodule.entity.AiFix;
import org.ai.aimodule.repository.AiFixRepository;
import org.ai.aimodule.service.AiFixService;
import org.ai.aimodule.util.PromptBuilder;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiFixServiceImpl implements AiFixService {
    private final ChatClient chatClient;
    private final SonarIssueRepository sonarIssueRepository;
    private final PromptBuilder promptBuilder;
    private final AiFixRepository aiFixRepository;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Value("${spring.ai.ollama.chat.model}")
    private String model;

    @PostConstruct
    public void init() {
        System.out.println("OLLAMA MODEL = " + model);
    }
    @Override
    public String generateFix(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public AiFixResponse generateFix(Long issueId) {

        List<AiFix> fixes =
                aiFixRepository.findByIssueIdOrderByCreatedAtDesc(issueId);

        if (!fixes.isEmpty()) {
            return AiFixResponse.builder()
                    .fixedCode(fixes.get(0).getSuggestedFix())
                    .fixExplanation(fixes.get(0).getExplanation())
                    .build();
        }

        SonarIssue issue = sonarIssueRepository.findById(issueId)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        String prompt = promptBuilder.buildPrompt(issue);

        try {

            String response = generateFix(prompt);

            System.out.println("RAW RESPONSE:");
            System.out.println(response);

            Pattern pattern = Pattern.compile("\\{.*}", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response);

            if (!matcher.find()) {
                throw new RuntimeException("No JSON found in AI response");
            }

            String json = matcher.group();

            AiFixResponse aiFixResponse =
                    objectMapper.readValue(json, AiFixResponse.class);

            AiFix aiFix = AiFix.builder()
                    .issueId(issueId)
                    .suggestedFix(aiFixResponse.getFixedCode())
                    .explanation(
                            objectMapper.writeValueAsString(aiFixResponse))
                    .modelName("llama3")
                    .confidenceScore(0.90)
                    .build();

            aiFixRepository.save(aiFix);
            issue.setSuggestedFix(aiFix.getSuggestedFix());
            issue.setExplanation(aiFix.getExplanation());
            issue.setConfidenceScore(aiFix.getConfidenceScore());
            sonarIssueRepository.save(issue);
            return aiFixResponse;

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to parse AI response",
                    ex
            );
        }
    }

    @Override
    public List<AiFix> getFixHistory(Long issueId) {

        return aiFixRepository.findByIssueIdOrderByCreatedAtDesc(issueId);
    }

    @Override
    public AiFix getLatestFix(Long issueId) {

        return aiFixRepository
                .findTopByIssueIdOrderByCreatedAtDesc(issueId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No fixes found for issue id : " + issueId
                        )
                );
    }
}
