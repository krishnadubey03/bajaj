package com.bfhl.qualifier1.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void generateWebhookAndSubmitQuery() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> requestBody = Map.of(
                "name", "Krishna Dubey",
                "regNo", "REG12347",
                "email", "krishna@example.com"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String webhookUrl = (String) response.getBody().get("webhookUrl");
                String accessToken = (String) response.getBody().get("accessToken");

                System.out.println("✅ Webhook URL: " + webhookUrl);
                System.out.println("✅ Access Token: " + accessToken);

                String finalQuery = "SELECT * FROM users"; // Placeholder SQL
                submitFinalQuery(webhookUrl, accessToken, finalQuery);
            } else {
                System.out.println("❌ Failed to generate webhook: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("❌ Exception during webhook generation: " + e.getMessage());
        }
    }

    private void submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        String submissionUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("finalQuery", finalQuery);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(submissionUrl, request, String.class);
            System.out.println("🚀 Submission Response: " + response.getBody());
        } catch (Exception e) {
            System.out.println("❌ Submission failed: " + e.getMessage());
        }
    }
}
