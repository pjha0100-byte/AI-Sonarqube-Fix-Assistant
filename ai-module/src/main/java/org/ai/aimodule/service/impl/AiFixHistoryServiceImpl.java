package org.ai.aimodule.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.aimodule.dto.AiFixHistoryResponse;
import org.ai.aimodule.entity.AiFix;
import org.ai.aimodule.repository.AiFixRepository;
import org.ai.aimodule.service.AiFixHistoryService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiFixHistoryServiceImpl
        implements AiFixHistoryService {

    private final AiFixRepository aiFixRepository;

    @Override
    public Page<AiFixHistoryResponse> getAllFixes(
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        return aiFixRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public AiFixHistoryResponse getFixById(Long id) {

        AiFix fix = aiFixRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("AI Fix not found"));

        return mapToResponse(fix);
    }

    @Override
    public List<AiFixHistoryResponse> getFixesByIssueId(
            Long issueId
    ) {

        return aiFixRepository.findByIssueId(issueId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AiFixHistoryResponse mapToResponse(
            AiFix fix
    ) {

        return AiFixHistoryResponse.builder()
                .id(fix.getId())
                .issueId(fix.getIssueId())
                .suggestedFix(fix.getSuggestedFix())
                .explanation(fix.getExplanation())
                .modelName(fix.getModelName())
                .confidenceScore(fix.getConfidenceScore())
                .createdAt(fix.getCreatedAt())
                .build();
    }
}
