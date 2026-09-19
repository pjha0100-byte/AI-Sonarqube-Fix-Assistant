package org.ai.reportmodule.controller;


import lombok.RequiredArgsConstructor;
import org.ai.common.dtos.SeverityDistributionResponse;
import org.ai.reportmodule.dto.DashboardSummaryResponse;
import org.ai.reportmodule.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {

        return dashboardService.getSummary();
    }

    @GetMapping("/severity-distribution")
    public ResponseEntity<List<SeverityDistributionResponse>>
    getSeverityDistribution() {

        return ResponseEntity.ok(
                dashboardService.getSeverityDistribution()
        );
    }

    @GetMapping("/severity-distribution/{key}")
    public ResponseEntity<List<SeverityDistributionResponse>>
    getSeverityDistributionByProject(@PathVariable String key) {

        return ResponseEntity.ok(
                dashboardService.getSeverityDistributionByProjectKey(key)
        );
    }
}
