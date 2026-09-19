package org.ai.common.constants;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String SONAR_ANALYSIS_REQUEST =
            "sonar.analysis.request";

    public static final String AI_FIX_GENERATED =
            "ai.fix.generated";

    public static final String REPORT_GENERATED =
            "report.generated";
}
