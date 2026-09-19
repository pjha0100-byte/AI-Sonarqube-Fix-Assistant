package org.ai.reportmodule.service;

public interface ReportService {
    byte[] generatePdf(String projectKey);

    void generateWeeklyReport(String projectKey);
}
