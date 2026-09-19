package org.ai.aimodule.util;

import org.ai.sonarmodule.entity.SonarIssue;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPrompt(
            String rule,
            String message,
            String codeSnippet) {
        return """
            You are a Senior Java Developer.
            
            Analyze the following Java code and provide:
            
            1. Root Cause
            2. Fixed Code
            3. Detailed Explanation

                Code:
                %s
            Return response in this format:
               Root Cause:
                ...
               Fixed Code:
                ```java
                ...
               Explanation:
                ...""".formatted(codeSnippet);

//        return """
//                You are a Senior Java Architect.
//
//                Sonar Rule:
//                %s
//
//                Issue:
//                %s
//
//                Code:
//                %s
//
//                Provide:
//                1. Root Cause
//                2. Fixed Code
//                3. Explanation
//                """.formatted(rule, message, codeSnippet);
    }

    public String buildPrompt(SonarIssue issue) {

        return """
                You are a Senior Java Code Reviewer.
                STRICT RULES:
                - Return ONLY JSON.
                - Do not say "Here is the JSON".
                - Do not use markdown.
                - Do not add explanations outside JSON.
                - If possible, always provide a non-empty fixExplanation and fixedCode.
                - Return empty strings only if absolutely no fix can be suggested.
                
                Return ONLY valid JSON.

                {
                  "ruleKey":"",
                  "severity":"",
                  "rootCause":"",
                  "fixExplanation":"",
                  "fixedCode":""
                }

                Sonar Rule:
                %s

                Severity:
                %s

                Message:
                %s

                Code:
                %s
                """
                .formatted(
                        issue.getRuleKey(),
                        issue.getSeverity(),
                        issue.getMessage(),
                        issue.getCodeSnippet()
                );
    }
}
