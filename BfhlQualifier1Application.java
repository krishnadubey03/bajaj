package com.bfhl.qualifier1;

import com.bfhl.qualifier1.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BfhlQualifier1Application implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    public static void main(String[] args) {
        SpringApplication.run(BfhlQualifier1Application.class, args);
    }

    @Override
    public void run(String... args) {
        webhookService.generateWebhookAndSubmitQuery();
    }
}
