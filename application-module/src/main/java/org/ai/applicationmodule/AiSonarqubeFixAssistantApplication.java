package org.ai.applicationmodule;

import org.ai.authmodule.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "org.ai")
@EnableJpaRepositories(basePackages = {"org.ai.authmodule.repository", "org.ai.sonarmodule.repository", "org.ai.aimodule.repository"})
@EntityScan(basePackages = {"org.ai.authmodule.entity",  "org.ai.sonarmodule.entity", "org.ai.aimodule.entity"})
public class AiSonarqubeFixAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                AiSonarqubeFixAssistantApplication.class,
                args
        );
    }
        @Bean
        CommandLineRunner test(UserRepository repo) {
            return argss -> {
                System.out.println("User Count = " + repo.count());
            };
        }
}
