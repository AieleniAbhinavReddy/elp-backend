package com.root.services;

import com.root.dto.CompilerRequest;
import com.root.dto.CompilerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompilerService {

    private static final String JDOODLE_EXECUTE_URL = "https://api.jdoodle.com/v1/execute";

    @Value("${jdoodle.client-id}")
    private String clientId;

    @Value("${jdoodle.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public CompilerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Map user-friendly language names to JDoodle language codes
     */
    private String mapLanguageToJDoodleCode(String language) {
        switch(language.toLowerCase()) {
            case "java":
                return "java";
            case "python":
                return "python3";
            case "javascript":
                return "nodejs";
            case "cpp":
            case "c++":
                return "cpp";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language + ". Supported: java, python, javascript, cpp");
        }
    }

    public CompilerResponse execute(CompilerRequest request) {
        try {
            // Validate input
            if (request.getScript() == null || request.getScript().trim().isEmpty()) {
                throw new IllegalArgumentException("Script cannot be empty");
            }
            if (request.getLanguage() == null || request.getLanguage().trim().isEmpty()) {
                throw new IllegalArgumentException("Language must be specified");
            }

            // Map language to JDoodle code
            String jdoodleLanguage = mapLanguageToJDoodleCode(request.getLanguage());

            // Build request body
            Map<String, Object> body = new HashMap<>();
            body.put("clientId", clientId);
            body.put("clientSecret", clientSecret);
            body.put("script", request.getScript());
            body.put("language", jdoodleLanguage);
            
            // Set versionIndex (default to 0 if not provided)
            Integer versionIndex = request.getVersionIndex();
            body.put("versionIndex", versionIndex != null ? versionIndex : 0);
            
            // Set stdin (optional)
            if (request.getStdin() != null && !request.getStdin().isEmpty()) {
                body.put("stdin", request.getStdin());
            }

            // Call JDoodle API
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
            Map<String, Object> response = restTemplate.postForObject(
                    JDOODLE_EXECUTE_URL,
                    httpEntity,
                    Map.class
            );

            if (response == null) {
                throw new RuntimeException("No response from JDoodle API");
            }

            // Extract response values safely
            Object outputObj = response.get("output");
            Object errorObj = response.get("error");
            Object statusObj = response.get("statusCode");
            Object memoryObj = response.get("memory");
            Object cpuObj = response.get("cpuTime");

            return new CompilerResponse(
                    outputObj != null ? (String) outputObj : "",
                    errorObj != null ? (String) errorObj : "",
                    statusObj != null ? String.valueOf(statusObj) : "0",
                    memoryObj != null ? String.valueOf(memoryObj) : "",
                    cpuObj != null ? String.valueOf(cpuObj) : ""
            );
        } catch (IllegalArgumentException e) {
            return new CompilerResponse(
                    "",
                    e.getMessage(),
                    "-1",
                    "",
                    ""
            );
        } catch (RestClientException e) {
            return new CompilerResponse(
                    "",
                    "Error communicating with JDoodle API: " + e.getMessage(),
                    "-1",
                    "",
                    ""
            );
        } catch (Exception e) {
            return new CompilerResponse(
                    "",
                    "Unexpected error: " + e.getMessage(),
                    "-1",
                    "",
                    ""
            );
        }
    }
}