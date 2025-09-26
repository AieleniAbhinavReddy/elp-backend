package com.root.config;

// NO CHANGES NEEDED IN THIS FILE

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GroqConfig {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Bean
    public RestTemplate template() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            // Use Bearer token for Groq API authorization
            request.getHeaders().add("Authorization", "Bearer " + groqApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}