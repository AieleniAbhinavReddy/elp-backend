package com.root.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    // Inject credentials from application.properties
    @Value("${jdoodle.client.id}")
    private String clientId;

    @Value("${jdoodle.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> executeCode(String code, String language, String input) {
        String url = "https://api.jdoodle.com/v1/execute";

        // 1. Map our simple language names to JDoodle's required 'language' name
        Map<String, String> languageMap = Map.of(
            "java", "java",
            "python", "python3",
            "javascript", "nodejs",
            "c", "c",
            "cpp", "cpp17" // Using a modern C++ version
        );
        
        String jdoodleLanguage = languageMap.get(language.toLowerCase());
        if (jdoodleLanguage == null) {
            throw new IllegalArgumentException("Unsupported or invalid language: " + language);
        }

        // 2. Create the request body for the JDoodle API
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("clientId", clientId);
        requestBody.put("clientSecret", clientSecret);
        requestBody.put("script", code);
        requestBody.put("stdin", input);
        requestBody.put("language", jdoodleLanguage);
        requestBody.put("versionIndex", "0"); // Use latest stable version for the language

        // 3. Set required headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // 4. Make the API call and handle the response
        try {
            // JDoodle's response is a Map, so we receive it as such
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            
            if (response != null && response.containsKey("output")) {
                 // Check for compilation or runtime errors from JDoodle
                Integer statusCode = (Integer) response.get("statusCode");
                if (statusCode >= 400) { // Typically indicates an error
                    return Map.of("output", "", "error", response.get("output").toString());
                }
                return Map.of("output", response.get("output").toString(), "error", "");
            } else {
                 return Map.of("output", "", "error", "Invalid response from compiler API.");
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("output", "", "error", "Failed to connect to the compiler API. Please try again later.");
        }
    }
}