package com.vidaplus.sghss.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Value("${external-api.base-url:https://httpbin.org}")
    private String baseUrl;

    public String getHealth() {
        // Simple proxy to an external endpoint
        return restTemplate.getForObject(baseUrl + "/get", String.class);
    }
}
