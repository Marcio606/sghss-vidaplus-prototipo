package com.vidaplus.sghss.controller;

import com.vidaplus.sghss.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        String resp = externalApiService.getHealth();
        return ResponseEntity.ok(resp);
    }
}
