package com.root.controllers;

// NO CHANGES NEEDED IN THIS FILE

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatbot")
//@CrossOrigin(origins = "http://localhost:3000") // Configure for your frontend
public class GroqController {

    @Value("${groq.model.name}")
    private String model;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate template;

    @PostMapping("/send")
    public String chat(@RequestParam("prompt") String msg) {
        GroqRequest request = new GroqRequest(model, msg);
        GroqResponse response = template.postForObject(apiUrl, request, GroqResponse.class);
        
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response received from the API.";
        }
        
        return response.getChoices().get(0).getMessage().getContent();
    }

    // --- Data Transfer Objects (DTOs) ---
    // Using static nested classes to reduce the number of files.

    @Data
    static class GroqRequest {
        private String model;
        private List<Message> messages;

        public GroqRequest(String model, String prompt) {
            this.model = model;
            this.messages = new ArrayList<>();
            this.messages.add(new Message("user", prompt));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GroqResponse {
        private List<Choice> choices;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Choice {
        private int index;
        private Message message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Message {
        private String role;
        private String content;
    }
}