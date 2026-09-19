package org.ai.sonarmodule.repository;

import org.ai.common.dtos.SeverityDistributionResponse;
import org.ai.sonarmodule.entity.SonarIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface SonarIssueRepository extends JpaRepository<SonarIssue, Long> {

    Optional<SonarIssue> findByIssueKey(
            String issueKey);

    List<SonarIssue> findByProjectKey(String projectKey);
    Page<SonarIssue> findByProjectKey(String projectKey, Pageable pageable);
    long countBySeverity(String severity);

    //long countDistinctByProjectKeyIsNotNull();

    @Query("SELECT COUNT(DISTINCT s.projectKey) FROM SonarIssue s WHERE s.projectKey IS NOT NULL")
    long countDistinctByProjectKeyIsNotNull();

    @Query("""
       SELECT new org.ai.common.dtos.SeverityDistributionResponse(
            s.severity,
            COUNT(s)
       )
       FROM SonarIssue s
       GROUP BY s.severity
       ORDER BY COUNT(s) DESC
       """)
    List<SeverityDistributionResponse> getSeverityDistribution();

    @Query("""
       SELECT new org.ai.common.dtos.SeverityDistributionResponse(
             s.projectKey,
              s.severity,
              COUNT(s)
       )
       FROM SonarIssue s
       GROUP BY s.projectKey, s.severity
       """)
    List<SeverityDistributionResponse> getProjectSeverityDistribution();
}
