package org.ai.reportmodule.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "report")
@Getter
@Setter
public class ReportProperties {

    private Map<String, List<String>> notificationEmails;
}
