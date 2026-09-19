package org.ai.reportmodule.service.impl;

import jakarta.annotation.Nonnull;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.reportmodule.service.EmailService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl
        implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendReportEmail(
            String to,
            String projectKey,
            byte[] pdfReport) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper = getMimeMessageHelper(to, projectKey, message);

            helper.addAttachment("weekly-report.pdf",
                    new ByteArrayResource(pdfReport)
            );

            mailSender.send(message);

            log.info(
                    "Email sent to {}",
                    to
            );

        } catch (Exception ex) {

            log.error(
                    "Failed to send email",
                    ex
            );
        }
    }

    @Nonnull
    private static MimeMessageHelper getMimeMessageHelper(String to, String projectKey, MimeMessage message) throws MessagingException {
        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true
                );

        helper.setTo(to);

        helper.setSubject(
                "Weekly Sonar AI Report - "
                        + projectKey
        );

        helper.setText(
                """
                Hi Team,

                Please find attached the weekly SonarQube AI report.

                Project: %s

                Regards,
                AI Sonar Fix Assistant
                """
                        .formatted(projectKey)
        );
        return helper;
    }
}
