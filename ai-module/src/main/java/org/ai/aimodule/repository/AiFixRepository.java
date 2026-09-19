package org.ai.aimodule.repository;

import org.ai.aimodule.entity.AiFix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AiFixRepository extends JpaRepository<AiFix, Long> {

    Optional<AiFix> findByIssueId(Long issueId);    Page<AiFix> findAll(Pageable pageable);
    Optional<AiFix> findTopByIssueIdOrderByCreatedAtDesc(Long issueId);
    boolean existsByIssueId(Long issueId);

    List<AiFix> findByIssueIdOrderByCreatedAtDesc(Long issueId);
}
