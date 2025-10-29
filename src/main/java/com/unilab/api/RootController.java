package com.unilab.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Tag(name = "Root")
public class RootController {

    @GetMapping
    @Operation(summary = "API Root - Show base URLs dynamically")
    public ResponseEntity<Map<String, Object>> root(HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString().replaceAll("/$", "");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lab Booking API is running!");
        response.put("swagger_ui", baseUrl + "/swagger-ui.html");
        response.put("api_docs", baseUrl + "/v3/api-docs");
        response.put("health_check", baseUrl + "/api/health");
        response.put("events_api", baseUrl + "/api/events");

        return ResponseEntity.ok(response);
    }
}
