package org.ai.reportmodule.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.aimodule.entity.AiFix;
import org.ai.aimodule.repository.AiFixRepository;
import org.ai.reportmodule.config.ReportProperties;
import org.ai.reportmodule.service.ReportService;
import org.ai.sonarmodule.entity.SonarIssue;
import org.ai.sonarmodule.repository.SonarIssueRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SonarIssueRepository issueRepository;
    private final AiFixRepository aiFixRepository;
    private final ReportProperties reportProperties;

    @Override
    public byte[] generatePdf(String projectKey) {

        List<SonarIssue> issues =
                issueRepository.findByProjectKey(projectKey);

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, output);

            document.open();

            document.add(
                    new Paragraph(
                            "AI Sonar Fix Report"
                    )
            );

            document.add(
                    new Paragraph(
                            "Project : " + projectKey
                    )
            );

            document.add(new Paragraph(" "));

            for (SonarIssue issue : issues) {

                document.add(
                        new Paragraph(
                                "Issue : "
                                        + issue.getIssueKey()
                        )
                );

                document.add(
                        new Paragraph(
                                "Rule : "
                                        + issue.getRuleKey()
                        )
                );

                document.add(
                        new Paragraph(
                                "Severity : "
                                        + issue.getSeverity()
                        )
                );

                document.add(
                        new Paragraph(
                                "Message : "
                                        + issue.getMessage()
                        )
                );

                Optional<AiFix> fixes =
                        aiFixRepository
                                .findByIssueId(
                                        issue.getId()
                                );

                if (fixes.isPresent()) {

                    AiFix fix =fixes.get();


                    document.add(
                            new Paragraph(
                                    "Explanation : "
                                            + fix.getExplanation()
                            )
                    );

                    document.add(
                            new Paragraph(
                                    "Suggested Fix:"
                            )
                    );

                    document.add(
                            new Paragraph(
                                    fix.getSuggestedFix()
                            )
                    );
                }

                document.add(
                        new Paragraph(
                                "-----------------------------------"
                        )
                );
            }

            document.close();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate PDF",
                    ex
            );
        }

        return output.toByteArray();
    }

    @Override
    public void generateWeeklyReport(String projectKey) {
        byte[] pdf = generatePdf(projectKey);

        String filename =
                "weekly-report-"
                        + LocalDate.now()
                        + ".pdf";

        try {
            Path reportsDir = Paths.get("reports");
            Files.createDirectories(reportsDir);

            Path reportFile =
                    reportsDir.resolve(
                            "weekly-report-" +
                                    LocalDate.now() +
                                    ".pdf");

            Files.write(reportFile, pdf);

            log.info("Report saved to {}", reportFile);

        } catch (IOException e) {

            log.error("Error while writing report file", e);

            throw new RuntimeException(e);
        }

        List<String> recipients =
                reportProperties.getNotificationEmails()
                        .get(projectKey);

        for (String email : recipients) {
            //  emailService.sendReportEmail(email, projectKey,pdf);
        }
    }
}
