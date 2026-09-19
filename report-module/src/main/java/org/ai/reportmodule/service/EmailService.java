package org.ai.reportmodule.service;

public interface EmailService {

    void sendReportEmail(
            String to,
            String projectKey,
            byte[] pdfReport);
}
