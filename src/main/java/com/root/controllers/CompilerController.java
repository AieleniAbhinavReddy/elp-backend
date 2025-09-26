package com.root.controllers;

import com.root.services.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

// Request body structure (remains the same)
class CompilerRequest {
    private String code;
    private String language;
    private String input;

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
}

@RestController
@RequestMapping("/api/compiler")
// The @CrossOrigin annotation has been removed from here
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping("/run")
    public ResponseEntity<Map<String, String>> runCode(@RequestBody CompilerRequest request) {
        // This method's signature is now simpler because the new service doesn't throw checked exceptions
        Map<String, String> result = compilerService.executeCode(request.getCode(), request.getLanguage(), request.getInput());
        return ResponseEntity.ok(result);
    }
}