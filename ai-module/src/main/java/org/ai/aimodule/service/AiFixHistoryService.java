package org.ai.aimodule.service;

import org.ai.aimodule.dto.AiFixHistoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AiFixHistoryService {

    Page<AiFixHistoryResponse> getAllFixes(
            int page,
            int size
    );

    AiFixHistoryResponse getFixById(Long id);

    List<AiFixHistoryResponse> getFixesByIssueId(Long issueId);
}
