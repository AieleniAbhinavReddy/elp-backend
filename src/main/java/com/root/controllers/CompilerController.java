package com.root.controllers;

import com.root.dto.CompilerRequest;
import com.root.dto.CompilerResponse;
import com.root.services.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping("/execute")
    public ResponseEntity<CompilerResponse> executeCode(@RequestBody CompilerRequest request) {
        return ResponseEntity.ok(compilerService.execute(request));
    }
}