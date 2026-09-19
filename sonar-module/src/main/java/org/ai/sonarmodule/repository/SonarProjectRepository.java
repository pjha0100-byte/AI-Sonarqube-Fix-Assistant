package org.ai.sonarmodule.repository;

import org.ai.sonarmodule.entity.SonarProject;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface SonarProjectRepository extends JpaRepository<SonarProject, Long> {

    Optional<SonarProject> findByProjectKey(
            String projectKey);

    List<SonarProject> findByActiveTrue();
}