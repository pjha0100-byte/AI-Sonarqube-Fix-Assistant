package org.ai.aimodule.service;

import org.ai.aimodule.dto.AiFixResponse;
import org.ai.aimodule.entity.AiFix;

import java.util.List;

public interface AiFixService {
    String generateFix(String prompt);
    AiFixResponse generateFix(Long issueId);
    List<AiFix> getFixHistory(Long issueId);

    AiFix getLatestFix(Long issueId);
}
