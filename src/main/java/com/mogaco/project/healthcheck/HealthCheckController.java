package com.mogaco.project.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/api/health-check")
    public Long healthCheck() {
        return System.currentTimeMillis();
    }

}
